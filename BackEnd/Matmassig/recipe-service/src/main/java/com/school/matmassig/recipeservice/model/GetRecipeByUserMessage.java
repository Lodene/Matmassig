package com.school.matmassig.recipeservice.model;

public class GetRecipeByUserMessage {
    private Integer id;
    private String email;

    // constructeur
    public GetRecipeByUserMessage() {
    }

    public GetRecipeByUserMessage(String email, Integer id) {
        this.email = email;
        this.id = id;
    }

    // getter et setter
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

}
