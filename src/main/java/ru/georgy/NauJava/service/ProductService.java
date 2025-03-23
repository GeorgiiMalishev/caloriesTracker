package ru.georgy.NauJava.service;

import ru.georgy.NauJava.model.Product;

public interface ProductService {
    /**
     * Создать новый продукт
     * @param id Идентификатор
     * @param name Название продукта
     * @param calories Количество калорий
     * @param proteins Количество белков
     * @param carbs Количество углеводов
     * @param fats Количество жиров
     */
    void createProduct(Long id,
                       String name,
                       int calories,
                       int proteins,
                       int carbs,
                       int fats);

    /**
     * Найти продукт по id
     * @param id Идентификатор
     * @return Продукт
     * @throws IllegalArgumentException если продукт не найден
     */
    Product findById(Long id);

    /**
     * Удалить продукт по id
     * @param id Идентификатор
     */
    void deleteById(Long id);

    /**
     * Обновить название продукта
     * @param id Идентификатор
     * @param name Новое название
     * @throws IllegalArgumentException если продукт не найден
     */
    void updateName(Long id, String name);

    /**
     * Обновить количество калорий продукта
     * @param id Идентификатор
     * @param newCalories Новое количество калорий
     * @throws IllegalArgumentException если продукт не найден
     */
    void updateCalories(Long id, int newCalories);

    /**
     * Обновить количество белков продукта
     * @param id Идентификатор
     * @param newProtein Новое количество белков
     * @throws IllegalArgumentException если продукт не найден
     */
    void updateProteins(Long id, int newProtein);

    /**
     * Обновить количество углеводов продукта
     * @param id Идентификатор
     * @param newCarbs Новое количество углеводов
     * @throws IllegalArgumentException если продукт не найден
     */
    void updateCarbs(Long id, int newCarbs);

    /**
     * Обновить количество жиров продукта
     * @param id Идентификатор
     * @param newFats Новое количество жиров
     * @throws IllegalArgumentException если продукт не найден
     */
    void updateFats(Long id, int newFats);
}
