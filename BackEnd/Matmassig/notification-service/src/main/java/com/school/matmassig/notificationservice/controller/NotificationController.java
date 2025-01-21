package com.school.matmassig.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.matmassig.notificationservice.service.NotificationService;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/send-to-websocket")
    public String sendToWebSocket(@RequestParam String email, @RequestParam String message) {
        notificationService.connectAndSend(message);
        return "Message envoyé au WebSocket pour l'email : " + email;
    }
}
