package com.school.matmassig.recipeservice.repository;

import com.school.matmassig.recipeservice.model.IngredientsRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRecipeRepository extends JpaRepository<IngredientsRecipe, Integer> {
}
