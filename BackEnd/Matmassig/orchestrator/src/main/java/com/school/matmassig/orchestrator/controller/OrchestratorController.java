package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Ingredient;
import com.school.matmassig.orchestrator.model.Recipe;
import com.school.matmassig.orchestrator.model.RecipeMessage;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator")
public class OrchestratorController {

    private final RabbitMQPublisherService publisherService;

    public OrchestratorController(RabbitMQPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<String> sendRecipe(@RequestBody RecipeMessage recipeMessage) {
        System.out.println("Received RecipeMessage: " + recipeMessage);
        System.out.println("Recipe: " + recipeMessage.getRecipe());
        System.out.println("Ingredients: " + recipeMessage.getIngredients());

        publisherService.publishMessage(EXCHANGE_NAME, "recipe.create", recipeMessage);
        return ResponseEntity.ok("Recipe with ingredients sent to RabbitMQ");
    }
}
