package com.school.matmassig.recipeservice.repository;

import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsRecipeRepository extends JpaRepository<IngredientsRecipe, Integer> {
    List<IngredientsRecipe> findByRecipeId(Integer recipeId);

    void deleteByRecipeId(Integer id);
    void deleteAllByRecipeId(Integer recipeId);

}