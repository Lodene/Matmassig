package com.school.matmassig.orchestrator.model;

public class RecipeIA {
    private Integer id;
    private String email;
    private Integer userId;
    private Integer recipeIAId;
    private Boolean isFavourite;

    // constructeur
    public RecipeIA() {
    }

    public RecipeIA(Integer id, String email, Integer userId, Boolean isFavourite) {
        this.id = id;
        this.email = email;
        this.userId = userId;
        this.isFavourite = isFavourite;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
