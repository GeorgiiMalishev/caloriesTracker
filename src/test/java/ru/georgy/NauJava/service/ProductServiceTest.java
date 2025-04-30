package ru.georgy.NauJava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.georgy.NauJava.mapper.ProductMapper;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.service.product.ProductInput;
import ru.georgy.NauJava.service.product.ProductServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    /**
     * Создание нового продукта и проверка, что он сохраняется в репозитории
     */
    @Test
    void testCreateProduct() {
        productService.createProduct(1L, "Хлеб", 250, 7, 50, 3);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());

        Product product = captor.getValue();
        assertEquals(1L, product.getId());
        assertEquals("Хлеб", product.getName());
        assertEquals(250, product.getCalories());
        assertEquals(7, product.getProteins());
        assertEquals(50, product.getCarbs());
        assertEquals(3, product.getFats());
    }

    /**
     * Поиск существующего продукта по id
     */
    @Test
    void testFindByIdSuccess() {
        Product mockProduct = createProduct(1L, "Молоко", 60.0, 3.0, 5.0, 2.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Product result = productService.findById(1L);
        assertEquals("Молоко", result.getName());
    }

    /**
     * Поиск несуществующего продукта по id приводит к исключению
     */
    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.findById(1L));
    }

    /**
     * Удаление продукта по id вызывает метод репозитория
     */
    @Test
    void testDeleteById() {
        productService.deleteById(2L);
        verify(productRepository).deleteById(2L);
    }

    /**
     * Обновление названия существующего продукта
     */
    @Test
    void testUpdateName() {
        Product product = createProduct(1L, "Старое", 100.0, 10.0, 10.0, 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateName(1L, "Новое");

        assertEquals("Новое", product.getName());
        verify(productRepository).save(product);
    }

    /**
     * Обновление названия несуществующего продукта приводит к исключению
     */
    @Test
    void testUpdateNameNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateName(1L, "Новое"));
    }

    /**
     * Обновление калорий существующего продукта
     */
    @Test
    void testUpdateCalories() {
        Product product = createProduct(1L, "Продукт", 100.0, 10.0, 10.0, 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateCalories(1L, 200);

        assertEquals(200, product.getCalories());
        verify(productRepository).save(product);
    }

    /**
     * Обновление калорий несуществующего продукта приводит к исключению
     */
    @Test
    void testUpdateCaloriesNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateCalories(1L, 200));
    }

    /**
     * Обновление белков существующего продукта
     */
    @Test
    void testUpdateProteins() {
        Product product = createProduct(1L, "Продукт", 100.0, 10.0, 10.0, 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateProteins(1L, 15);

        assertEquals(15, product.getProteins());
        verify(productRepository).save(product);
    }

    /**
     * Обновление белков несуществующего продукта приводит к исключению
     */
    @Test
    void testUpdateProteinsNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateProteins(1L, 15));
    }

    /**
     * Обновление углеводов существующего продукта
     */
    @Test
    void testUpdateCarbs() {
        Product product = createProduct(1L, "Продукт", 100.0, 10.0, 10.0, 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateCarbs(1L, 30);

        assertEquals(30, product.getCarbs());
        verify(productRepository).save(product);
    }

    /**
     * Обновление углеводов несуществующего продукта приводит к исключению
     */
    @Test
    void testUpdateCarbsNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateCarbs(1L, 30));
    }

    /**
     * Обновление жиров существующего продукта
     */
    @Test
    void testUpdateFats() {
        Product product = createProduct(1L, "Продукт", 100.0, 10.0, 10.0, 10.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateFats(1L, 5);

        assertEquals(5, product.getFats());
        verify(productRepository).save(product);
    }

    /**
     * Обновление жиров несуществующего продукта приводит к исключению
     */
    @Test
    void testUpdateFatsNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.updateFats(1L, 5));
    }

    /**
     * Создание продукта через ProductInput
     */
    @Test
    void testCreateProductWithInput() {
        ProductInput input = new ProductInput("Йогурт", 120.0, 6.0, 12.0, 3.0);
        Product product = createProduct(3L, "Йогурт", 120.0, 6.0, 12.0, 3.0);

        when(productMapper.toEntity(input)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(input);

        assertNotNull(result);
        assertEquals("Йогурт", result.getName());
        verify(productMapper).toEntity(input);
        verify(productRepository).save(product);
    }

    private Product createProduct(Long id,
                                  String name,
                                  Double calories,
                                  Double proteins,
                                  Double carbs,
                                  Double fats) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCalories(calories);
        product.setProteins(proteins);
        product.setCarbs(carbs);
        product.setFats(fats);
        return product;
    }
}