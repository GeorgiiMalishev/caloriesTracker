package ru.georgy.NauJava.service.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductInput(
        @NotBlank
        String name,

        @Positive
        Double calories,

        @Positive
        Double proteins,

        @Positive
        Double carbs,

        @Positive
        Double fats
) {
}
