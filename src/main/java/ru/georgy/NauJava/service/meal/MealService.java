package ru.georgy.NauJava.service.meal;

import ru.georgy.NauJava.model.Meal;

public interface MealService {
    
    /**
     * Создает новый прием пищи с указанными продуктами
     * 
     * @param mealDTO DTO приема пищи
     * @return созданный прием пищи
     */
    Meal createMealWithProducts(MealDTO mealDTO);
} 