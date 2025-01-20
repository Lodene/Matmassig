package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Review;
import com.school.matmassig.orchestrator.model.DeleteReviewRequest;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator/review")
@CrossOrigin(origins = "http://localhost:8100")
public class ReviewController {

    private final RabbitMQPublisherService publisherService;

    public ReviewController(RabbitMQPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // Endpoint: Création d'une review
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody Review review) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.create", review);
        return ResponseEntity.ok("Review creation request sent to RabbitMQ");
    }

    // Endpoint: Modification d'une review
    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.update", review);
        return ResponseEntity.ok("Review update request sent to RabbitMQ");
    }

    // Endpoint: Suppression d'une review
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReview(@RequestBody DeleteReviewRequest deleteReviewRequest) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.delete", deleteReviewRequest);
        return ResponseEntity.ok("Review delete request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les reviews par userId
    @GetMapping("/getbyuser/{userId}")
    public ResponseEntity<String> getReviewsByUser(@PathVariable Integer userId) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.getbyuser", userId);
        return ResponseEntity.ok("Get reviews by user request sent to RabbitMQ");
    }

    // Endpoint: Récupérer les reviews par recipeId
    @GetMapping("/getbyrecipe/{recipeId}")
    public ResponseEntity<String> getReviewsByRecipe(@PathVariable Integer recipeId) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.getbyrecipe", recipeId);
        return ResponseEntity.ok("Get reviews by recipe request sent to RabbitMQ");
    }
}
