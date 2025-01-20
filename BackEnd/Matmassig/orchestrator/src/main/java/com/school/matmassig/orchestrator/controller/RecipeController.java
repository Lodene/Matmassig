package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Review;
import com.school.matmassig.orchestrator.model.RecipeMessage;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator")
public class RecipeController {

    private final RabbitMQPublisherService publisherService;

    public RecipeController(RabbitMQPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<String> sendRecipe(@RequestBody RecipeMessage recipeMessage) {
        System.out.println("DEBUG: Requete reçu");
        System.out.println("DEBUG: RecipeMessage received: " + recipeMessage);
        System.out.println("DEBUG: Recipe part: " + recipeMessage.getRecipe());
        System.out.println("DEBUG: Ingredients part: " + recipeMessage.getIngredients());

        publisherService.publishMessage(EXCHANGE_NAME, "recipe.create", recipeMessage);
        return ResponseEntity.ok("Recipe with ingredients sent to RabbitMQ");
    }

    @PostMapping("/review")
    public ResponseEntity<String> sendReview(@RequestBody Review review) {
        System.out.println("DEBUG: Review received: " + review);
        publisherService.publishMessage(EXCHANGE_NAME, "review.create", review);
        return ResponseEntity.ok("Review sent to RabbitMQ");
    }
}
