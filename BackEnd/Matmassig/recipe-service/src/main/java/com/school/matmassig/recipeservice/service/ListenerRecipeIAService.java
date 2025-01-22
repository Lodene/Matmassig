package com.school.matmassig.recipeservice.service;

import com.school.matmassig.recipeservice.model.RecipeIA;
import com.school.matmassig.recipeservice.repository.RecipeIARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListenerRecipeIAService {

    @Autowired
    private RecipeIARepository recipeRepository;

    public void addRecipeIA(RecipeIA recipe) {
        RecipeIA savedRecipe = recipeRepository.save(recipe);
    }

    // delete recipe by id
    public void deleteRecipe(Integer recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    // update
    public void updateRecipe(RecipeIA recipe) {
        recipeRepository.save(recipe);
    }

}