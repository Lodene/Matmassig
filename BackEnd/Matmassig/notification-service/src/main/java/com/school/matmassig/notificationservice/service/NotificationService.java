package com.school.matmassig.notificationservice.service;

import java.util.Map;

import com.school.matmassig.notificationservice.models.MessageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private WebSocketService webSocketService;

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void connectAndSend(String message) {
        try {
            NotificationPayload payload = parseMessage(message);

            if (payload == null) {
                System.out.println("Message mal formaté : " + message);
                return;
            }

            System.out.println("Email : " + payload.getEmail());
            System.out.println("Texte : " + payload.getMessage());
            MessageEntity me = new MessageEntity(payload.getMessage(), payload.getEmail());
            // fixme: refacto toute cette merde
            if (payload.getMessage().startsWith("User recipes fetched successfully: ")) {
                me.setMessage(payload.getMessage().replace("User recipes fetched successfully:", "{\"list\": ").concat("}"));
                ResponseEntity<String> response = restTemplate.postForEntity("http://websocket-service:8089/recipeByUser", me, String.class);
            } else if (payload.getMessage().startsWith("your review has been created")) {
                ResponseEntity<String> response = restTemplate.postForEntity("http://websocket-service:8089/reviewCreated", me, String.class);

            } else {

            }



            /*
            if (webSocketService == null) {
                webSocketService = new WebSocketService();
                webSocketService.connectToWebSocket(payload);
            }

            webSocketService.sendMessage(payload);
            System.out.println(
                    "Message envoyé à l'email : " + payload.getEmail() + " avec le texte : " + payload.getMessage());
        */
        } catch (Exception e) {
            System.err.println("Erreur lors de la connexion ou de l'envoi du message : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private NotificationPayload parseMessage(String message) {
        try {
            // Utilisation de ObjectMapper pour parser le message JSON en un Map
            ObjectMapper objectMapper = new ObjectMapper();

            // Détection si le message est encapsulé en tant que chaîne brute
            if (message.startsWith("\"") && message.endsWith("\"")) {
                System.out.println("Message encodé détecté, décodage en cours...");
                // Décodage de la chaîne brute JSON
                message = objectMapper.readValue(message, String.class);
            }

            // Désérialisation en Map
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
            System.err.println("Erreur lors du parsing en Map : " + e.getMessage());

            try {
                // Tentative de désérialisation directe en NotificationPayload
                ObjectMapper objectMapper = new ObjectMapper();
                if (message.startsWith("\"") && message.endsWith("\"")) {
                    System.out.println("Message encodé détecté, décodage en cours...");
                    message = objectMapper.readValue(message, String.class);
                }

                NotificationPayload payload = objectMapper.readValue(message, NotificationPayload.class);
                return payload;

            } catch (Exception ex) {
                System.err.println("Erreur lors du parsing en NotificationPayload : " + ex.getMessage());
                return null;
            }
        }
    }

}
