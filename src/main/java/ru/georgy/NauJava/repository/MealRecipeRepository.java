package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.MealRecipe;

@RepositoryRestResource(path = "mealRecipes")
public interface MealRecipeRepository extends CrudRepository<MealRecipe, Long> {
} 