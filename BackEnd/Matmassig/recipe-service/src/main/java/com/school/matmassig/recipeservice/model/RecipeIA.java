package com.school.matmassig.recipeservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserRecipeIA")
public class RecipeIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "recipeIA_id") // Correspond au nom de la colonne dans la base de données
    private Integer recipeId;

    @Column(name = "is_favourite")
    private Boolean isFavourite;

    // tostring
    @Override
    public String toString() {
        return "RecipeIA{" +
                "id=" + id +
                ", userId=" + userId +
                ", recipeId=" + recipeId +
                ", isFavourite=" + isFavourite +
                '}';
    }

    // Constructeur par défaut requis par JPA
    public RecipeIA() {
    }

    public RecipeIA(Integer userId, Integer recipeId, Boolean isFavourite) {
        this.userId = userId;
        this.recipeId = recipeId;
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

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
