package com.school.matmassig.userpreferencesservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.userpreferencesservice.dto.UserPreferenceDTO;
import com.school.matmassig.userpreferencesservice.service.UserPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final UserPreferenceService service;
    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(UserPreferenceService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "preference-queue")
    public void consumeMessage(Message message) {
        try {
            // Récupération des propriétés du message
            MessageProperties properties = message.getMessageProperties();
            String routingKey = properties.getReceivedRoutingKey();

            // Log the routingKey and raw message
            logger.debug("Received message with routingKey: {}", routingKey);
            logger.debug("Raw message payload: {}", new String(message.getBody()));

            // Désérialiser le payload JSON
            UserPreferenceDTO preferenceDTO = objectMapper.readValue(message.getBody(), UserPreferenceDTO.class);

            // Log the deserialized object
            logger.debug("Deserialized message: {}", preferenceDTO);

            // Gérer le message en fonction de la routingKey
            switch (routingKey) {
                case "preference.create":
                    service.createPreference(preferenceDTO);
                    break;
                case "preference.update":
                    service.updatePreference(preferenceDTO);
                    break;
                case "preference.delete":
                    service.deletePreference(preferenceDTO.getId(), preferenceDTO.getUserId());
                    break;
                case "preference.getbyuser":
                    service.getPreferencesByUserId(preferenceDTO.getUserId());
                    break;
                default:
                    logger.warn("Unknown routingKey: {}", routingKey);
            }
        } catch (Exception e) {
            logger.error("Failed to process message: {}", message, e);
        }
    }
}
