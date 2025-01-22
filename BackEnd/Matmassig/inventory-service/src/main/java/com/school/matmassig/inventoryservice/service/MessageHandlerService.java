package com.school.matmassig.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.model.InventoryItem;
import com.school.matmassig.inventoryservice.model.InventoryItemMessage;
import com.school.matmassig.inventoryservice.repository.InventoryItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageHandlerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final InventoryItemRepository inventoryItemRepository;

    public MessageHandlerService(InventoryItemRepository inventoryItemRepository, ObjectMapper objectMapper,
            RabbitTemplate rabbitTemplate) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Map<String, Object> handleAddItem(InventoryItemMessage message) {
        try {

            InventoryItem item = new InventoryItem();
            item.setUserId(message.getUserId());
            item.setQuantity(message.getQuantity());
            item.setIngredientId(message.getIngredientId());
            InventoryItem savedItem = inventoryItemRepository.save(item);

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item ajouté avec succès !");
            System.out.println("Item ajouté avec succès : " + savedItem);
            sendToEsbQueue(result);

            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de l'ajout de l'item : " + e.getMessage());
            return null;
        }
    }

    public Map<String, Object> handleDeleteItem(InventoryItemMessage message) {
        try {
            inventoryItemRepository.deleteById(message.getId());

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item supprimé avec succès !");
            System.out.println("Item supprimé avec succès, ID : " + message.getId());
            sendToEsbQueue(result);
            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la suppression de l'item : " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public Map<String, Object> handleUpdateItem(InventoryItemMessage message) throws Exception {
        try {
            if (message.getId() == null || message.getId() <= 0) {
                sendErrorToEsb(message.getEmail(), "Invalid item ID: " + message.getId());
                throw new IllegalArgumentException("Invalid item ID.");
            }
            InventoryItem item = inventoryItemRepository.getById(message.getId());
            item.setQuantity(message.getQuantity());
            inventoryItemRepository.save(item);

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", "Item mis à jour avec succès !");
            System.out.println("Item mis à jour : " + message.getId());
            sendToEsbQueue(result);
            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un item : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la mise à jour de l'item : " + e.getMessage());
            return null;
        }
    }

    public Map<String, Object> handleGetItems(InventoryItemMessage message) {
        try {
            // Utilisation de la méthode findByUserId
            List<InventoryItem> items = inventoryItemRepository.findByUserId(message.getUserId());

            Map<String, Object> result = new HashMap<>();
            result.put("email", message.getEmail());
            result.put("message", items);
            System.out.println("Items récupérés pour l'utilisateur " + message.getUserId());
            sendToEsbQueue(result);
            return result;
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des items : " + e.getMessage());
            sendErrorToEsb(message.getEmail(), "Erreur lors de la récupération des items : " + e.getMessage());
            return null;
        }
    }

    private void sendToEsbQueue(Object result) {
        try {
            System.out.println("TA MERE LA PUTE MARCHE");
            String resultMessage = objectMapper.writeValueAsString(result);

            if (resultMessage == null || resultMessage.isEmpty()) {
                System.err.println("Result message is null or empty. Skipping send.");
                return;
            }
            rabbitTemplate.convertAndSend("app-exchange", "esb.notification", resultMessage);
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
