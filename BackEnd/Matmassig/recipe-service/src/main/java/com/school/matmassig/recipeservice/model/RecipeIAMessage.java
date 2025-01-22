package com.school.matmassig.recipeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIAMessage {
    private Integer id;
    private Integer userId;
    private Integer recipeIAId;
    private Boolean isFavourite;
    private String email;

    // Constructeur par défaut
    public RecipeIAMessage() {
    }

    // Constructeur avec arguments
    public RecipeIAMessage(Integer id, Integer userId, Integer recipeIAId, Boolean isFavourite) {
        this.id = id;
        this.userId = userId;
        this.recipeIAId = recipeIAId;
        this.isFavourite = isFavourite;
    }

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecipeIAId() {
        return recipeIAId;
    }

    public void setRecipeIAId(Integer recipeIAId) {
        this.recipeIAId = recipeIAId;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
