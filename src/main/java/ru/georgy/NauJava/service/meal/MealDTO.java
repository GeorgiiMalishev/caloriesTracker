package ru.georgy.NauJava.service.meal;

import ru.georgy.NauJava.model.MealType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для создания приема пищи с продуктами
 */
public record MealDTO(
    Long userId,
    LocalDateTime dateTime,
    MealType mealType,
    List<ProductQuantityDTO> products
) {} 