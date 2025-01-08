package com.school.matmassig.recipeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.repository.RecipeRepository;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Integer id, Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setIngredientsRecipeId(recipe.getIngredientsRecipeId());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setUpdatedAt(recipe.getUpdatedAt());
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        recipeRepository.deleteById(id);
    }
}
