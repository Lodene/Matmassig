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
}
