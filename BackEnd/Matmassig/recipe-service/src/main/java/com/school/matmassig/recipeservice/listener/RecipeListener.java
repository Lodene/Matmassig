package com.school.matmassig.recipeservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.model.DeleteRecipeMessage;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.service.RecipeMessageProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
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
    public void listenToRecipeProcessingQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {
        try {
            // Handle specific cases based on the routing key
            switch (routingKey) {
                case "recipe.create":
                    RecipeMessage recipeMessageCreate = objectMapper.readValue(message, RecipeMessage.class);
                    handleRecipeCreate(recipeMessageCreate);
                    break;

                case "recipe.update":
                    RecipeMessage recipeMessageUpdate = objectMapper.readValue(message, RecipeMessage.class);
                    handleRecipeUpdate(recipeMessageUpdate);
                    break;

                case "recipe.delete":
                    DeleteRecipeMessage recipeMessageDelete = objectMapper.readValue(message,
                            DeleteRecipeMessage.class);
                    processingService.handleDeleteRecipe(recipeMessageDelete);
                    break;

                case "recipe.getbyuser":
                    DeleteRecipeMessage recipeMessageGetByUser = objectMapper.readValue(message,
                            DeleteRecipeMessage.class);
                    processingService.handleGetRecipesByUser(recipeMessageGetByUser);
                    break;

                case "recipe.getbyrecipe":
                    DeleteRecipeMessage recipeMessagegetByRecipe = objectMapper.readValue(message,
                            DeleteRecipeMessage.class);
                    processingService.handleGetRecipeDetails(recipeMessagegetByRecipe);
                    break;
                case "recipe.getall":
                    DeleteRecipeMessage recipeMessageGetAll = objectMapper.readValue(message,
                            DeleteRecipeMessage.class);
                    processingService.handleGetAllRecipes(recipeMessageGetAll);
                    break;

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

    private void handleRecipeCreate(RecipeMessage recipeMessage) {
        System.out.println("Handling recipe creation for: " + recipeMessage);
        processingService.handleCreateRecipe(recipeMessage);
    }

    private void handleRecipeUpdate(RecipeMessage recipeMessage) {
        System.out.println("Handling recipe update for: " + recipeMessage);
        processingService.handleUpdateRecipe(recipeMessage);
    }

    }

    

    
            

    
    
            
    
            

    
    
    