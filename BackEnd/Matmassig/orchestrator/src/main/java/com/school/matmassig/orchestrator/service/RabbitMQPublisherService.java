package com.school.matmassig.orchestrator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQPublisherService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(String exchange, String routingKey, Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(exchange, routingKey, jsonMessage);
            System.out.println("Message sent to RabbitMQ: " + jsonMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing message: " + e.getMessage());
        }
    }

    public void publishMessageWithAdditionalData(String exchange, String routingKey, Object originalData, String email) {
        try {
            // Convert original data to Map for combination
            Map<String, Object> data = objectMapper.convertValue(originalData, Map.class);
            data.put("email", email);

            // Serialize the combined data
            String jsonMessage = objectMapper.writeValueAsString(data);
            rabbitTemplate.convertAndSend(exchange, routingKey, jsonMessage);

            System.out.println("Message sent to RabbitMQ: " + jsonMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error converting message: " + e.getMessage());
        }
    }
}
