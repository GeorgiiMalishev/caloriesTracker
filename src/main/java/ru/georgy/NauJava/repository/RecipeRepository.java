package ru.georgy.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.georgy.NauJava.model.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long>, RecipeRepositoryCustom {
    
    /**
     * Находит рецепты, в которых используется указанный продукт 
     * и общая калорийность которых меньше указанного значения.
     * 
     * @param productId идентификатор продукта
     * @param maxCalories максимальная общая калорийность
     * @return список рецептов
     */
    @Query("SELECT r FROM Recipe r " +
           "JOIN RecipeProduct rp ON rp.recipe = r " +
           "WHERE rp.product.id = :productId " +
           "GROUP BY r " +
           "HAVING SUM(rp.product.calories * rp.quantity / 100) < :maxCalories " +
           "ORDER BY SUM(rp.product.calories * rp.quantity / 100)")
    List<Recipe> findRecipesWithProductAndLowerCalories(
            @Param("productId") Long productId, 
            @Param("maxCalories") Double maxCalories);
} 