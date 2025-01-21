package com.school.matmassig.reviewservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.reviewservice.model.MessageReview;
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
            MessageReview reviewMessage = objectMapper.readValue(message,
                    MessageReview.class);

            switch (routingKey) {
                case "review.create":
                    result = processCreateReview(reviewMessage);
                    break;
                case "review.delete":
                    result = processDeleteReview(reviewMessage);
                    break;
                case "review.update":
                    result = processUpdateReview(reviewMessage);
                    break;
                case "review.getbyuser":
                    result = processGetUserReviews(reviewMessage);
                    break;
                case "review.getbyrecipe":
                    result = processGetRecipeReviews(reviewMessage);
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

    private Map<String, Object> processCreateReview(MessageReview message) throws Exception {

        if (message.getRecipeId() == null || message.getRecipeId() <= 0) {
            sendErrorToEsb(message.getEmail(), "Invalid recipe ID: " + message.getRecipeId());
            throw new IllegalArgumentException("Invalid recipe ID.");
        }

        Review review = new Review();
        review.setRecipeId(message.getRecipeId());
        review.setUserId(message.getUserId());
        review.setRating(message.getRating());
        review.setComment(message.getComment());
        review.setCreatedAt(message.getCreatedAt());

        listenerService.saveReview(review);

        Map<String, Object> result = new HashMap<>();
        result.put("email", message.getEmail());
        result.put("message", "your review has been created");
        System.out.println("Review created: " + review);
        return result;
    }

    private Map<String, Object> processDeleteReview(MessageReview message) throws Exception {
        if (message.getId() == null) {
            throw new IllegalArgumentException("ID is required for deleting a review.");
        }

        listenerService.deleteReview(message.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("email", message.getEmail());
        result.put("message", "your review has been deleted");
        System.out.println("Review deleted with ID: " + message.getId() + " and user ID: " + message.getUserId());

        return result;
    }

    private Map<String, Object> processUpdateReview(MessageReview message) throws Exception {
        if (message.getRecipeId() == null || message.getRecipeId() <= 0) {
            sendErrorToEsb(message.getEmail(), "Invalid recipe ID: " + message.getRecipeId());
            throw new IllegalArgumentException("Invalid recipe ID.");
        }

        Review review = new Review();
        review.setRecipeId(message.getRecipeId());
        review.setUserId(message.getUserId());
        review.setRating(message.getRating());
        review.setComment(message.getComment());
        review.setCreatedAt(message.getCreatedAt());
        listenerService.updateReview(message.getId(), review);

        Map<String, Object> result = new HashMap<>();
        result.put("email", message.getEmail());
        result.put("message", "Review updated successfully");
        System.out.println("Review updated: " + message.getId());
        return result;
    }

    private Map<String, Object> processGetUserReviews(MessageReview message) {
        Integer userId = message.getUserId();
        List<Review> reviews = listenerService.getReviewsByUserId(userId);

        // Convertir la liste des reviews en une structure sérialisable
        String reviewsJson;
        try {
            reviewsJson = objectMapper.writeValueAsString(reviews);
        } catch (Exception e) {
            System.err.println(
                    "Erreur lors de la sérialisation des reviews pour l'utilisateur " + userId + ": " + e.getMessage());
            reviewsJson = "[]"; // Liste vide en cas d'erreur
        }

        Map<String, Object> result = new HashMap<>();
        result.put("email", message.getEmail());
        result.put("message", reviewsJson); // Insérer le JSON au lieu de la liste brute
        System.out.println("Reviews for user " + userId + ": " + reviewsJson);
        return result;
    }

    private Map<String, Object> processGetRecipeReviews(MessageReview message) {
        List<Review> reviews = listenerService.getReviewsByRecipeId(message.getRecipeId());

        // Convertir la liste des reviews en une structure sérialisable
        String reviewsJson;
        try {
            reviewsJson = objectMapper.writeValueAsString(reviews);
        } catch (Exception e) {
            System.err.println("Erreur lors de la sérialisation des reviews pour la recette " + message.getRecipeId()
                    + ": " + e.getMessage());
            reviewsJson = "[]"; // Liste vide en cas d'erreur
        }

        Map<String, Object> result = new HashMap<>();
        result.put("email", message.getEmail());
        result.put("message", reviewsJson); // Insérer le JSON au lieu de la liste brute
        System.out.println("Reviews for recipe " + message.getRecipeId() + ": " + reviewsJson);
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