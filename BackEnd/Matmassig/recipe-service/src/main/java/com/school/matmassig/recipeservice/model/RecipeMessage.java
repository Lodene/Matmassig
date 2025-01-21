package com.school.matmassig.recipeservice.model;

import java.util.List;

public class RecipeMessage {
    private String email;
    private Recipe recipe;
    private List<IngredientsRecipe> ingredients;

    // Getters and setters
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

    public List<IngredientsRecipe> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeMessage{" +
                "email='" + email + '\'' +
                ", recipe=" + recipe +
                ", ingredients=" + ingredients +
                '}';
    }
}
