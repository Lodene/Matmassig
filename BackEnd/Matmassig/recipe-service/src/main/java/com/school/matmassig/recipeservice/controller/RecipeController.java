package com.school.matmassig.recipeservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.school.matmassig.recipeservice.model.Ingredients;
import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeRequest.getTitle());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredientsRecipeId(recipeRequest.getIngredientsRecipeId());
        recipe.setInstructions(recipeRequest.getInstructions());
        recipe.setuserId(recipeRequest.getuserId());
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        Recipe createdRecipe = recipeService.addRecipe(recipe, recipeRequest.getListIngredients());
        return ResponseEntity.ok(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Integer id, @RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeRequest.getTitle());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredientsRecipeId(recipeRequest.getIngredientsRecipeId());
        recipe.setInstructions(recipeRequest.getInstructions());
        recipe.setuserId(recipeRequest.getuserId());
        recipe.setUpdatedAt(LocalDateTime.now());

        Recipe updatedRecipe = recipeService.updateRecipe(id, recipe, recipeRequest.getListIngredients());
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredients> addIngredient(@RequestBody Ingredients ingredient) {
        Ingredients createdIngredient = recipeService.addIngredient(ingredient);
        return ResponseEntity.ok(createdIngredient);
    }

    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<IngredientsRecipe> addIngredientToRecipe(@PathVariable Integer recipeId,
            @RequestBody IngredientsRecipe ingredientsRecipe) {
        IngredientsRecipe linkedIngredient = recipeService.addIngredientToRecipe(recipeId, ingredientsRecipe);
        return ResponseEntity.ok(linkedIngredient);
    }

    @GetMapping("/{recipeId}/ingredients")
    public ResponseEntity<List<IngredientsRecipe>> getIngredientsForRecipe(@PathVariable Integer recipeId) {
        List<IngredientsRecipe> ingredients = recipeService.getIngredientsForRecipe(recipeId);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredients>> getAllIngredients() {
        List<Ingredients> ingredients = recipeService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }
}

// DTO Class for Recipe Request
class RecipeRequest {
    private String title;
    private String description;
    private Integer ingredientsRecipeId;
    private String instructions;
    private Integer userId;
    private List<Integer> listIngredients;

    // Getters and Setters

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

    public List<Integer> getListIngredients() {
        return listIngredients;
    }

    public void setListIngredients(List<Integer> listIngredients) {
        this.listIngredients = listIngredients;
    }
}
