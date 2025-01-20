package com.school.matmassig.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.model.InventoryItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageHandlerService {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    public MessageHandlerService(InventoryService inventoryService, ObjectMapper objectMapper) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
    }

    public void processMessage(String message, String routingKey) {
        try {
            switch (routingKey) {
                case RabbitConfig.ROUTING_KEY_ADD:
                    handleAddItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_DELETE:
                    handleDeleteItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_UPDATE:
                    handleUpdateItem(message);
                    break;
                case RabbitConfig.ROUTING_KEY_GET:
                    handleGetItems(message);
                    break;
                default:
                    System.err.println("Clé de routage inconnue : " + routingKey);
            }
        } catch (Exception e) {
            System.err
                    .println("Erreur lors du traitement du message avec clé [" + routingKey + "] : " + e.getMessage());
        }
    }

    private void handleAddItem(String message) {
        try {
            InventoryItem item = parseMessageToInventoryItem(message);
            InventoryItem savedItem = inventoryService.addItem(item);
            System.out.println("Item ajouté avec succès : " + savedItem);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un item : " + e.getMessage());
        }
    }

    private void handleDeleteItem(String message) {
        try {
            Integer itemId = Integer.parseInt(message);
            inventoryService.deleteItem(itemId);
            System.out.println("Item supprimé avec succès, ID : " + itemId);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un item : " + e.getMessage());
        }
    }

    private void handleUpdateItem(String message) {
        try {
            InventoryItem updatedItem = parseMessageToInventoryItem(message);
            InventoryItem item = inventoryService.updateItem(updatedItem.getId(), updatedItem);
            System.out.println("Item mis à jour avec succès : " + item);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un item : " + e.getMessage());
        }
    }

    private void handleGetItems(String message) {
        try {
            Integer userId = Integer.parseInt(message);
            List<InventoryItem> items = inventoryService.getItemsByUserId(userId);
            System.out.println("Items récupérés pour l'utilisateur " + userId + " : " + items);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des items : " + e.getMessage());
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
}