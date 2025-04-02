package ru.georgy.NauJava.service.meal;

public interface MealService {
    
    /**
     * Создает новый прием пищи с указанными продуктами
     * 
     * @param mealInput DTO приема пищи
     * @return созданный прием пищи
     */
    MealResponse createMealWithProducts(MealInput mealInput);
} 