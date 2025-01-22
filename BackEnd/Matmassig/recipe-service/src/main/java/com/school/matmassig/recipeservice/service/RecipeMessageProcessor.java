package com.school.matmassig.recipeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.DeleteRecipeMessage;
import com.school.matmassig.recipeservice.model.NotificationMessage;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.repository.RecipeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeMessageProcessor {

    private final ListenerSerice service;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RecipeRepository recipeRepository;

    public RecipeMessageProcessor(ListenerSerice service, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper,
            RecipeRepository recipeRepository) {
        this.service = service;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.recipeRepository = recipeRepository;
    }

    public void handleCreateRecipe(RecipeMessage recipeMessage) {
        Integer recipeId = recipeMessage.getRecipe().getId();
        if (recipeId != null && recipeRepository.existsById(recipeId)) {
            sendErrorToEsb(recipeMessage.getEmail(), "Recipe with ID " + recipeId + " already exists.");
            return;
        }

        service.saveRecipeAndIngredients(recipeMessage.getRecipe(), recipeMessage.getIngredients());
        sendNotificationToEsb(recipeMessage.getEmail(), "Recipe created successfully!");
    }

    public void handleDeleteRecipe(DeleteRecipeMessage deleteMessage) {
        Integer recipeId = deleteMessage.getid();
        if (recipeId == null || !recipeRepository.existsById(recipeId)) {
            sendErrorToEsb(deleteMessage.getEmail(), "Recipe with ID " + recipeId + " does not exist.");
            return;
        }

        service.deleteRecipe(recipeId);
        sendNotificationToEsb(deleteMessage.getEmail(), "Recipe deleted successfully!");
    }

    public void handleUpdateRecipe(RecipeMessage recipeMessage) {
        Recipe updatedRecipe = recipeMessage.getRecipe();
        Integer recipeId = updatedRecipe.getId();
        if (recipeId == null || !recipeRepository.existsById(recipeId)) {
            sendErrorToEsb(recipeMessage.getEmail(), "Recipe with ID " + recipeId + " does not exist.");
            return;
        }

        service.updateRecipe(recipeId, updatedRecipe, recipeMessage.getIngredients());
        sendNotificationToEsb(recipeMessage.getEmail(), "Recipe updated successfully!");
    }

    public void handleGetRecipesByUser(DeleteRecipeMessage recipeMessage) {
        Integer userId = recipeMessage.getUserId();
        if (userId == null) {
            sendErrorToEsb(recipeMessage.getEmail(), "User ID is missing.");
            return;
        }

        List<Recipe> userRecipes = recipeRepository.findByUserId(userId);
        sendNotificationToEsb(recipeMessage.getEmail(), "User recipes fetched successfully: " + userRecipes);
    }

    public void handleGetRecipeDetails(DeleteRecipeMessage recipeMessagegetByRecipe) {
        Integer recipeId = recipeMessagegetByRecipe.getRecipeId();
        if (recipeId == null) {
            sendErrorToEsb(recipeMessagegetByRecipe.getEmail(), "Recipe ID is missing.");
            return;
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID " + recipeId));
        sendNotificationToEsb(recipeMessagegetByRecipe.getEmail(), "Recipe details: " + recipe);
    }

    public void handleGetAllRecipes(DeleteRecipeMessage recipeMessage) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        sendNotificationToEsb(recipeMessage.getEmail(), "All recipes fetched successfully: " + allRecipes);
    }

    private void sendNotificationToEsb(String email, String message) {
        if (email == null || email.isEmpty()) {
            System.err.println("Email is null or empty. Skipping notification.");
            return;
        }

        NotificationMessage notification = new NotificationMessage(email, message);
        sendMessageToQueue(notification);
    }

    private void sendErrorToEsb(String email, String errorMessage) {
        if (email == null || email.isEmpty()) {
            System.err.println("Email is null or empty. Skipping error notification.");
            return;
        }

        NotificationMessage errorNotification = new NotificationMessage(email, errorMessage);
        sendMessageToQueue(errorNotification);
    }

    private void sendMessageToQueue(NotificationMessage message) {
        try {
            rabbitTemplate.convertAndSend("app-exchange", "esb.notifications",
                    objectMapper.writeValueAsString(message));
            System.out.println("Message sent to ESB queue: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send message to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleProcessingError(RecipeMessage recipeMessage, String topic, Exception e) {
        System.err.println("Error processing message for topic [" + topic + "]: " + e.getMessage());
        if (recipeMessage != null && recipeMessage.getEmail() != null) {
            sendErrorToEsb(recipeMessage.getEmail(), "Error processing recipe message: " + e.getMessage());
        }
        e.printStackTrace();
    }
}
