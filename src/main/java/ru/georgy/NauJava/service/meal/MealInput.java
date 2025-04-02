package ru.georgy.NauJava.service.meal;

import jakarta.validation.constraints.NotNull;
import ru.georgy.NauJava.model.MealType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для создания приема пищи с продуктами
 */
public record MealInput(
    @NotNull
    Long userId,
    @NotNull
    LocalDateTime dateTime,
    @NotNull
    MealType mealType,
    @NotNull
    List<ProductQuantityDTO> products
) {} 