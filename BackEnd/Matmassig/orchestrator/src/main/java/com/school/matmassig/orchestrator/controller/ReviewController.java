package com.school.matmassig.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.orchestrator.model.Review;
import com.school.matmassig.orchestrator.model.DeleteReviewRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import com.school.matmassig.orchestrator.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/review")
public class ReviewController {

    private final RabbitMQPublisherService publisherService;
    private final JwtUtils jwtUtils;

    public ReviewController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoint: Création d'une review
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody Review review, @RequestHeader("Authorization") String authHeader) {
        System.out.println("Received request to create review: " + review);

        // Vérification du header Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("Authorization header is missing or invalid: " + authHeader);
            return ResponseEntity.status(403).body("Authorization header is missing or invalid.");
        }

        try {
            // Extraction du token
            String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
            System.out.println("Extracted token: " + token);

            // Extraction de l'email depuis le token
            String email = jwtUtils.getEmailFromToken(token);
            System.out.println("Extracted email from token: " + email);

            // Ajout de l'email au commentaire pour vérification
            review.setEmail(email);
            System.out.println("Updated review with email: " + review);

            // Publication du message dans RabbitMQ
            publisherService.publishMessage(EXCHANGE_NAME, "review.create", review);
            System.out.println("Message sent to RabbitMQ for review creation.");
            return ResponseEntity.ok("Review creation request sent to RabbitMQ with email.");
        } catch (Exception e) {
            System.err.println("Error processing create review request: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error while creating review.");
        }
    }

    // Endpoint: Modification d'une review
    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody Review review, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        review.setEmail(email);
        publisherService.publishMessage(EXCHANGE_NAME, "review.update", review);
        return ResponseEntity.ok("Review update request sent to RabbitMQ");
    }

    // Endpoint: Suppression d'une review
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReview(@RequestBody Review review, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        review.setEmail(email);
        publisherService.publishMessage(EXCHANGE_NAME, "review.delete", review);
        return ResponseEntity.ok("Review delete request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les reviews par userId
    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getReviewsByUser(@PathVariable Integer userId, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        publisherService.publishMessageWithAdditionalData(EXCHANGE_NAME, "review.getbyuser", payload, email);
        return ResponseEntity.ok("Get reviews by user request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les reviews par recipeId
    @GetMapping("/getbyrecipe/{recipeId}")
    public ResponseEntity<String> getReviewsByRecipe(@PathVariable Integer recipeId, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        System.out.println("Request by email: " + email);
        publisherService.publishMessage(EXCHANGE_NAME, "review.getbyrecipe", recipeId);
        return ResponseEntity.ok("Get reviews by recipe request sent to RabbitMQ");
    }

    // Méthode pour extraire l'email du token
    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getEmailFromToken(token);
    }
}
