package com.laszlo.recipes.model;
/**
 * Refers to what kind of ingredients the recipe contains.
 */
public enum DishSpecification {
    /**
        This recipe has an undefined  ingredient specification.
     */
    UNKNOWN,
    VEGETARIAN,
    PESCATARIAN,
    VEGAN,
    OMNIVORE
}
