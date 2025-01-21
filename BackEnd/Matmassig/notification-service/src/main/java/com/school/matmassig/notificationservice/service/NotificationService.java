package com.school.matmassig.notificationservice.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationService {

    private WebSocketService webSocketService;

    public void connectAndSend(String message) {
        try {
            NotificationPayload payload = parseMessage(message);

            if (payload == null) {
                System.out.println("Message mal formaté : " + message);
                return;
            }

            System.out.println("Email : " + payload.getEmail());
            System.out.println("Texte : " + payload.getMessage());

            if (webSocketService == null) {
                webSocketService = new WebSocketService();
                webSocketService.connectToWebSocket(payload);
            }

            webSocketService.sendMessage(payload);
            System.out.println(
                    "Message envoyé à l'email : " + payload.getEmail() + " avec le texte : " + payload.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion ou de l'envoi du message : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private NotificationPayload parseMessage(String message) {
        try {
            // Utilisation de ObjectMapper pour parser le message JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> jsonMap = objectMapper.readValue(message, Map.class);

            String email = jsonMap.get("email");
            String text = jsonMap.get("message");

            if (email == null || email.isEmpty()) {
                System.out.println("Message mal formaté : email manquant.");
                return null;
            }

            if (text == null || text.isEmpty()) {
                System.out.println("Message mal formaté : texte manquant.");
                return null;
            }

            return new NotificationPayload(email, text);
        } catch (Exception e) {
            System.err.println("Erreur lors du parsing du message JSON : " + e.getMessage());
            return null;
        }
    }
}
