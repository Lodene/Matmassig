package com.school.matmassig.notificationservice.service;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketService {

    private WebSocketClient webSocketClient;

    public void connectToWebSocket(NotificationPayload payload) {
        try {
            String wsUrl = "ws://websocket-service:8089?email=" + payload.getEmail();
            System.out.println("Connexion au WebSocket : " + wsUrl);
            webSocketClient = new WebSocketClient(new URI(wsUrl)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connexion WebSocket ouverte avec l'email : " + payload.getEmail());
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Message reçu du WebSocket : " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connexion WebSocket fermée : " + reason);
                    WebSocketService.this.reconnect(payload);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                    WebSocketService.this.reconnect(payload);
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
            WebSocketService.this.reconnect(payload);
        }
    }

    public void sendMessage(NotificationPayload payload) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(payload.getMessage());
            System.out.println("Message envoyé : " + payload.getMessage());
        } else {
            System.out.println("WebSocket non connecté");
        }
    }

    private void reconnect(NotificationPayload payload) {
        try {
            System.out.println("Tentative de reconnexion...");
            Thread.sleep(5000); // Attendre 5 secondes avant de réessayer
            connectToWebSocket(payload);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class NotificationPayload {
    private String email;
    private String message;

    public NotificationPayload(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
