package com.school.matmassig.reviewservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.matmassig.reviewservice.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserId(Integer userId);

    List<Review> findByRecipeId(Integer recipeId);
}
