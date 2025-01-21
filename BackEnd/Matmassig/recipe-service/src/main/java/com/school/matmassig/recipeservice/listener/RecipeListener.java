package com.school.matmassig.recipeservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.service.RecipeMessageProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RecipeListener {

    private final RecipeMessageProcessor processingService;
    private final ObjectMapper objectMapper;

    public RecipeListener(RecipeMessageProcessor processingService, ObjectMapper objectMapper) {
        this.processingService = processingService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "recipe-queue")
    public void listenToRecipeProcessingQueue(String message) {
        try {
            RecipeMessage recipeMessage = objectMapper.readValue(message, RecipeMessage.class);
            processingService.processRecipeMessage(recipeMessage);
        } catch (Exception e) {
            System.err.println("Failed to process recipe processing message: " + message);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "esb-queue")
    public void listenToEsbNotificationsQueue(String message) {
        try {
            // Handle ESB notifications if required.
            System.out.println("ESB Notification received: " + message);
        } catch (Exception e) {
            System.err.println("Failed to process ESB notification message: " + message);
            e.printStackTrace();
        }
    }
}