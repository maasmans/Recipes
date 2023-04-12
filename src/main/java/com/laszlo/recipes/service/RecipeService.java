package com.laszlo.recipes.service;

import com.laszlo.recipes.model.Ingredient;
import com.laszlo.recipes.model.Recipe;
import com.laszlo.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Collection<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe save(Recipe newRecipe) {
        return recipeRepository.save(newRecipe);
    }

    public Iterable<Recipe> saveAll(Iterable<Recipe> recipes) {
        return recipeRepository.saveAll(recipes);
    }

    /**
     * Finds the recipes that have the instructionText within the {@link Recipe#getInstructions()}.
     * The text can be placed in any particular spot within the instructions for it to find a match and return the recipe.
     */
    public List<Recipe> findByInstructions(String instructionText) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> Objects.nonNull(recipe.getInstructions()))
                .filter(recipe -> Pattern.compile(instructionText).matcher(recipe.getInstructions()).find())
                .toList();
    }

    /**
     * Finds the recipes by the inclusion or exclusion of a ingredient name.
     */
    public List<Recipe> findByIngredients(Map<String, Ingredient.Inclusion> ingredientNamesInclusions) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipeMatchesInclusions(recipe, ingredientNamesInclusions))
                .toList();
    }

    /**
     * A recipe should match all ingredientInclusion specification
     */
    private boolean recipeMatchesInclusions(Recipe recipe, Map<String, Ingredient.Inclusion> ingredientsInclusions) {
        return ingredientsInclusions.entrySet().stream()
                .allMatch(inclusion -> recipeMatchesInclusion(recipe, inclusion.getKey(), inclusion.getValue()));
    }

    /**
     * An ingredient can either be excluded or included. Therefor it should comply with one of the nested boolean statements.
     * If it's included the recipe should contain an ingredient with the same name and the inclusion type included,
     * if it's excluded it should not contain an ingredient with the same name and the inclusion type excluded.
     *
     * This check could have also been done the other way around checking Included by checking whether it's not excluded and Excluded by whether it's not included.
     * It's chosen to always check for included even when the ingredient has to be excluded.
     *
     * We get a contradictory state when for example when in the recipe an ingredient is both included and excluded.
     * */
    private boolean recipeMatchesInclusion(Recipe recipe, String ingredientName, Ingredient.Inclusion specification) {
        checkForContradictions(recipe);
        return (specification == Ingredient.Inclusion.INCLUDED &&
                recipe.getIngredients().stream()
                        .filter(ingredient -> Objects.equals(ingredient.getInclusion(), Ingredient.Inclusion.INCLUDED))
                        .map(Ingredient::getName)
                        .anyMatch(name -> name.equals(ingredientName)) ||
                (specification == Ingredient.Inclusion.EXCLUDED &&
                        recipe.getIngredients().stream()
                                .filter(ingredient -> Objects.equals(ingredient.getInclusion(), Ingredient.Inclusion.INCLUDED))
                                .map(Ingredient::getName)
                                .noneMatch(name -> name.equals(ingredientName))));
    }

    /**
     * Checks if a recipe has an ingredients with the same name and contradiction inclusion. If so throws an illegal state exception.
     */
    private void checkForContradictions(final Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredients.stream()
                    .anyMatch(a -> Objects.equals(a.getName(), ingredient.getName()) &&
                            !Objects.equals(a.getInclusion(), ingredient.getInclusion())))
                throw new IllegalStateException(String.format("Recipe %s contains an ingredient that's included and excluded", recipe.getName()));
        }
    }
}
