package com.school.matmassig.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.model.InventoryItem;
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

            switch (routingKey) {
                case RabbitConfig.ROUTING_KEY_ADD:
                    result = handleAddItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_DELETE:
                    result = handleDeleteItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_UPDATE:
                    result = handleUpdateItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_GET:
                    result = handleGetItems(message);
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

    private Map<String, Object> handleAddItem(String message) {
        try {
            InventoryItem item = parseMessageToInventoryItem(message);
            InventoryItem savedItem = inventoryService.addItem(item);

            Map<String, Object> result = new HashMap<>();
            result.put("email", extractEmail(message));
            result.put("item", savedItem);
            System.out.println("Item ajouté avec succès : " + savedItem);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleDeleteItem(String message) {
        try {
            Integer itemId = Integer.parseInt(message);
            inventoryService.deleteItem(itemId);

            Map<String, Object> result = new HashMap<>();
            result.put("email", extractEmail(message));
            result.put("itemId", itemId);
            System.out.println("Item supprimé avec succès, ID : " + itemId);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleUpdateItem(String message) {
        try {
            InventoryItem updatedItem = parseMessageToInventoryItem(message);
            InventoryItem item = inventoryService.updateItem(updatedItem.getId(), updatedItem);

            Map<String, Object> result = new HashMap<>();
            result.put("email", extractEmail(message));
            result.put("item", item);
            System.out.println("Item mis à jour avec succès : " + item);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un item : " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> handleGetItems(String message) {
        try {
            Integer userId = Integer.parseInt(message);
            List<InventoryItem> items = inventoryService.getItemsByUserId(userId);

            Map<String, Object> result = new HashMap<>();
            result.put("email", extractEmail(message));
            result.put("items", items);
            System.out.println("Items récupérés pour l'utilisateur " + userId + " : " + items);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des items : " + e.getMessage());
            return null;
        }
    }

    private InventoryItem parseMessageToInventoryItem(String message) {
        try {
            return objectMapper.readValue(message, InventoryItem.class);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur lors de la conversion du message JSON en InventoryItem : " + e.getMessage(), e);
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

    private String extractEmail(String message) {
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(message, Map.class);
            return (String) jsonMap.get("email");
        } catch (Exception e) {
            System.err.println("Failed to extract email from message: " + e.getMessage());
            return null;
        }
    }
}
