package com.laszlo.recipes.service;

import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Collection<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe save(Recipe newRecipe) {
        return recipeRepository.save(newRecipe);
    }

    public Iterable<Recipe> saveAll(Iterable<Recipe> recipes) {
        return recipeRepository.saveAll(recipes);
    }

    /**
     * Finds the recipes that have the instructionText within the {@link Recipe#getInstructions()}.
     * The text can be placed in any particular spot within the instructions for it to find a match and return the recipe.
     */
    public List<Recipe> findByInstructions(String instructionText) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> Objects.nonNull(recipe.getInstructions()))
                .filter(recipe -> Pattern.compile(instructionText).matcher(recipe.getInstructions()).find())
                .toList();
    }
}
