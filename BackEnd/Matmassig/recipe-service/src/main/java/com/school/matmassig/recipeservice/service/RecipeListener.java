package com.school.matmassig.recipeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.recipeservice.config.RabbitMQConfig;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.repository.RecipeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeListener {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.RECIPE_QUEUE)
    public void receiveRecipeMessage(String message) {
        try {
            // Désérialiser le message JSON en objet Recipe
            Recipe recipe = objectMapper.readValue(message, Recipe.class);

            // Sauvegarder dans la base de données
            recipeRepository.save(recipe);

            System.out.println("Recipe saved successfully: " + recipe);
        } catch (Exception e) {
            System.err.println("Failed to process recipe message: " + message);
            e.printStackTrace();
        }
    }
}
