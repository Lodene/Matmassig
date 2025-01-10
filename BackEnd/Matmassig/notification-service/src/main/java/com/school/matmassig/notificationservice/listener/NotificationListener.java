package com.school.matmassig.notificationservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.school.matmassig.notificationservice.service.NotificationService;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "esb-queue")
    public void receiveMessage(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Aucun message à traiter.");
            return;
        }
        System.out.println("Message reçu depuis esb-queue : " + message);
        // Traitez le message ici
    }
}
