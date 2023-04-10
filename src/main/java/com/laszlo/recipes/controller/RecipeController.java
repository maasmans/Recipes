package com.laszlo.recipes.controller;

import com.laszlo.recipes.model.DishSpecification;
import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.service.IngredientService;
import com.laszlo.recipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping
    public Collection<Recipe> getRecipes() {
        return recipeService.findAll();
    }

    @PostMapping()
    Recipe save(@RequestBody Recipe recipe) {
//        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        Recipe savedRecipe = recipeService.save(recipe);
//        ingredientService.saveAll(ingredientService.saveAll(recipe.getIngredients()));
        return savedRecipe;
    }
    @GetMapping("/create")
    void create() {
        Recipe recipe = new Recipe();
        recipe.setName("hollahollaholla");
        recipe.setInstructions("hollahollaholla");
        recipe.setDishSpecification(DishSpecification.OMNIVORE);
        Recipe save = recipeService.save(recipe);
        Ingredient ingredient = new Ingredient();
        ingredient.setName("asd");
        ingredient.setInclusion(Ingredient.Inclusion.EXCLUDED);
        ingredient.setRecipe(save);
        ingredientService.save(ingredient);

    }
}
