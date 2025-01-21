package com.school.matmassig.recipeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.NotificationMessage;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.repository.RecipeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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

    public void processRecipeMessage(RecipeMessage recipeMessage) {
        try {
            System.out.println("Processing recipe: " + recipeMessage.getRecipe().getTitle());
            System.out.println("User email: " + recipeMessage.getEmail());
            System.out.println("Ingredients: " + recipeMessage.getIngredients());

            // Vérification que le recipe_id existe
            Integer recipeId = recipeMessage.getRecipe().getId();
            if (recipeId != null && !recipeRepository.existsById(recipeId)) {
                sendErrorToEsb(recipeMessage.getEmail(), "Le recipe avec ID " + recipeId + " n'existe pas.");
                return;
            }

            // Sauvegarder la recette et les ingrédients
            service.saveRecipeAndIngredients(recipeMessage.getRecipe(), recipeMessage.getIngredients());
            System.out.println("Recipe and ingredients saved successfully!");

            // Envoyer une notification à l'ESB
            sendNotificationToEsb(recipeMessage.getEmail());
        } catch (Exception e) {
            System.err.println("Error while processing recipe message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendNotificationToEsb(String email) {
        if (email == null || email.isEmpty()) {
            System.err.println("Email is null or empty. Skipping notification.");
            return;
        }

        NotificationMessage notification = new NotificationMessage(
                email,
                "Le plat a été créé");

        try {
            rabbitTemplate.convertAndSend("esb-queue", objectMapper.writeValueAsString(notification));
            System.out.println("Notification sent to ESB queue: " + notification);
        } catch (Exception e) {
            System.err.println("Failed to send notification to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendErrorToEsb(String email, String errorMessage) {
        if (email == null || email.isEmpty()) {
            System.err.println("Email is null or empty. Skipping error notification.");
            return;
        }

        NotificationMessage errorNotification = new NotificationMessage(
                email,
                errorMessage);

        try {
            rabbitTemplate.convertAndSend("esb-queue", objectMapper.writeValueAsString(errorNotification));
            System.out.println("Error notification sent to ESB queue: " + errorNotification);
        } catch (Exception e) {
            System.err.println("Failed to send error notification to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
