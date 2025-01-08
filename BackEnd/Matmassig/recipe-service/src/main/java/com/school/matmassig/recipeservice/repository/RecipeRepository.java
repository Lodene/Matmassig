package com.school.matmassig.recipeservice.repository;

import com.school.matmassig.recipeservice.model.Recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUserId(Integer userId);
}