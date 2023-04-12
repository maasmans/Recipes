package com.laszlo.recipes.service;


import com.laszlo.recipes.model.DishSpecification;
import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.IngredientRepository;
import com.laszlo.recipes.repository.RecipeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestContextManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class RecipeIntegrationTest {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    RecipeService recipeService;

    @Container
    private static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres:15.2");

    /**
     * Overwrites the default configuration and sets new values for the datasource directly from the testcontainer to the applicationContext.
     */
    @DynamicPropertySource
    static void configureTestContainerProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", dbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", dbContainer::getUsername);
        registry.add("spring.datasource.password", dbContainer::getPassword);
    }

    /**
     * Method executed after every test and cleans up the used database.
     */
    @AfterAll
    static void cleanUpDatabase() {
        ApplicationContext applicationContext = new TestContextManager(RecipeIntegrationTest.class).getTestContext().getApplicationContext();
        RecipeRepository recipeRepository = applicationContext.getBean(RecipeRepository.class);
        IngredientRepository ingredientRepository = applicationContext.getBean(IngredientRepository.class);
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    void integrationTest() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .name("name")
                .instructions("instruction")
                .dishSpecification(DishSpecification.OMNIVORE)
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        Ingredient ingredient = Ingredient.builder()
                .inclusion(Ingredient.Inclusion.INCLUDED)
                .recipe(savedRecipe)
                .name("Eggplant")
                .build();
        ingredientRepository.save(ingredient);
        HashMap<String, Ingredient.Inclusion> ingredientInclusions = new HashMap<>();
        ingredientInclusions.put("Eggplant", Ingredient.Inclusion.INCLUDED);
        // Act
        List<Recipe> byIngredients = recipeService.findByIngredients(ingredientInclusions);
        // Assert
        assertThat(byIngredients).isNotNull().hasSize(1);
        assertThat(byIngredients.get(0)).usingRecursiveComparison().ignoringFields("ingredients").isEqualTo(recipe);
    }

    @Test
    void BasicSaveAndRetrieveOperation() {
        // Arrange
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
        // Act
        List<Recipe> savedRecipes = recipeRepository.findAll();
        // Assert
        assertThat(savedRecipes).hasSize(1);
    }
}
