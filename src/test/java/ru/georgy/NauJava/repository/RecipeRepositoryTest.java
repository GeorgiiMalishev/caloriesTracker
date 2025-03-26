package ru.georgy.NauJava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.georgy.NauJava.model.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс для тестирования методов репозитория рецептов.
 */
@SpringBootTest
class RecipeRepositoryTest {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private RecipeProductRepository recipeProductRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Тестирует поиск рецептов, содержащих определенный продукт и имеющих 
     * ограниченную калорийность, с использованием JPQL запроса.
     * Сценарий теста:
     * 1. Создаётся тестовый пользователь
     * 2. Создаются тестовые продукты
     * 3. Создаются три тестовых рецепта с разной калорийностью
     * 4. Выполняется поиск рецептов, которые содержат яйца и имеют калорийность < 400.0 ккал
     * 5. Проверяется, что в результатах присутствует только "Омлет", и отсутствуют "Блины" и "Фриттата"
     */
    @Test
    void testFindRecipesWithProductAndLowerCalories() {
        User user = createTestUser();
        
        Product product1 = createTestProduct("Яйца", 157.0, 12.7, 0.9, 11.0);
        Product product2 = createTestProduct("Молоко", 63.0, 3.1, 4.7, 2.5);
        Product product3 = createTestProduct("Мука", 334.0, 10.3, 1.1, 70.6);
        
        Recipe recipe1 = createTestRecipe(user, "Омлет", 350.0, 20.0, 30.0, 15.0, 250.0);
        Recipe recipe2 = createTestRecipe(user, "Блины", 500.0, 20.0, 30.0, 15.0, 250.0);
        Recipe recipe3 = createTestRecipe(user, "Фриттата", 420.0, 20.0, 30.0, 15.0, 250.0);
        
        addProductToRecipe(recipe1, product1, 120.0);
        addProductToRecipe(recipe1, product2, 50.0);
        
        addProductToRecipe(recipe2, product2, 200.0);
        addProductToRecipe(recipe2, product3, 100.0);
        
        addProductToRecipe(recipe3, product1, 180.0);
        addProductToRecipe(recipe3, product2, 30.0);
        
        List<Recipe> foundRecipes = recipeRepository.findRecipesWithProductAndLowerCalories(
                product1.getId(), 400.0);
        
        Assertions.assertEquals(1, foundRecipes.size());
        Assertions.assertTrue(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe1.getId())));
        Assertions.assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe2.getId())));
        Assertions.assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe3.getId())));
    }
    
    /**
     * Тестирует поиск рецептов, содержащих определенный продукт и имеющих 
     * ограниченную калорийность, с использованием Criteria API.
     * <p>
     * Сценарий теста:
     * 1. Создаётся тестовый пользователь
     * 2. Создаются тестовые продукты
     * 3. Создаются три тестовых рецепта с разной калорийностью
     * 4. Выполняется поиск рецептов, которые содержат яйца и имеют калорийность < 400.0 ккал
     *    с использованием реализации на базе Criteria API
     * 5. Проверяется, что в результатах присутствует только "Омлет", и отсутствуют "Блины" и "Фриттата"
     */
    @Test
    void testFindRecipesWithProductAndCalories() {
        User user = createTestUser();
        
        Product product1 = createTestProduct("Яйца", 157.0, 12.7, 0.7, 11.5);
        Product product2 = createTestProduct("Молоко", 42.0, 2.8, 4.7, 1.0);
        Product product3 = createTestProduct("Мука", 334.0, 10.3, 1.1, 70.6);
        
        Recipe recipe1 = createTestRecipe(user, "Омлет", 350.0, 20.0, 30.0, 15.0, 250.0);
        Recipe recipe2 = createTestRecipe(user, "Блины", 500.0, 20.0, 30.0, 15.0, 250.0);
        Recipe recipe3 = createTestRecipe(user, "Фриттата", 420.0, 20.0, 30.0, 15.0, 250.0);
        
        addProductToRecipe(recipe1, product1, 120.0);
        addProductToRecipe(recipe1, product2, 50.0);
        
        addProductToRecipe(recipe2, product2, 200.0);
        addProductToRecipe(recipe2, product3, 100.0);
        
        addProductToRecipe(recipe3, product1, 180.0);
        addProductToRecipe(recipe3, product2, 30.0);
        
        List<Recipe> foundRecipes = recipeRepository.findRecipesWithProductAndCalories(
                product1.getId(), 400.0);
        
        Assertions.assertEquals(1, foundRecipes.size());
        Assertions.assertTrue(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe1.getId())));
        Assertions.assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe2.getId())));
        Assertions.assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe3.getId())));
    }
    
    /**
     * Создает и сохраняет тестовый продукт с указанными параметрами.
     * 
     * @param name название продукта
     * @param calories калорийность на 100 г
     * @param proteins содержание белков на 100 г
     * @param carbs содержание углеводов на 100 г
     * @param fats содержание жиров на 100 г
     * @return сохраненный в базе тестовый продукт
     */
    private Product createTestProduct(String name, Double calories, Double proteins, Double carbs, Double fats) {
        Product product = new Product();
        product.setName(name);
        product.setCalories(calories);
        product.setProteins(proteins);
        product.setCarbs(carbs);
        product.setFats(fats);
        
        return productRepository.save(product);
    }
    
    /**
     * Создает и сохраняет тестового пользователя с случайными данными.
     * 
     * @return сохраненный в базе тестовый пользователь
     */
    private User createTestUser() {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID()+ "@example.com");
        user.setPasswordHash(UUID.randomUUID().toString());
        
        return userRepository.save(user);
    }
    
    /**
     * Создает и сохраняет тестовый рецепт с указанными параметрами.
     * 
     * @param user пользователь-создатель рецепта
     * @param name название рецепта
     * @param calories общая калорийность рецепта
     * @param proteins общее содержание белков в рецепте
     * @param carbs общее содержание углеводов в рецепте
     * @param fats общее содержание жиров в рецепте
     * @param weight общий вес готового блюда
     * @return сохраненный в базе тестовый рецепт
     */
    private Recipe createTestRecipe(User user, String name, Double calories, Double proteins, Double carbs, Double fats, Double weight) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setUser(user);
        recipe.setTotalCalories(calories);
        recipe.setTotalProteins(proteins);
        recipe.setTotalCarbohydrates(carbs);
        recipe.setTotalFats(fats);
        recipe.setTotalWeight(weight);
        
        return recipeRepository.save(recipe);
    }
    
    /**
     * Создает и сохраняет связь между рецептом и продуктом.
     * 
     * @param recipe рецепт
     * @param product продукт
     * @param quantity количество продукта в граммах
     * @return сохраненная в базе связь между рецептом и продуктом
     */
    private RecipeProduct addProductToRecipe(Recipe recipe, Product product, Double quantity) {
        RecipeProduct recipeProduct = new RecipeProduct();
        recipeProduct.setRecipe(recipe);
        recipeProduct.setProduct(product);
        recipeProduct.setQuantity(quantity);
        
        return recipeProductRepository.save(recipeProduct);
    }
} 