package com.school.matmassig.recipeservice.service;

import com.school.matmassig.recipeservice.model.Ingredient;
import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.repository.IngredientsRecipeRepository;
import com.school.matmassig.recipeservice.repository.RecipeRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListenerSerice {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientsRecipeRepository ingredientsRecipeRepository;

    @Transactional
    public void saveRecipeAndIngredients(Recipe recipe, List<IngredientsRecipe> ingredientsRecipes) {
        // Sauvegarder la recette
        Recipe savedRecipe = recipeRepository.save(recipe);
        System.out.println("Recipe saved successfully!" + savedRecipe);

        // Sauvegarder les ingrédients liés à cette recette

        for (IngredientsRecipe ingredientsRecipe : ingredientsRecipes) {
            System.out.println("\n\nIngredientsRecipe before : " + ingredientsRecipe.toString());
            ingredientsRecipe.setRecipeId(savedRecipe.getId()); // Associe l'ID de la recette aux ingrédients
            System.out.println("\n\nIngredientsRecipe after : " + ingredientsRecipe.toString());
            ingredientsRecipeRepository.save(ingredientsRecipe);
        }

        System.out.println("Recipe and ingredients saved successfully!");
    }
}