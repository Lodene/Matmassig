package com.school.matmassig.reviewservice.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.matmassig.reviewservice.model.Review;
import com.school.matmassig.reviewservice.repository.ReviewRepository;

@Service
public class ListenerSerice {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void saveReview(Review review) {
        // Sauvegarder la recette
        Review savedReview = reviewRepository.save(review);
        System.out.println("Recipe saved successfully!" + review);

        System.out.println("Recipe and ingredients saved successfully!");
    }
}