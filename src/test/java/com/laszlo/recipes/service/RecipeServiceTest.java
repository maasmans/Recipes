package com.laszlo.recipes.service;

import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}