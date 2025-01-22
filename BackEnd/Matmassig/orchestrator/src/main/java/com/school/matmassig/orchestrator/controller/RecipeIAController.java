package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.RecipeMessage;
import com.school.matmassig.orchestrator.model.PaginationRequest;
import com.school.matmassig.orchestrator.model.RecipeIA;
import com.school.matmassig.orchestrator.model.DeleteRecipeRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.school.matmassig.orchestrator.util.JwtUtils;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

import java.util.HashMap;
import java.util.Map;;

@RestController
@RequestMapping("/api/orchestrator/recipeIA")
@CrossOrigin(origins = "http://localhost:8100")
public class RecipeIAController {

    private final RabbitMQPublisherService publisherService;

    private final JwtUtils jwtUtils;

    public RecipeIAController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoint: Création d'une recette
    @PostMapping("/add")
    public ResponseEntity<String> createRecipeIA(@RequestBody RecipeIA recipeMessage,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println("DEBUG: Recipe IA creation request received");
        System.out.println("DEBUG: RecipeIAMessage: " + recipeMessage);
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        recipeMessage.setEmail(email);
        recipeMessage.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipeIA.create", recipeMessage);
        return ResponseEntity.ok("Recipe creation request sent to RabbitMQ");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRecipeIA(@RequestBody RecipeIA recipeMessage,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println("DEBUG: Recipe IA creation request received");
        System.out.println("DEBUG: RecipeIAMessage: " + recipeMessage);
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        recipeMessage.setEmail(email);
        recipeMessage.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipeIA.update", recipeMessage);
        return ResponseEntity.ok("Recipe update request sent to RabbitMQ");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRecipeIA(@RequestBody RecipeIA recipeMessage,
            @RequestHeader("Authorization") String authHeader) {
        System.out.println("DEBUG: Recipe IA creation request received");
        System.out.println("DEBUG: RecipeIAMessage: " + recipeMessage);
        String email = extractEmailFromToken(authHeader);
        Integer userId = extractUserIdFromToken(authHeader);
        recipeMessage.setEmail(email);
        recipeMessage.setUserId(userId);
        publisherService.publishMessage(EXCHANGE_NAME, "recipeIA.delete", recipeMessage);
        return ResponseEntity.ok("Recipe delete request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les recettes par userId
    @GetMapping("/getbyuser")
    public ResponseEntity<String> getRecipesByUser(@RequestHeader("Authorization") String authHeader) {
        System.out.println("DEBUG: Get recipes IA by user request received");
        Integer userId = extractUserIdFromToken(authHeader);
        System.out.println("DEBUG: User ID: " + userId);
        String email = extractEmailFromToken(authHeader);
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        publisherService.publishMessageWithAdditionalData(EXCHANGE_NAME, "recipeIA.getbyuser", payload, email);
        return ResponseEntity.ok("Get recipes by user request sent to RabbitMQ");
    }

    @GetMapping("/getall")
    public ResponseEntity<String> getAllRecipes(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("DEBUG: Get all recipeAI request received");
        System.out.println("DEBUG: Page: " + page + ", Size: " + size);
        String email = extractEmailFromToken(authHeader);
        // Encapsuler les informations de pagination dans un objet ou directement dans un Map
        PaginationRequest paginationRequest = new PaginationRequest(page, size);
        paginationRequest.setEmail(email);
        // Publier dans RabbitMQ
        publisherService.publishMessage(EXCHANGE_NAME, "recipeIA.getall", paginationRequest);
        return ResponseEntity.ok("Get all recipeIA request sent to RabbitMQ with pagination");
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
