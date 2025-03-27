package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.MealRecipe;

public interface MealRecipeRepository extends CrudRepository<MealRecipe, Long> {
} 