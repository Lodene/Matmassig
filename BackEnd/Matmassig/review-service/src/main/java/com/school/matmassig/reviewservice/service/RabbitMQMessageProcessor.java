package com.school.matmassig.reviewservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.matmassig.reviewservice.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQMessageProcessor {

    private final ListenerSerice listenerService;
    private final ObjectMapper objectMapper;

    public RabbitMQMessageProcessor(ListenerSerice listenerService, ObjectMapper objectMapper) {
        this.listenerService = listenerService;
        this.objectMapper = objectMapper;
    }

    public void processMessage(String message, String routingKey) {
        try {
            switch (routingKey) {
                case "review.create":
                    processCreateReview(message);
                    break;
                case "review.delete":
                    processDeleteReview(message);
                    break;
                case "review.update":
                    processUpdateReview(message);
                    break;
                case "review.getUser":
                    processGetUserReviews(message);
                    break;
                case "review.getRecipe":
                    processGetRecipeReviews(message);
                    break;
                default:
                    System.err.println("Unknown routing key: " + routingKey);
            }
        } catch (Exception e) {
            System.err.println("Error processing message with routing key [" + routingKey + "]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processCreateReview(String message) throws Exception {
        Review review = objectMapper.readValue(message, Review.class);
        listenerService.saveReview(review);
        System.out.println("Review created: " + review);
    }

    private void processDeleteReview(String message) {
        Integer id = Integer.parseInt(message);
        listenerService.deleteReview(id);
        System.out.println("Review deleted with ID: " + id);
    }

    private void processUpdateReview(String message) throws Exception {
        Review updatedReview = objectMapper.readValue(message, Review.class);
        listenerService.updateReview(updatedReview.getId(), updatedReview);
        System.out.println("Review updated: " + updatedReview);
    }

    private void processGetUserReviews(String message) {
        Integer userId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByUserId(userId);
        System.out.println("Reviews for user " + userId + ": " + reviews);
    }

    private void processGetRecipeReviews(String message) {
        Integer recipeId = Integer.parseInt(message);
        List<Review> reviews = listenerService.getReviewsByRecipeId(recipeId);
        System.out.println("Reviews for recipe " + recipeId + ": " + reviews);
    }
}
