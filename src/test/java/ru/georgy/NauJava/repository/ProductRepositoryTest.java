package ru.georgy.NauJava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.georgy.NauJava.model.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования методов репозитория продуктов.
 */
@SpringBootTest
class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;
    
    private Product protein1;
    private Product protein2;
    private Product lowProtein;
    private Product midProtein;
    
    @BeforeEach
    void setUp() {
        protein1 = createTestProduct("Куриная грудка", 165.0, 24.0, 0.0, 3.6);
        protein2 = createTestProduct("Говядина", 250.0, 26.0, 0.0, 19.0);
        lowProtein = createTestProduct("Яблоко", 52.0, 0.3, 14.0, 0.2);
        midProtein = createTestProduct("Творог обезжиренный", 71.0, 18.0, 3.3, 0.6);
    }
    
    /**
     * Тестирует поиск продуктов по содержанию белка и калориям с использованием Query Lookup Strategy.
     * Сценарий теста:
     * 1. Создаются четыре тестовых продукта с разными значениями белков и калорий
     * 2. Выполняется запрос для поиска продуктов с белком > 20.0 г и калориями < 200.0 ккал
     * 3. Проверяется, что в результатах присутствует только "Куриная грудка",
     *    и отсутствуют остальные продукты.
     */
    @Test
    void testFindByProteinsGreaterThanAndCaloriesLessThan() {
        List<Product> foundProducts = productRepository.findByProteinsGreaterThanAndCaloriesLessThan(20.0, 200.0);

        assertTrue(foundProducts.stream().anyMatch(p -> p.getId().equals(protein1.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(protein2.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(lowProtein.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(midProtein.getId())));
    }
    
    /**
     * Тестирует поиск продуктов по содержанию белка и калориям с использованием Criteria API.
     * <p>
     * Сценарий теста:
     * 1. Создаются четыре тестовых продукта с разными значениями белков и калорий
     * 2. Выполняется запрос для поиска продуктов с белком > 20.0 г и калориями < 200.0 ккал
     *    с использованием реализации на базе Criteria API
     * 3. Проверяется, что в результатах присутствует только "Куриная грудка",
     *    и отсутствуют остальные продукты.
     */
    @Test
    void testFindProductsByProteinsAndCalories() {
        List<Product> foundProducts = productRepository.findProductsByProteinsAndCalories(20.0, 200.0);
        
        assertTrue(foundProducts.stream().anyMatch(p -> p.getId().equals(protein1.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(protein2.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(lowProtein.getId())));
        assertFalse(foundProducts.stream().anyMatch(p -> p.getId().equals(midProtein.getId())));
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
} 