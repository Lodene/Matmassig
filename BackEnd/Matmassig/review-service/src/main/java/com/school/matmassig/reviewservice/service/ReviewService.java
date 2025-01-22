package com.school.matmassig.reviewservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.school.matmassig.reviewservice.model.MessageReview;
import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.api.url:http://api.example.com/recommendation}")
    private String aiApiUrl;

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
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Integer id, Review updatedReview) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setRating(updatedReview.getRating());
                    existingReview.setComment(updatedReview.getComment());
                    Review savedReview = reviewRepository.save(existingReview);
                    return savedReview;
                })
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    void sendToAI(MessageReview reviewMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MessageReview> entity = new HttpEntity<>(reviewMessage, headers);

            restTemplate.postForEntity(aiApiUrl, entity, String.class);
            log.info("Successfully sent review to AI API: {}", reviewMessage);
        } catch (Exception e) {
            log.error("Failed to send review to AI API: {}", reviewMessage, e);
        }
    }
}
