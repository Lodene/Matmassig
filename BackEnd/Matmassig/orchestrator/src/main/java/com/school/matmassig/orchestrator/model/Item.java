package com.school.matmassig.orchestrator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("email")
    private String email;

    @JsonProperty("id")
    private Integer id; // Facultatif pour item.create

    @JsonProperty("ingredientId")
    private Integer ingredientId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("userId")
    private Integer userId;

    public Item() {}

    public Item(Integer id, Integer ingredientId, Integer quantity, Integer userId) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.userId = userId;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", quantity=" + quantity +
                ", userId=" + userId +
                '}';
    }
}