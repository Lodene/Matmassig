package com.school.matmassig.notificationservice.Service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageConsumer {

    private final RestTemplate restTemplate;

    @Value("${target.api.url}")
    private String targetApiUrl;

    public MessageConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "my-queue")
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);

        // Transmettre le message à une API cible
        try {
            String response = restTemplate.postForObject(targetApiUrl, message, String.class);
            System.out.println("API Response: " + response);
        } catch (Exception e) {
            System.err.println("Failed to call target API: " + e.getMessage());
        }
    }
}
