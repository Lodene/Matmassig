package com.school.matmassig.inventoryservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.inventoryservice.config.RabbitConfig;
import com.school.matmassig.inventoryservice.model.InventoryItemMessage;
import com.school.matmassig.inventoryservice.service.MessageHandlerService;

@Component
public class InventoryListener {
    private final MessageHandlerService processingService;
    private final ObjectMapper objectMapper;

    public InventoryListener(MessageHandlerService processingService, ObjectMapper objectMapper) {
        this.processingService = processingService;
        this.objectMapper = objectMapper;

    }

    @RabbitListener(queues = "item-queue")
    public void listen(String message, @Header("amqp_receivedRoutingKey") String routingKey) throws Exception {
        try {
            InventoryItemMessage itemMessage = objectMapper.readValue(message, InventoryItemMessage.class);

            switch (routingKey) {
                case "item.create":
                    processingService.handleAddItem(itemMessage);
                    break;
                case "item.delete":
                    processingService.handleDeleteItem(itemMessage);
                    break;
                case "item.update":
                    processingService.handleUpdateItem(itemMessage);
                    break;
                case "item.getbyuser":
                    processingService.handleGetItems(itemMessage);
                    break;
                default:
                    System.err.println("Clé de routage inconnue : " + routingKey);
            }
        } catch (JsonProcessingException e) {
            // Handle the exception, e.g., log it
            e.printStackTrace();
        }
    }
}