package com.school.matmassig.reviewservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get reviews by userId
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable Integer userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    // Get reviews by recipeId
    @GetMapping("/recipe/{recipeId}")
    public List<Review> getReviewsByRecipeId(@PathVariable Integer recipeId) {
        return reviewService.getReviewsByRecipeId(recipeId);
    }

    // Add a review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewService.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok(updatedReview);
    }
}
