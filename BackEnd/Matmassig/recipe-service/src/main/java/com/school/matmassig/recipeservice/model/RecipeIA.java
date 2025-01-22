package com.school.matmassig.recipeservice.model;

public class RecipeIA {
    private Integer id;
    private Integer user_id;
    private Integer recipeIA_id;
    private Boolean is_Favourite;

    // constructeur
    public RecipeIA(Integer user_id, Integer recipeIA_id, Boolean is_Favourite) {
        this.user_id = user_id;
        this.recipeIA_id = recipeIA_id;
        this.is_Favourite = is_Favourite;
    }

    // getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRecipeIA_id() {
        return recipeIA_id;
    }

    public void setRecipeIA_id(Integer recipeIA_id) {
        this.recipeIA_id = recipeIA_id;
    }

    public Boolean getIs_Favourite() {
        return is_Favourite;
    }

    public void setIs_Favourite(Boolean is_Favourite) {
        this.is_Favourite = is_Favourite;
    }

}
