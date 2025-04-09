package ru.georgy.NauJava.service.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для входящих данных пользователя.
 */
public record UserInput(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {}
