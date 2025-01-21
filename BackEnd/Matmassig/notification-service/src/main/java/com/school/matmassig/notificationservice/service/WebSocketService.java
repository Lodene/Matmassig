package com.school.matmassig.notificationservice.service;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketService {

    private WebSocketClient webSocketClient;

    public void connectToWebSocket(String email, String message) {
        try {
            String wsUrl = "ws://websocket-service:8089?email=" + email;
            // String wsUrl = "ws://websocket-service:8089?email=test@test.test";
            System.out.println("Connexion au WebSocket : " + wsUrl);
            webSocketClient = new WebSocketClient(new URI(wsUrl)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connexion WebSocket ouverte avec l'email : " + email);
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Message reçu du WebSocket : " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Connexion WebSocket fermée : " + reason);
                    WebSocketService.this.reconnect(email, message);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                    WebSocketService.this.reconnect(email, message);
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
            WebSocketService.this.reconnect(email, message);
        }
    }

    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
            System.out.println("Message envoyé : " + message);
        } else {
            System.out.println("WebSocket non connecté");
        }
    }

    private void reconnect(String email, String message) {
        try {
            System.out.println("Tentative de reconnexion...");
            Thread.sleep(5000); // Attendre 5 secondes avant de réessayer
            connectToWebSocket(email, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
