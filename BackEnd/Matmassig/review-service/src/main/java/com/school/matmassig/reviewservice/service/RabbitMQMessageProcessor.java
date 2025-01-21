package com.school.matmassig.reviewservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.reviewservice.model.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitMQMessageProcessor {

    private final ListenerSerice listenerService;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMessageProcessor(ListenerSerice listenerService, ObjectMapper objectMapper,
            RabbitTemplate rabbitTemplate) {
        this.listenerService = listenerService;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(String message, String routingKey) {
        try {
            Object result = null;

            switch (routingKey) {
                case "review.create":
                    result = processCreateReview(message);
                    break;
                case "review.delete":
                    result = processDeleteReview(message);
                    break;
                case "review.update":
                    result = processUpdateReview(message);
                    break;
                case "review.getbyuser":
                    result = processGetUserReviews(message);
                    break;
                case "review.getbyrecipe":
                    result = processGetRecipeReviews(message);
                    break;
                default:
                    System.err.println("Unknown routing key: " + routingKey);
            }

            if (result != null) {
                sendToEsbQueue(result);
            }

        } catch (DataIntegrityViolationException e) {
            // Gestion spécifique de la contrainte de clé étrangère
            String errorMessage = "Invalid recipe ID or recipe does not exist.";
            String email = extractEmail(message);
            sendErrorToEsb(email, errorMessage);

            // Facultatif : un log simple pour suivre l'erreur sans exposer toute la pile
            System.err.println("Constraint violation: " + errorMessage);
        } catch (Exception e) {
            // Gestion générique pour d'autres erreurs
            System.err.println("Error processing message with routing key [" + routingKey + "]: " + e.getMessage());
            // Pas de printStackTrace pour éviter d'exposer des informations sensibles
        }
    }

    private Map<String, Object> processCreateReview(String message) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Review review = objectMapper.readValue(message, Review.class);

        if (review.getRecipeId() == null || review.getRecipeId() <= 0) {
            sendErrorToEsb(extractEmail(message), "Invalid recipe ID: " + review.getRecipeId());
            throw new IllegalArgumentException("Invalid recipe ID.");
        }

        listenerService.saveReview(review);
        result.put("email", extractEmail(message));
        result.put("message", "your review has been created");
        System.out.println("Review created: " + review);
        return result;
    }

    private Map<String, Object> processDeleteReview(String message) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Review review = objectMapper.readValue(message, Review.class);

        if (review.getId() == null) {
            throw new IllegalArgumentException("ID is required for deleting a review.");
        }

        listenerService.deleteReview(review.getId());
        result.put("email", extractEmail(message));
        result.put("message", "your review has been deleted");
        System.out.println("Review deleted with ID: " + review.getId() + " and user ID: " + review.getUserId());

        return result;
    }

    private Map<String, Object> processUpdateReview(String message) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Review updatedReview = objectMapper.readValue(message, Review.class);

        if (updatedReview.getRecipeId() == null || updatedReview.getRecipeId() <= 0) {
            sendErrorToEsb(extractEmail(message), "Invalid recipe ID: " + updatedReview.getRecipeId());
            throw new IllegalArgumentException("Invalid recipe ID.");
        }

        listenerService.updateReview(updatedReview.getId(), updatedReview);
        result.put("email", extractEmail(message));
        result.put("message", updatedReview);
        System.out.println("Review updated: " + updatedReview);
        return result;
    }

    private Map<String, Object> processGetUserReviews(String message) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByUserId(userId);
        result.put("email", extractEmail(message));
        result.put("message", reviews);
        System.out.println("Reviews for user " + userId + ": " + reviews);
        return result;
    }

    private Map<String, Object> processGetRecipeReviews(String message) {
        Map<String, Object> result = new HashMap<>();
        Integer recipeId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByRecipeId(recipeId);
        result.put("email", extractEmail(message));
        result.put("message", reviews);
        System.out.println("Reviews for recipe " + recipeId + ": " + reviews);
        return result;
    }

    private void sendToEsbQueue(Object result) {
        try {
            String resultMessage = objectMapper.writeValueAsString(result);

            if (resultMessage == null || resultMessage.isEmpty()) {
                System.err.println("Result message is null or empty. Skipping send.");
                return;
            }

            rabbitTemplate.convertAndSend("esb-queue", resultMessage);
            System.out.println("Result sent to ESB queue: " + resultMessage);
        } catch (Exception e) {
            System.err.println("Failed to send result to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendErrorToEsb(String email, String errorMessage) {
        try {
            System.out.println("Sending error notification to ESB queue: " + errorMessage);
            Map<String, String> errorPayload = new HashMap<>();
            errorPayload.put("email", email);
            errorPayload.put("message", errorMessage);

            String errorMessageJson = objectMapper.writeValueAsString(errorPayload);
            rabbitTemplate.convertAndSend("esb-queue", errorMessageJson);
            System.out.println("Error notification sent to ESB queue: " + errorMessageJson);
        } catch (Exception e) {
            System.err.println("Failed to send error notification to ESB queue: " + e.getMessage());
        }
    }

    private String extractEmail(String message) {
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(message, Map.class);
            return (String) jsonMap.get("email");
        } catch (Exception e) {
            System.err.println("Failed to extract email from message: " + e.getMessage());
            return null;
        }
    }
}
