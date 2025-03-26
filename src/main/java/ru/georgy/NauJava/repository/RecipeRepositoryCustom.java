package ru.georgy.NauJava.repository;

import ru.georgy.NauJava.model.Recipe;
import java.util.List;

public interface RecipeRepositoryCustom {
    
    /**
     * Находит рецепты, в которых используется указанный продукт 
     * и общая калорийность которых меньше указанного значения.
     * 
     * @param productId идентификатор продукта
     * @param maxCalories максимальная общая калорийность
     * @return список рецептов
     */
    List<Recipe> findRecipesWithProductAndCalories(Long productId, Double maxCalories);
} 