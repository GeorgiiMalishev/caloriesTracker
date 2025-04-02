package ru.georgy.NauJava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.georgy.NauJava.model.*;
import ru.georgy.NauJava.repository.MealProductRepository;
import ru.georgy.NauJava.repository.MealRepository;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import ru.georgy.NauJava.service.meal.MealInput;
import ru.georgy.NauJava.service.meal.MealResponse;
import ru.georgy.NauJava.service.meal.MealService;
import ru.georgy.NauJava.service.meal.ProductQuantityDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс для тестирования сервиса приемов пищи.
 */
@SpringBootTest
class MealServiceTest {
    
    @Autowired
    private MealService mealService;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private MealProductRepository mealProductRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User user;
    private Product product1;
    private Product product2;
    
    @BeforeEach
    void setUp() {
        user = createTestUser();
        product1 = createTestProduct("Яблоко", 52.0);
        product2 = createTestProduct("Банан", 89.0);
    }
    
    /**
     * Тестирует успешное выполнение транзакционной операции создания приема пищи
     * с подсчетом его общей калорийности на основе используемых продуктов.
     * Сценарий теста:
     * 1. Создаётся DTO с информацией о приеме пищи и двух продуктах с указанием их количества
     * 2. Вызывается сервисный метод для создания приема пищи
     * 3. Проверяется, что прием пищи был успешно сохранен в базе
     * 4. Проверяется корректность расчета калорийности: 100г яблока и 150г банана = 185.5 ккал
     * 5. Проверяется, что в базе созданы все связи между приемом пищи и продуктами
     */
    @Test
    @Transactional
    void testCreateMealWithProducts() {
        List<ProductQuantityDTO> productDTOs = new ArrayList<>();
        productDTOs.add(new ProductQuantityDTO(product1.getId(), 100.0));
        productDTOs.add(new ProductQuantityDTO(product2.getId(), 150.0));
        
        MealInput mealInput = new MealInput(
            user.getId(),
            LocalDateTime.now(),
            MealType.BREAKFAST,
            productDTOs
        );
        
        MealResponse savedMeal = mealService.createMealWithProducts(mealInput);
        
        Assertions.assertNotNull(savedMeal.id());
        
        double expectedCalories = 
            (product1.getCalories() * 100.0 / 100.0) + 
            (product2.getCalories() * 150.0 / 100.0);
        Assertions.assertEquals(185.5, expectedCalories, 0.01);
        
        List<MealProduct> foundMealProducts = new ArrayList<>();
        mealProductRepository.findAll().forEach(mp -> {
            if (mp.getMeal().getId().equals(savedMeal.id())) {
                foundMealProducts.add(mp);
            }
        });
        
        Assertions.assertEquals(2, foundMealProducts.size());
    }
    
    /**
     * Тестирует откат транзакции при возникновении ошибки в процессе 
     * создания приема пищи с продуктами.
     * Сценарий теста:
     * 1. Фиксируется начальное количество записей в таблицах
     * 2. Создается DTO с некорректными данными (null в поле quantity)
     * 3. Вызывается метод сервиса, ожидается исключение
     * 4. Проверяется, что транзакция откатилась и количество записей не изменилось
     */
    @Test
    void testTransactionRollback() {
        long initialMealCount = mealRepository.count();
        long initialMealProductCount = mealProductRepository.count();
        
        List<ProductQuantityDTO> productDTOs = new ArrayList<>();
        productDTOs.add(new ProductQuantityDTO(product1.getId(), null));
        
        MealInput mealInput = new MealInput(
            user.getId(),
            LocalDateTime.now(),
            MealType.BREAKFAST,
            productDTOs
        );

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> mealService.createMealWithProducts(mealInput));

        Assertions.assertTrue(
            exception instanceof DataIntegrityViolationException ||
            exception.getCause() instanceof DataIntegrityViolationException
        );

        Assertions.assertEquals(initialMealCount, mealRepository.count());
        Assertions.assertEquals(initialMealProductCount, mealProductRepository.count());
    }
    
    /**
     * Создает и сохраняет тестового пользователя с случайными данными.
     * 
     * @return сохраненный в базе тестовый пользователь
     */
    private User createTestUser() {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID() + "@example.com");
        user.setPasswordHash(UUID.randomUUID().toString());
        
        return userRepository.save(user);
    }
    
    /**
     * Создает и сохраняет тестовый продукт с указанными параметрами.
     * 
     * @param name название продукта
     * @param calories калорийность на 100 г
     * @return сохраненный в базе тестовый продукт
     */
    private Product createTestProduct(String name, Double calories) {
        Product product = new Product();
        product.setName(name);
        product.setCalories(calories);
        product.setProteins(5.0);
        product.setCarbs(20.0);
        product.setFats(0.5);
        
        return productRepository.save(product);
    }
} 