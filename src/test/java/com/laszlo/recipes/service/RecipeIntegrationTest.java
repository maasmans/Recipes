package com.laszlo.recipes.service;


import com.laszlo.recipes.model.DishSpecification;
import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.IngredientRepository;
import com.laszlo.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ActiveProfiles("test-containers-flyway")
public class RecipeIntegrationTest {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;
//    @Container
//    private static final PostgreSQLContainer dbContainer = new PostgreSQLContainer("postgres:15.2");
//    @Container
//    private static final PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:15.2")
//        .withDatabaseName("recipes")
//        .withUsername("foo")
//        .withPassword("password");

//    static {
//        postgresqlContainer.start();
//        System.setProperty("spring.datasource.url", postgresqlContainer.getJdbcUrl());
//    }

    @Test
    void test() {
        Recipe recipe = Recipe.builder()
                .name("name")
                .instructions("instruction")
                .dishSpecification(DishSpecification.OMNIVORE)
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        Ingredient ingredient = Ingredient.builder()
                .inclusion(Ingredient.Inclusion.INCLUDED)
                .recipe(savedRecipe)
                .name("pepper")
                .build();
        ingredientRepository.save(ingredient);
        List<Recipe> savedRecipes = recipeRepository.findAll();
//        List<Ingredient> savedRecipes = recipeRepository.findAll();
        assertThat(savedRecipes).isNotNull();
        assertThat(savedRecipes).hasSize(1);
    }

}
