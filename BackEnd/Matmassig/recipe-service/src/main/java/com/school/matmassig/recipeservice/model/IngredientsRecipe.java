package com.school.matmassig.recipeservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
public class IngredientsRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer ingredientId;
    private Integer quantity;
    private Integer recipeId;
    private String unit;

    // toString
    @Override
    public String toString() {
        return "IngredientsRecipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredientId=" + ingredientId +
                ", quantity=" + quantity +
                ", recipeId=" + recipeId +
                ", unit='" + unit +
                '}';
    }

    // constructor
    public IngredientsRecipe() {
        // Default constructor required by JPA
    }

    public IngredientsRecipe(Integer ingredientId, Integer quantity, Integer recipeId, String name, String unit) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.recipeId = recipeId;
        this.name = name;
        this.unit = unit;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
