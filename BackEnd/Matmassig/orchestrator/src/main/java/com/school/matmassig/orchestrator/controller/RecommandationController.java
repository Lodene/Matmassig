package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import com.school.matmassig.orchestrator.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/recommandation")
@CrossOrigin(origins = "http://localhost:8100")
public class RecommandationController {

    private final RabbitMQPublisherService publisherService;
    private final JwtUtils jwtUtils;

    public RecommandationController(RabbitMQPublisherService publisherService, JwtUtils jwtUtils) {
        this.publisherService = publisherService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoint: Demandande de Recommandation
    @GetMapping("/get")
    public ResponseEntity<String> getReviewsByUser(@RequestHeader("Authorization") String authHeader) {
        try {
            System.out.println("DEBUG: Received Authorization header: " + authHeader);

            // Extraire l'email du token
            String email = extractEmailFromToken(authHeader);
            System.out.println("DEBUG: Extracted email from token: " + email);

            // Extraire le userId du token
            Integer userId = extractUserIdFromToken(authHeader);
            System.out.println("DEBUG: Extracted userId from token: " + userId);

            if (email == null || userId == null) {
                System.out.println("ERROR: Failed to extract email or userId from token");
                return ResponseEntity.status(403).body("Invalid token: Unable to extract user information");
            }

            // Préparation de la charge utile à publier
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", userId);

            System.out.println("DEBUG: Payload prepared for RabbitMQ: " + payload);

            // Publier le message dans RabbitMQ
            publisherService.publishMessageWithAdditionalData(EXCHANGE_NAME, "recommandation.getbyuser", payload, email);
            System.out.println("DEBUG: Message published to RabbitMQ for userId: " + userId);

            return ResponseEntity.ok("Get recommendation for user request sent to RabbitMQ");
        } catch (Exception e) {
            System.out.println("ERROR: Exception occurred while processing the request: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    // Méthode pour extraire l'email du token
    private String extractEmailFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getEmailFromToken(token);
    }

    private Integer extractUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Suppression du préfixe "Bearer "
        return jwtUtils.getUserIdFromToken(token);
    }
}
