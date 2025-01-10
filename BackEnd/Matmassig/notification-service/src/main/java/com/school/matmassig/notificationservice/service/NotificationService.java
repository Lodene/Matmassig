package com.school.matmassig.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void handleNotification(String message) {
        // Logique métier pour traiter la notification
        System.out.println("Notification traitée : " + message);
    }
}
