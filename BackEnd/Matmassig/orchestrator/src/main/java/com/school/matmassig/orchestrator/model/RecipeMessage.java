package com.school.matmassig.orchestrator.model;

import java.util.List;

public class RecipeMessage {
    private String email;

    private Recipe recipe;
    private List<Ingredient> ingredients;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
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

    @Override
    public String toString() {
        return "RecipeMessage{" +
                "recipe=" + recipe +
                ", ingredients=" + ingredients +
                '}';
    }
}
