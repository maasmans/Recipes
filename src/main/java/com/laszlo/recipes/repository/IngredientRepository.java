package com.laszlo.recipes.repository;

import com.laszlo.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository  extends JpaRepository<Ingredient,Integer> {
}
