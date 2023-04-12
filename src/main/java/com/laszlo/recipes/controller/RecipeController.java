package com.laszlo.recipes.controller;

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
        return recipeService.save(recipe);
    }
}
