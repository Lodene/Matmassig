package com.school.matmassig.userpreferencesservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.userpreferencesservice.config.RabbitMQConfig;
import com.school.matmassig.userpreferencesservice.dto.NotificationMessage;
import com.school.matmassig.userpreferencesservice.dto.UserPreferenceDTO;
import com.school.matmassig.userpreferencesservice.entity.UserPreference;
import com.school.matmassig.userpreferencesservice.service.UserPreferenceService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final UserPreferenceService service;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQConsumer(UserPreferenceService service, ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.service = service;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "preference-queue")
    public void consumeMessage(Message message) {
        try {
            // Récupération des propriétés du message
            MessageProperties properties = message.getMessageProperties();
            String routingKey = properties.getReceivedRoutingKey();

            logger.debug("Received message with routingKey: {}", routingKey);

            // Conversion en chaîne JSON
            String jsonPayload = new String(message.getBody(), StandardCharsets.UTF_8);
            logger.debug("Raw message payload: {}", jsonPayload);

            // Désérialisation en DTO
            UserPreferenceDTO preferenceDTO = objectMapper.readValue(jsonPayload, UserPreferenceDTO.class);
            logger.debug("Deserialized message: {}", preferenceDTO);

            // Gérer le message en fonction de la routingKey
            switch (routingKey) {
                case "preference.create":
                    service.createPreference(preferenceDTO);
                    Map<String, Object> resultCreate = new HashMap<>();
                    resultCreate.put("email", preferenceDTO.getEmail());
                    resultCreate.put("message", "La préférence a été créée avec succès");
                    sendMessageToQueue(resultCreate);
                    break;
                case "preference.update":
                    service.updatePreference(preferenceDTO);
                    Map<String, Object> resultUpdate = new HashMap<>();
                    resultUpdate.put("email", preferenceDTO.getEmail());
                    resultUpdate.put("message", "La préférence a été mise à jour avec succès");
                    sendMessageToQueue(resultUpdate);
                    break;
                case "preference.delete":
                    service.deletePreference(preferenceDTO.getId(), preferenceDTO.getUserId());
                    Map<String, Object> resultPreference = new HashMap<>();
                    resultPreference.put("email", preferenceDTO.getEmail());
                    resultPreference.put("message", "La préférence a été supprimée avec succès");
                    sendMessageToQueue(resultPreference);
                    break;
                case "preference.getbyuser":
                    List<UserPreference> result = service.getPreferencesByUserId(preferenceDTO.getUserId());
                    Map<String, Object> resultGetbyuser = new HashMap<>();
                    resultGetbyuser.put("email", preferenceDTO.getEmail());

                    // Sérialiser la liste en JSON avant de l'ajouter au message
                    String serializedResult = objectMapper.writeValueAsString(result);
                    resultGetbyuser.put("message", serializedResult);

                    sendMessageToQueue(resultGetbyuser);
                    break;
                default:
                    logger.warn("Unknown routingKey: {}", routingKey);
            }
        } catch (Exception e) {
            logger.error("Failed to process message: {}", new String(message.getBody()), e);
        }
    }

    private void sendMessageToQueue(Map<String, Object> message) {
        try {
            // Créez une instance de NotificationMessage à partir des données du Map
            String email = (String) message.get("email");
            String text = (String) message.get("message");
            NotificationMessage notificationMessage = new NotificationMessage(email, text);

            // Sérialisez l'objet en JSON
            String jsonMessage = objectMapper.writeValueAsString(notificationMessage);

            // Envoyez le JSON brut
            rabbitTemplate.convertAndSend("app-exchange", "esb.notifications", jsonMessage);

            System.out.println("Message sent to ESB queue: " + jsonMessage);
        } catch (Exception e) {
            System.err.println("Failed to send message to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
