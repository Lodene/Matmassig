package com.school.matmassig.recipeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteRecipeMessage {
    private Integer id;
    private String email; // Email de l'utilisateur pour les notifications
    private Integer userId; // Id de l'utilisateur pour les notifications
    private Integer recipeId;
    private Integer page;
    private Integer size;

    // contructeur
    public DeleteRecipeMessage() {
    }

    public DeleteRecipeMessage(Integer id, String email, Integer userId) {
        this.id = id;
        this.email = email;
        this.userId = userId;
    }

    // Getters et Setters
    public Integer getid() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DeleteRecipeMessage{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
