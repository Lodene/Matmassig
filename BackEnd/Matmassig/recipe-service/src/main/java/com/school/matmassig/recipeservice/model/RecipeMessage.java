package com.school.matmassig.recipeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RecipeMessage {

    @JsonProperty("recipe")
    private Recipe recipe;

    @JsonProperty("ingredients")
    private List<IngredientsRecipe> ingredients;

    // Constructeur par défaut
    public RecipeMessage() {
    }

    // Constructeur avec paramètres
    public RecipeMessage(
            @JsonProperty("recipe") Recipe recipe,
            @JsonProperty("ingredients") List<IngredientsRecipe> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeMessage{" +
                "recipe=" + recipe +
                ", ingredients=" + ingredients +
                '}';
    }

    // Getters et Setters
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
}
