package com.school.matmassig.recipeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @Column(name = "ingredients_recipe_id")
    @JsonProperty("ingredientsRecipeId")
    private int ingredientsRecipeId;

    @JsonProperty("instructions")
    private String instructions;

    @Column(name = "user_id", nullable = false)
    @JsonProperty("userId")
    private int userId;

    @Column(name = "created_at")
    @JsonProperty("createdAt")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    private Date updatedAt = new Date();

    // Constructeur par défaut requis par JPA
    public Recipe() {
    }

    public Recipe(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("ingredientsRecipeId") int ingredientsRecipeId,
            @JsonProperty("instructions") String instructions,
            @JsonProperty("userId") int userId) {
        this.title = title;
        this.description = description;
        this.ingredientsRecipeId = ingredientsRecipeId;
        this.instructions = instructions;
        this.userId = userId;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", title:" + "\"" + title + "\"" +
                ", description:" + "\"" + description + "\"" +
                ", ingredientsRecipeId:" + ingredientsRecipeId +
                ", instructions:" + "\"" +instructions + "\"" +
                ", userId:" + userId +
                ", createdAt:" + "\"" + createdAt + "\"" +
                ", updatedAt:" + "\"" +  updatedAt + "\"" +
                '}';
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

    public int getIngredientsRecipeId() {
        return ingredientsRecipeId;
    }

    public void setIngredientsRecipeId(int ingredientsRecipeId) {
        this.ingredientsRecipeId = ingredientsRecipeId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
