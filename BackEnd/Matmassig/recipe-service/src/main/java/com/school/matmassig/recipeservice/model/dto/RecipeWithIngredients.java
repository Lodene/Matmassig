package com.school.matmassig.recipeservice.model.dto;

import java.util.List;

import com.school.matmassig.recipeservice.model.Recipe;

public class RecipeWithIngredients {
    private Recipe recipe;
    private List<IngredientDetails> ingredients;

    public RecipeWithIngredients(Recipe recipe, List<IngredientDetails> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<IngredientDetails> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDetails> ingredients) {
        this.ingredients = ingredients;
    }
}
