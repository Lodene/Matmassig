package com.school.matmassig.recipeservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class IngredientsRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer ingredientId;
    private Integer quantity;
    private Integer recipeId;

    // constructor
    public IngredientsRecipe() {
        // Default constructor required by JPA
    }

    public IngredientsRecipe(Integer ingredientId, Integer quantity, Integer recipeId) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.recipeId = recipeId;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

}
