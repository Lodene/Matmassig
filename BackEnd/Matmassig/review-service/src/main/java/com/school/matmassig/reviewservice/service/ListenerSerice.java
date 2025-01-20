package com.school.matmassig.reviewservice.service;

import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ListenerSerice {

    private final ReviewRepository reviewRepository;

    public ListenerSerice(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
        System.out.println("Review saved: " + review);
    }

    @Transactional
    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
        System.out.println("Review deleted with ID: " + id);
    }

    @Transactional
    public void updateReview(Integer id, Review updatedReview) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));
        review.setRecipeId(updatedReview.getRecipeId());
        review.setUserId(updatedReview.getUserId());
        review.setRating(updatedReview.getRating());
        review.setComment(updatedReview.getComment());
        reviewRepository.save(review);
        System.out.println("Review updated: " + review);
    }

    public List<Review> getReviewsByUserId(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getReviewsByRecipeId(Integer recipeId) {
        return reviewRepository.findByRecipeId(recipeId);
    }
}
