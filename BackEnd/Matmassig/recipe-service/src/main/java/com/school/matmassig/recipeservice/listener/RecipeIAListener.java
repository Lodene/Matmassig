package com.school.matmassig.recipeservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.DeleteRecipeMessage;
import com.school.matmassig.recipeservice.model.RecipeIAMessage;
import com.school.matmassig.recipeservice.model.RecipeIAPaginationMessage;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.service.RecipeIAMessageProcessor;

@Component
public class RecipeIAListener {
    private final RecipeIAMessageProcessor processingService;
    private final ObjectMapper objectMapper;

    public RecipeIAListener(RecipeIAMessageProcessor processingService, ObjectMapper objectMapper) {
        this.processingService = processingService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "recipeIA-queue")
    public void listenToRecipeProcessingQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {
        try {
            // Handle specific cases based on the routing key
            switch (routingKey) {
                case "recipeIA.create":
                    RecipeIAMessage recipeMessageCreate = objectMapper.readValue(message, RecipeIAMessage.class);
                    handleRecipeCreate(recipeMessageCreate);
                    break;

                case "recipeIA.update":
                    RecipeIAMessage recipeMessageUpdate = objectMapper.readValue(message, RecipeIAMessage.class);
                    handleRecipeUpdate(recipeMessageUpdate);
                    break;

                case "recipeIA.delete":
                    RecipeIAMessage recipeMessageDelete = objectMapper.readValue(message,
                            RecipeIAMessage.class);
                    processingService.handleDeleteRecipe(recipeMessageDelete);
                    break;

                case "recipeIA.getbyuser":
                    RecipeIAMessage recipeMessageGetByUser = objectMapper.readValue(message,
                            RecipeIAMessage.class);
                    processingService.handleGetRecipesByUser(recipeMessageGetByUser);
                    break;

                case "recipeIA.getall":
                    RecipeIAMessage recipeMessagegetByRecipe = objectMapper.readValue(message,
                            RecipeIAMessage.class);
                    processingService.handleGetAllRecipeIA(recipeMessagegetByRecipe.getPage(),
                            recipeMessagegetByRecipe.getSize(), recipeMessagegetByRecipe.getEmail());
                    break;

                default:
                    System.err.println("Unknown routing key: " + routingKey);
            }
        } catch (Exception e) {
            System.err.println("Failed to process recipe processing message: " + message);
            e.printStackTrace();
        }
    }

    private void sendErrorToEsb(String email, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendErrorToEsb'");
    }

    private void handleRecipeCreate(RecipeIAMessage recipeMessage) {
        System.out.println("Handling recipe creation for: " + recipeMessage);
        processingService.handleCreateRecipe(recipeMessage);
    }

    private void handleRecipeUpdate(RecipeIAMessage recipeMessage) {
        System.out.println("Handling recipe update for: " + recipeMessage);
        processingService.handleUpdateRecipe(recipeMessage);
    }
}
