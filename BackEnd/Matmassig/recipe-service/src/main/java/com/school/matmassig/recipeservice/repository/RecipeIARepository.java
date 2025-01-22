package com.school.matmassig.recipeservice.repository;

import com.school.matmassig.recipeservice.model.RecipeIA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIARepository extends JpaRepository<RecipeIA, Integer> {
    List<RecipeIA> findByUserId(Integer userId);

    List<RecipeIA> findByRecipeId(Integer userId);

    List<RecipeIA> findByUserIdAndIsFavourite(Integer userId, Boolean isFavourite);

    RecipeIA save(RecipeIA recipe);

    // si un user id et un recipe id existe
    boolean existsByRecipeIdAndUserId(Integer recipeId, Integer userId);

    // delete by id and user id
    void deleteByRecipeIdAndUserId(Integer recipeId, Integer userId);
}