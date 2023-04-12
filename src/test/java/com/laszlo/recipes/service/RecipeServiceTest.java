package com.laszlo.recipes.service;

import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RecipeServiceTest {
    /**
     * Unit under test
     */
    private RecipeService recipeService;

    /* Dependencies. */
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp(){
        recipeRepository = Mockito.mock(RecipeRepository.class);
        recipeService = new RecipeService(recipeRepository);
    }

    @Test
    void findByInstruction() {
        // Arrange
        Recipe recipeWithOven1 = Recipe.builder().instructions("These are instructions with an oven").build();
        Recipe recipeWithOven2 = Recipe.builder().instructions("These are oven instructions").build();
        Recipe recipeWithoutOven = Recipe.builder().instructions("These are instructions without anything").build();
        Recipe recipeWithoutInstructions = Recipe.builder().instructions(null).build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipeWithOven1, recipeWithOven2, recipeWithoutOven, recipeWithoutInstructions));
        // Act
        List<Recipe> result = recipeService.findByInstructions("oven");
        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void findByIngredients_IncludedFound() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .ingredients(List.of(
                        Ingredient.builder()
                                .name("Eggplant")
                                .inclusion(Ingredient.Inclusion.INCLUDED)
                                .build()))
                .build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.INCLUDED);
        // Act
        List<Recipe> byIngredients = recipeService.findByIngredients(ingredientInclusions);
        // Assert
        assertThat(byIngredients).hasSize(1);
        assertThat(byIngredients.get(0)).usingRecursiveComparison().isEqualTo(recipe);
    }

    @Test
    void findByIngredients_IncludedNotFound() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .ingredients(List.of())
                .build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.INCLUDED);
        // Act
        List<Recipe> byIngredients = recipeService.findByIngredients(ingredientInclusions);
        // Assert
        assertThat(byIngredients).isEmpty();
    }

    @Test
    void findByIngredients_ExcludedNotFound() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .ingredients(List.of(
                        Ingredient.builder()
                                .name("Eggplant")
                                .inclusion(Ingredient.Inclusion.INCLUDED)
                                .build()))
                .build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.EXCLUDED);
        // Act
        List<Recipe> byIngredients = recipeService.findByIngredients(ingredientInclusions);
        // Assert
        assertThat(byIngredients).isEmpty();
    }

    @Test
    void findByIngredients_ExcludedFound() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .ingredients(List.of())
                .build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.EXCLUDED);
        // Act
        List<Recipe> byIngredients = recipeService.findByIngredients(ingredientInclusions);
        // Assert
        assertThat(byIngredients).hasSize(1);
        assertThat(byIngredients.get(0)).usingRecursiveComparison().isEqualTo(recipe);
    }

    @Test
    void findByIngredients_ThrowsExeception() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .name("lasagna")
                .ingredients(List.of(
                        Ingredient.builder()
                                .name("Eggplant")
                                .inclusion(Ingredient.Inclusion.INCLUDED)
                                .build(),
                        Ingredient.builder()
                                .name("Eggplant")
                                .inclusion(Ingredient.Inclusion.EXCLUDED)
                                .build()
                ))
                .build();
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.EXCLUDED);
        // Act + Assert
        final IllegalStateException exception = assertThrows(IllegalStateException.class, () -> recipeService.findByIngredients(ingredientInclusions));
        assertThat(exception).hasMessageContaining("Recipe lasagna contains an ingredient that's included and excluded");
    }
}