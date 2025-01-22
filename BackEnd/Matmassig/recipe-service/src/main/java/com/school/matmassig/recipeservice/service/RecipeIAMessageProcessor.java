package com.school.matmassig.recipeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.DeleteRecipeMessage;
import com.school.matmassig.recipeservice.model.NotificationMessage;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.model.RecipeIA;
import com.school.matmassig.recipeservice.model.RecipeIAMessage;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.repository.RecipeIARepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIAMessageProcessor {

    private final ListenerRecipeIAService service;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final RecipeIARepository recipeRepository;

    public RecipeIAMessageProcessor(ListenerRecipeIAService service, RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper,
            RecipeIARepository recipeRepository) {
        this.service = service;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.recipeRepository = recipeRepository;
    }

    public void handleCreateRecipe(RecipeIAMessage recipeMessage) {
        Integer recipeId = recipeMessage.getRecipeIA_id();
        Integer userId = recipeMessage.getUser_id();
        if (recipeId != null && recipeRepository.existsByRecipeIdAndUserId(recipeId, userId)) {
            sendErrorToEsb(recipeMessage.getEmail(), "Recipe with ID " + recipeId + " already exists.");
            return;
        }

        RecipeIA recipe = new RecipeIA(recipeMessage.getUser_id(), recipeMessage.getRecipeIA_id(),
                recipeMessage.getIs_Favourite());
        service.addRecipeIA(recipe);
        sendNotificationToEsb(recipeMessage.getEmail(), "Recipe IA created successfully!");
    }

    public void handleDeleteRecipe(RecipeIAMessage deleteMessage) {
        Integer recipeId = deleteMessage.getId();
        if (recipeId == null || !recipeRepository.existsByRecipeIdAndUserId(recipeId, deleteMessage.getUser_id())) {
            sendErrorToEsb(deleteMessage.getEmail(), "Recipe with ID " + recipeId + " does not exist.");
            return;
        }

        service.deleteRecipe(recipeId, deleteMessage.getUser_id());
        sendNotificationToEsb(deleteMessage.getEmail(), "Recipe IA deleted successfully!");
    }

    public void handleUpdateRecipe(RecipeIAMessage recipeMessage) {
        Integer recipeId = recipeMessage.getRecipeIA_id();
        Integer userId = recipeMessage.getUser_id();
        if (recipeId == null || !recipeRepository.existsByRecipeIdAndUserId(recipeId, userId)) {
            sendErrorToEsb(recipeMessage.getEmail(), "Recipe with ID " + recipeId + " does not exist.");
            return;
        }

        RecipeIA recipe = new RecipeIA(userId, recipeId, recipeMessage.getIs_Favourite());
        service.updateRecipe(recipe);
        sendNotificationToEsb(recipeMessage.getEmail(), "Recipe IA updated successfully!");
    }

    public void handleGetRecipesByUser(RecipeIAMessage recipeMessage) {
        if (recipeMessage.getUser_id() == null) {
            sendErrorToEsb(recipeMessage.getEmail(), "User ID is missing.");
            return;
        }
        Integer userId = recipeMessage.getUser_id();

        List<RecipeIA> userRecipes = recipeRepository.findByUserId(userId);
        sendNotificationToEsb(recipeMessage.getEmail(), "User recipes IA fetched successfully: " + userRecipes);
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
            rabbitTemplate.convertAndSend("esb-queue", objectMapper.writeValueAsString(message));
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
