package com.laszlo.recipes.controller;

import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping()
    Ingredient save(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }
}
