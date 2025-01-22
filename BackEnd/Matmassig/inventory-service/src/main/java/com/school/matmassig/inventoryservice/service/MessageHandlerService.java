package com.school.matmassig.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.model.InventoryItem;
import com.school.matmassig.inventoryservice.model.InventoryItemMessage;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageHandlerService {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public MessageHandlerService(InventoryService inventoryService, ObjectMapper objectMapper,
            RabbitTemplate rabbitTemplate) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(String message, String routingKey) {
        try {
            Object result = null;
            InventoryItemMessage itemMessage = objectMapper.readValue(message, InventoryItemMessage.class);

            switch (routingKey) {
                case RabbitConfig.ROUTING_KEY_ADD:
                    result = handleAddItem(itemMessage);
                    break;
                case RabbitConfig.ROUTING_KEY_DELETE:
                    result = handleDeleteItem(itemMessage);
                    break;
                case RabbitConfig.ROUTING_KEY_UPDATE:
                    result = handleUpdateItem(itemMessage);
                    break;
                case RabbitConfig.ROUTING_KEY_GET:
                    result = handleGetItems(itemMessage);
                    break;
                default:
                    System.err.println("Clé de routage inconnue : " + routingKey);
            }
            if (result != null) {
                sendToEsbQueue(result);
            }
        } catch (Exception e) {
            System.err
                    .println("Erreur lors du traitement du message avec clé [" + routingKey + "] : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Map<String, Object> handleAddItem(InventoryItemMessage message) {
        try {
            InventoryItem item = new InventoryItem(
                    message.getId(),
                    message.getUserId(),
                    message.getQuantity());
            InventoryItem savedItem = inventoryService.addItem(item);

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item ajouté avec succès !");
            System.out.println("Item ajouté avec succès : " + savedItem);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de l'ajout de l'item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleDeleteItem(InventoryItemMessage message) {
        try {
            inventoryService.deleteItem(message.getId());

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item supprimé avec succès !");
            System.out.println("Item supprimé avec succès, ID : " + message.getId());

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la suppression de l'item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleUpdateItem(InventoryItemMessage message) throws Exception {
        try {
            if (message.getId() == null || message.getId() <= 0) {
                sendErrorToEsb(message.getEmail(), "Invalid item ID: " + message.getId());
                throw new IllegalArgumentException("Invalid item ID.");
            }

            InventoryItem item = new InventoryItem(
                    message.getId(),
                    message.getUserId(),
                    message.getQuantity());
            inventoryService.updateItem(item.getId(), item);

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item mis à jour avec succès !");
            System.out.println("Item mis à jour : " + message.getId());
            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la mise à jour de l'item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleGetItems(InventoryItemMessage message) {
        try {
            List<InventoryItem> items = inventoryService.getItemsByUserId(message.getUserId());

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Items récupérés avec succès !");
            System.out.println("Items récupérés pour l'utilisateur " + message.getUserId());

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des items : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la récupération des items : " + e.getMessage());
            return null;
        }
    }

    private void sendToEsbQueue(Object result) {
        try {
            String resultMessage = objectMapper.writeValueAsString(result);

            if (resultMessage == null || resultMessage.isEmpty()) {
                System.err.println("Result message is null or empty. Skipping send.");
                return;
            }

            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.QUEUE_NAME, resultMessage);
            System.out.println("Result sent to ESB queue: " + resultMessage);
        } catch (Exception e) {
            System.err.println("Failed to send result to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendErrorToEsb(String email, String errorMessage) {
        try {
            System.out.println("Sending error notification to ESB queue: " + errorMessage);
            Map<String, String> errorPayload = new HashMap<>();
            errorPayload.put("email", email);
            errorPayload.put("message", errorMessage);

            String errorMessageJson = objectMapper.writeValueAsString(errorPayload);
            rabbitTemplate.convertAndSend("app-exchange", "esb.notification", errorMessageJson);
            System.out.println("Error notification sent to ESB queue: " + errorMessageJson);
        } catch (Exception e) {
            System.err.println("Failed to send error notification to ESB queue: " + e.getMessage());
        }
    }
}
