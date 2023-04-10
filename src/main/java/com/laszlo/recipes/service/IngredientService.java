package com.laszlo.recipes.service;

import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.IngredientRepository;
import com.laszlo.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Iterable<Ingredient> saveAll(Iterable<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }

}
