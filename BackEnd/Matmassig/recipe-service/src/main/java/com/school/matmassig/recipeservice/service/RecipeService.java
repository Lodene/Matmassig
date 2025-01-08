package com.school.matmassig.recipeservice.service;

import com.school.matmassig.recipeservice.model.Ingredients;
import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.repository.IngredientRepository;
import com.school.matmassig.recipeservice.repository.IngredientsRecipeRepository;
import com.school.matmassig.recipeservice.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientsRecipeRepository ingredientsRecipeRepository;

    public Recipe addRecipe(Recipe recipe, List<Integer> ingredientIds) {
        Recipe savedRecipe = recipeRepository.save(recipe);

        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            List<IngredientsRecipe> ingredientsRecipes = ingredientIds.stream().map(ingredientId -> {
                IngredientsRecipe ingredientsRecipe = new IngredientsRecipe();
                ingredientsRecipe.setRecipeId(savedRecipe.getId());
                ingredientsRecipe.setIngredientId(ingredientId);
                ingredientsRecipe.setQuantity(1); // Default quantity, can be customized
                return ingredientsRecipe;
            }).collect(Collectors.toList());

            ingredientsRecipeRepository.saveAll(ingredientsRecipes);
        }

        return savedRecipe;
    }

    public Recipe updateRecipe(Integer id, Recipe recipe, List<Integer> ingredientIds) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setIngredientsRecipeId(recipe.getIngredientsRecipeId());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setUpdatedAt(recipe.getUpdatedAt());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);

        if (ingredientIds != null) {
            // Remove existing ingredient associations
            ingredientsRecipeRepository.deleteAllByRecipeId(id);

            // Add new associations
            List<IngredientsRecipe> ingredientsRecipes = ingredientIds.stream().map(ingredientId -> {
                IngredientsRecipe ingredientsRecipe = new IngredientsRecipe();
                ingredientsRecipe.setRecipeId(id);
                ingredientsRecipe.setIngredientId(ingredientId);
                ingredientsRecipe.setQuantity(1); // Default quantity, can be customized
                return ingredientsRecipe;
            }).collect(Collectors.toList());

            ingredientsRecipeRepository.saveAll(ingredientsRecipes);
        }

        return updatedRecipe;
    }

    public void deleteRecipe(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        ingredientsRecipeRepository.deleteAllByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    public Ingredients addIngredient(Ingredients ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredients> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public IngredientsRecipe addIngredientToRecipe(Integer recipeId, IngredientsRecipe ingredientsRecipe) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        ingredientsRecipe.setRecipeId(recipeId);
        return ingredientsRecipeRepository.save(ingredientsRecipe);
    }

    public List<IngredientsRecipe> getIngredientsForRecipe(Integer recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RuntimeException("Recipe not found");
        }
        return ingredientsRecipeRepository.findByRecipeId(recipeId);
    }
}
