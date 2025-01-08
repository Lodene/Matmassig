package com.school.matmassig.orchestrator.controller;

import com.school.matmassig.orchestrator.model.Recipe;
import com.school.matmassig.orchestrator.model.Review;
import com.school.matmassig.orchestrator.service.RabbitMQPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.school.matmassig.orchestrator.config.RabbitMQConfig.EXCHANGE_NAME;

@RestController
@RequestMapping("/api/orchestrator")
public class OrchestratorController {

    private final RabbitMQPublisherService publisherService;

    public OrchestratorController(RabbitMQPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<String> sendRecipe(@RequestBody Recipe recipe) {
        publisherService.publishMessage(EXCHANGE_NAME, "recipe.create", recipe);
        return ResponseEntity.ok("Recipe sent to RabbitMQ");
    }

    @PostMapping("/review")
    public ResponseEntity<String> sendReview(@RequestBody Review review) {
        publisherService.publishMessage(EXCHANGE_NAME, "review.create", review);
        return ResponseEntity.ok("Review sent to RabbitMQ");
    }
}
