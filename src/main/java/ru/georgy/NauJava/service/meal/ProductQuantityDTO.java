package ru.georgy.NauJava.service.meal;

/**
 * DTO для продукта и его количества в приеме пищи
 */
public record ProductQuantityDTO(
    Long productId,
    Double quantity
) {} 