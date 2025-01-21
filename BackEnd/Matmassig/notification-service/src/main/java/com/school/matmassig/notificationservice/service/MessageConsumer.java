package com.school.matmassig.notificationservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageConsumer {

    private final RestTemplate restTemplate;

    public MessageConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "esb-queue")
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
    }
}
