package com.school.matmassig.orchestrator.model;

import java.util.List;

public class Recipe {
    private String title;
    private String description;
    private Integer ingredientsRecipeId;
    private String instructions;
    private Integer userId;
    private List<Integer> listIngredients;

    // Getters et Setters
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
