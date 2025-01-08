package com.school.matmassig.recipeservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private Integer ingredientsRecipeId;
    private String instructions;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeur par défaut requis par JPA
    public Recipe() {
    }

    public Recipe(String title, String description, Integer ingredientsRecipeId, String instructions,
            Integer userId) {
        this.title = title;
        this.description = description;
        this.ingredientsRecipeId = ingredientsRecipeId;
        this.instructions = instructions;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

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

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
