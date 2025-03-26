package ru.georgy.NauJava.service;

import ru.georgy.NauJava.model.Meal;
import ru.georgy.NauJava.model.MealProduct;

import java.util.List;

public interface MealService {
    
    /**
     * Создает новый прием пищи с указанными продуктами
     * 
     * @param meal прием пищи
     * @param products список продуктов и их количества
     * @return созданный прием пищи
     */
    Meal createMealWithProducts(Meal meal, List<MealProduct> products);
} 