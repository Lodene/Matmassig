package com.school.matmassig.recipeservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.school.matmassig.recipeservice.model.Ingredient;
import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.model.dto.RecipeWithIngredients;
import com.school.matmassig.recipeservice.model.dto.RecipeRequest;
import com.school.matmassig.recipeservice.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    public RecipeRequest recipeRequest;

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeRequest.getTitle());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredientsRecipeId(recipeRequest.getIngredientsRecipeId());
        recipe.setInstructions(recipeRequest.getInstructions());
        recipe.setUserId(recipeRequest.getUserId()); // Assurez-vous que cette ligne est correcte
        recipe.setCreatedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        recipe.setUpdatedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));

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
        recipe.setUserId(recipeRequest.getUserId());
        recipe.setUpdatedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        Recipe updatedRecipe = recipeService.updateRecipe(id, recipe, recipeRequest.getListIngredients());
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = recipeService.addIngredient(ingredient);
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
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = recipeService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecipeWithIngredients>> getRecipesForUser(@PathVariable Integer userId) {
        List<RecipeWithIngredients> recipes = recipeService.getRecipesForUser(userId);
        return ResponseEntity.ok(recipes);
    }
}