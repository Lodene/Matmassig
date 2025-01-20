package com.school.matmassig.reviewservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.reviewservice.model.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

        } catch (Exception e) {
            System.err.println("Error processing message with routing key [" + routingKey + "]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Review processCreateReview(String message) throws Exception {
        Review review = objectMapper.readValue(message, Review.class);
        listenerService.saveReview(review);
        System.out.println("Review created: " + review);
        return review;
    }

    private Map<String, Integer> processDeleteReview(String message) throws Exception {
        // Désérialisez le message JSON en un objet Review
        Review review = objectMapper.readValue(message, Review.class);

        // Assurez-vous que l'ID n'est pas null
        if (review.getId() == null) {
            throw new IllegalArgumentException("ID is required for deleting a review.");
        }

        // Retournez une Map contenant l'ID et l'userId
        Map<String, Integer> response = new HashMap<>();
        response.put("id", review.getId());
        response.put("userId", review.getUserId());

        // Supprimez la review en utilisant l'ID
        listenerService.deleteReview(review.getId());
        System.out.println("Review deleted with ID: " + review.getId() + " and user ID: " + review.getUserId());

        return response;
    }

    private Review processUpdateReview(String message) throws Exception {
        Review updatedReview = objectMapper.readValue(message, Review.class);
        listenerService.updateReview(updatedReview.getId(), updatedReview);
        System.out.println("Review updated: " + updatedReview);
        return updatedReview;
    }

    private List<Review> processGetUserReviews(String message) {
        Integer userId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByUserId(userId);
        System.out.println("Reviews for user " + userId + ": " + reviews);
        return reviews;
    }

    private List<Review> processGetRecipeReviews(String message) {
        Integer recipeId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByRecipeId(recipeId);
        System.out.println("Reviews for recipe " + recipeId + ": " + reviews);
        return reviews;
    }

    private void sendToEsbQueue(Object result) {
        try {
            String resultMessage = objectMapper.writeValueAsString(result);
            rabbitTemplate.convertAndSend("esb-queue", resultMessage);
            System.out.println("Result sent to ESB queue: " + resultMessage);
        } catch (Exception e) {
            System.err.println("Failed to send result to ESB queue: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
