package ru.georgy.NauJava.service.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO для входящих данных пользователя.
 */
public record UserInput(
        @NotBlank
        String username,

        @NotBlank
        @Pattern(regexp = ".*@.*")
        String email,

        @NotBlank
        String password
) {}
