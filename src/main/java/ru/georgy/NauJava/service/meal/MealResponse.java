package ru.georgy.NauJava.service.meal;

import ru.georgy.NauJava.model.MealType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для возврата приема пищи с продуктами
 */
public record MealResponse(
        Long id,
        Long userId,
        LocalDateTime dateTime,
        MealType mealType,
        String note,
        List<ProductQuantityDTO> products
) {}