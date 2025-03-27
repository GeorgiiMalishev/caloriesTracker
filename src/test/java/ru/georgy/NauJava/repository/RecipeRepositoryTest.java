package ru.georgy.NauJava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.georgy.NauJava.model.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    
    private User user;
    private Product product1; 
    private Product product2; 
    private Product product3; 
    private Recipe recipe1;   
    private Recipe recipe2;   
    private Recipe recipe3;   
    
    @BeforeEach
    void setUp() {
        user = createTestUser();
        
        product1 = createTestProduct("Яйца", 157.0, 12.7, 0.9, 11.0);
        product2 = createTestProduct("Молоко", 63.0, 3.1, 4.7, 2.5);
        product3 = createTestProduct("Мука", 334.0, 10.3, 1.1, 70.6);
        
        recipe1 = createTestRecipe(user, "Омлет");
        recipe2 = createTestRecipe(user, "Блины");
        recipe3 = createTestRecipe(user, "Фриттата");
        
        addProductToRecipe(recipe1, product1, 120.0);
        addProductToRecipe(recipe1, product2, 50.0);
        
        addProductToRecipe(recipe2, product2, 200.0);
        addProductToRecipe(recipe2, product3, 100.0);
        
        addProductToRecipe(recipe3, product1, 180.0);
        addProductToRecipe(recipe3, product2, 30.0);
    }
    
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
        List<Recipe> foundRecipes = recipeRepository.findRecipesWithProductAndLowerCalories(
                product1.getId(), 250.0);
        
        assertEquals(1, foundRecipes.size());
        assertTrue(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe1.getId())));
        assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe2.getId())));
        assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe3.getId())));
    }
    
    /**
     * Тестирует поиск рецептов, содержащих определенный продукт и имеющих
     * ограниченную калорийность, с использованием Criteria API.
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
        List<Recipe> foundRecipes = recipeRepository.findRecipesWithProductAndCalories(
                product1.getId(), 250.0);
        
        assertEquals(1, foundRecipes.size());
        assertTrue(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe1.getId())));
        assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe2.getId())));
        assertFalse(foundRecipes.stream().anyMatch(r -> r.getId().equals(recipe3.getId())));
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
     * @return сохраненный в базе тестовый рецепт
     */
    private Recipe createTestRecipe(User user, String name) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setUser(user);
        
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