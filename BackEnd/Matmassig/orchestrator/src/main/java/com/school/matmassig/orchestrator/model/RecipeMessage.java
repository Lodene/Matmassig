package com.school.matmassig.orchestrator.model;

import java.util.List;

public class RecipeMessage {

    private Recipe recipe;
    private List<Ingredient> ingredients;

    // Getters et Setters
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
