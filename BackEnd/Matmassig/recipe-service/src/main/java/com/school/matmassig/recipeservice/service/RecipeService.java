package com.school.matmassig.recipeservice.service;

import com.school.matmassig.recipeservice.model.Ingredient;
import com.school.matmassig.recipeservice.model.IngredientsRecipe;
import com.school.matmassig.recipeservice.model.Recipe;
import com.school.matmassig.recipeservice.model.RecipeMessage;
import com.school.matmassig.recipeservice.model.dto.RecipeWithIngredients;
import com.school.matmassig.recipeservice.model.dto.IngredientDetails;
import com.school.matmassig.recipeservice.repository.IngredientRepository;
import com.school.matmassig.recipeservice.repository.IngredientsRecipeRepository;
import com.school.matmassig.recipeservice.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RecipeService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.api.url:http://api.example.com/recommendation}")
    private String aiApiUrl;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientsRecipeRepository ingredientsRecipeRepository;

    public Recipe addRecipe(Recipe recipe, List<Integer> ingredientIds) {
        Recipe savedRecipe = recipeRepository.save(recipe);

        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            List<IngredientsRecipe> ingredientsRecipes = ingredientIds.stream().map(ingredientId -> {
                IngredientsRecipe ingredientsRecipe = new IngredientsRecipe();
                ingredientsRecipe.setRecipeId(savedRecipe.getId());
                ingredientsRecipe.setIngredientId(ingredientId);
                ingredientsRecipe.setQuantity(1);
                return ingredientsRecipe;
            }).collect(Collectors.toList());

            ingredientsRecipeRepository.saveAll(ingredientsRecipes);
        }

        return savedRecipe;
    }

    public Recipe updateRecipe(Integer id, Recipe recipe, List<Integer> ingredientIds) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        existingRecipe.setTitle(recipe.getTitle());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setIngredientsRecipeId(recipe.getIngredientsRecipeId());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setUpdatedAt(recipe.getUpdatedAt());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);

        if (ingredientIds != null) {
            // Remove existing ingredient associations
            ingredientsRecipeRepository.deleteAllByRecipeId(id);

            // Add new associations
            List<IngredientsRecipe> ingredientsRecipes = ingredientIds.stream().map(ingredientId -> {
                IngredientsRecipe ingredientsRecipe = new IngredientsRecipe();
                ingredientsRecipe.setRecipeId(id);
                ingredientsRecipe.setIngredientId(ingredientId);
                ingredientsRecipe.setQuantity(1); // Default quantity, can be customized
                return ingredientsRecipe;
            }).collect(Collectors.toList());

            ingredientsRecipeRepository.saveAll(ingredientsRecipes);
        }

        return updatedRecipe;
    }

    public void deleteRecipe(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        ingredientsRecipeRepository.deleteAllByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public IngredientsRecipe addIngredientToRecipe(Integer recipeId, IngredientsRecipe ingredientsRecipe) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        ingredientsRecipe.setRecipeId(recipeId);
        return ingredientsRecipeRepository.save(ingredientsRecipe);
    }

    public List<IngredientsRecipe> getIngredientsForRecipe(Integer recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RuntimeException("Recipe not found");
        }
        return ingredientsRecipeRepository.findByRecipeId(recipeId);
    }

    public List<RecipeWithIngredients> getRecipesForUser(Integer userId) {
        List<Recipe> userRecipes = recipeRepository.findByUserId(userId);
        if (userRecipes.isEmpty()) {
            throw new RuntimeException("No recipes found for user with ID " + userId);
        }

        return userRecipes.stream().map(recipe -> {
            List<IngredientsRecipe> ingredientsRecipes = ingredientsRecipeRepository.findByRecipeId(recipe.getId());
            List<IngredientDetails> ingredientDetails = ingredientsRecipes.stream().map(ingredientsRecipe -> {
                Ingredient ingredient = ingredientRepository.findById(ingredientsRecipe.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found"));
                return new IngredientDetails(ingredient.getId(), ingredient.getName(), ingredientsRecipe.getQuantity());
            }).collect(Collectors.toList());
            return new RecipeWithIngredients(recipe, ingredientDetails);
        }).collect(Collectors.toList());
    }

    public void sendToAI(RecipeMessage reviewMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RecipeMessage> entity = new HttpEntity<>(reviewMessage, headers);

            restTemplate.postForEntity(aiApiUrl, entity, String.class);
            log.info("Successfully sent review to AI API: {}", reviewMessage);
        } catch (Exception e) {
            log.error("Failed to send review to AI API: {}", reviewMessage, e);
        }
    }

}
