package com.school.matmassig.reviewservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByUserId(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getReviewsByRecipeId(Integer recipeId) {
        return reviewRepository.findByRecipeId(recipeId);
    }

    public Review addReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Integer id, Review updatedReview) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setRating(updatedReview.getRating());
                    existingReview.setComment(updatedReview.getComment());
                    return reviewRepository.save(existingReview);
                })
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}
