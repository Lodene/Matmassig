package com.school.matmassig.orchestrator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ingredientsRecipeId")
    private Integer ingredientsRecipeId;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("userId")
    private Integer userId;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
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

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredientsRecipeId=" + ingredientsRecipeId +
                ", instructions='" + instructions + '\'' +
                ", userId=" + userId +
                '}';
    }
}
