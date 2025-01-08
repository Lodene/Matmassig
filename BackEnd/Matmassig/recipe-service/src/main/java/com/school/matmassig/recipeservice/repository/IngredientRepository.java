package com.school.matmassig.recipeservice.repository;

import com.school.matmassig.recipeservice.model.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
