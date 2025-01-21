package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.RecipeMessage;
import com.school.matmassig.orchestrator.model.PaginationRequest;
import com.school.matmassig.orchestrator.model.DeleteRecipeRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.school.matmassig.orchestrator.util.JwtUtils;

import java.util.HashMap;
import java.util.Map;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/recipe")
public class RecipeController {

    private final RabbitMQPublisherService publisherService;

    private final JwtUtils jwtUtils;


    public RecipeController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoint: Création d'une recette
    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody RecipeMessage recipeMessage, @RequestHeader("Authorization") String authHeader) {
        System.out.println("DEBUG: Recipe creation request received");
        System.out.println("DEBUG: RecipeMessage: " + recipeMessage);
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        recipeMessage.getRecipe().setEmail(email);
        recipeMessage.getRecipe().setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.create", recipeMessage);
        return ResponseEntity.ok("Recipe creation request sent to RabbitMQ");
    }

    // Endpoint: Modification d'une recette
    @PutMapping("/update")
    public ResponseEntity<String> updateRecipe(@RequestBody RecipeMessage recipeMessage) {
        System.out.println("DEBUG: Recipe update request received");
        System.out.println("DEBUG: RecipeMessage: " + recipeMessage);
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.update", recipeMessage);
        return ResponseEntity.ok("Recipe update request sent to RabbitMQ");
    }

    // Endpoint: Suppression d'une recette
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRecipe(@RequestBody DeleteRecipeRequest deleteRecipeRequest) {
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.delete", deleteRecipeRequest);
        return ResponseEntity.ok("Recipe delete request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les recettes par userId
    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getRecipesByUser(@PathVariable Integer userId) {
        System.out.println("DEBUG: Get recipes by user request received");
        System.out.println("DEBUG: User ID: " + userId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.getbyuser", userId);
        return ResponseEntity.ok("Get recipes by user request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les recettes par recipeId
    @GetMapping("/getbyrecipe/{recipeId}")
    public ResponseEntity<String> getRecipesByRecipe(@PathVariable Integer recipeId) {
        System.out.println("DEBUG: Get recipes by recipe request received");
        System.out.println("DEBUG: Recipe ID: " + recipeId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.getbyrecipe", recipeId);
        return ResponseEntity.ok("Get recipes by recipe request sent to RabbitMQ");
    }

    @GetMapping("/getall")
    public ResponseEntity<String> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("DEBUG: Get all recipes request received");
        System.out.println("DEBUG: Page: " + page + ", Size: " + size);

        // Encapsuler les informations de pagination dans un objet ou directement dans un Map
        PaginationRequest paginationRequest = new PaginationRequest(page, size);

        // Publier dans RabbitMQ
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.getall", paginationRequest);
        return ResponseEntity.ok("Get all recipes request sent to RabbitMQ with pagination");
    }

    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getEmailFromToken(token);
    }

    private Integer extractUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getUserIdFromToken(token);
    }
}

