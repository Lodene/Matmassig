package com.school.matmassig.recipeservice.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipePayload {
    private RecipeRequest recipe;
    private List<IngredientRequest> ingredients;

    // Getters et Setters
    public RecipeRequest getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeRequest recipe) {
        this.recipe = recipe;
    }

    public List<IngredientRequest> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRequest> ingredients) {
        this.ingredients = ingredients;
    }
}
