package com.school.matmassig.recipeservice.service;

import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.repository.IngredientsRecipeRepository;
import com.school.matmassig.recipeservice.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ListenerSerice {

    private final RecipeRepository recipeRepository;
    private final IngredientsRecipeRepository ingredientsRecipeRepository;

    public ListenerSerice(RecipeRepository recipeRepository, IngredientsRecipeRepository ingredientsRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientsRecipeRepository = ingredientsRecipeRepository;
    }

    @Transactional
    public void saveRecipeAndIngredients(Recipe recipe, List<IngredientsRecipe> ingredientsRecipes) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        System.out.println("Recipe saved successfully: " + savedRecipe);

        for (IngredientsRecipe ingredientsRecipe : ingredientsRecipes) {
            ingredientsRecipe.setRecipeId(savedRecipe.getId());
            ingredientsRecipeRepository.save(ingredientsRecipe);
        }

        System.out.println("Recipe and ingredients saved successfully!");
    }

    @Transactional
    public void deleteRecipe(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with ID: " + id);
        }
        ingredientsRecipeRepository.deleteAllByRecipeId(id);
        recipeRepository.deleteById(id);
        System.out.println("Recipe deleted successfully with ID: " + id);
    }

    @Transactional
    public void updateRecipe(Integer id, Recipe recipe, List<IngredientsRecipe> ingredientsRecipes) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));

        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setIngredientsRecipeId(recipe.getIngredientsRecipeId());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setUpdatedAt(recipe.getUpdatedAt());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);

        if (ingredientsRecipes != null) {
            ingredientsRecipeRepository.deleteAllByRecipeId(id);
            for (IngredientsRecipe ingredientsRecipe : ingredientsRecipes) {
                ingredientsRecipe.setRecipeId(updatedRecipe.getId());
                ingredientsRecipeRepository.save(ingredientsRecipe);
            }
        }

        System.out.println("Recipe updated successfully with ID: " + id);
    }
}
