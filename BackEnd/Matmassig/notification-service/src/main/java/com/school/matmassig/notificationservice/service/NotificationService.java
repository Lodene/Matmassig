package com.school.matmassig.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private WebSocketService webSocketService;

    public void connectAndSend(String message) {
        // Découper la chaîne pour extraire l'email et le texte
        String[] parts = message.split(";");
        String email = null;
        String text = null;

        // Parcourir les parties pour récupérer email et texte
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                if ("email".equalsIgnoreCase(key)) {
                    email = value;
                } else if ("message".equalsIgnoreCase(key)) {
                    text = value;
                }
            }
        }

        // Vérifier que l'email et le texte sont présents
        if (email == null) {
            System.out.println("Message mal formaté : email manquant : " + message);
            return;
        } else if (text == null) {
            System.out.println("Message mal formaté : texte manquant : " + message);
            return;
        }

        System.out.println("Email : " + email);
        System.out.println("\nTexte : " + text);

        // Créer une connexion WebSocket si nécessaire
        if (webSocketService == null) {
            webSocketService = new WebSocketService();
            webSocketService.connectToWebSocket(email, text);

            // Attendre un court instant pour établir la connexion
            try {
                Thread.sleep(1000); // Pas idéal pour une production, mais acceptable ici
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Envoyer le message
        webSocketService.sendMessage(text);
        System.out.println("Message envoyé à l'email : " + email + " avec le texte : " + text);
    }
}
