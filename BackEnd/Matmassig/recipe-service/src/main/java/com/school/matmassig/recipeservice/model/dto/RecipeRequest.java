package com.school.matmassig.recipeservice.model.dto;

import java.util.List;

public class RecipeRequest {
    private String title;
    private String description;
    private Integer ingredientsRecipeId;
    private String instructions;
    private Integer userId;
    private List<Integer> listIngredients;

    // constructor
    public RecipeRequest(String title, String description, Integer ingredientsRecipeId, String instructions,
            Integer userId, List<Integer> listIngredients) {
        this.title = title;
        this.description = description;
        this.ingredientsRecipeId = ingredientsRecipeId;
        this.instructions = instructions;
        this.userId = userId;
        this.listIngredients = listIngredients;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIngredientsRecipeId() {
        return ingredientsRecipeId;
    }

    public void setIngredientsRecipeId(Integer ingredientsRecipeId) {
        this.ingredientsRecipeId = ingredientsRecipeId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getListIngredients() {
        return listIngredients;
    }

    public void setListIngredients(List<Integer> listIngredients) {
        this.listIngredients = listIngredients;
    }
}
