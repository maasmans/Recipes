package com.laszlo.recipes.controller;

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

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping()
    Ingredient save(@RequestBody Ingredient ingredient) {
        Ingredient savedIngredient = ingredientService.save(ingredient);
        return savedIngredient;
    }
}
