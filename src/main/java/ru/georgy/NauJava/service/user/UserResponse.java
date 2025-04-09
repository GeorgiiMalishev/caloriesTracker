package ru.georgy.NauJava.service.user;

import ru.georgy.NauJava.model.Gender;
import ru.georgy.NauJava.model.Role;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO для ответа с данными пользователя.
 */
public record UserResponse(
        Long id,
        String username,
        Double height,
        Double weight,
        Gender gender,
        LocalDate birthDate,
        Set<Role> roles
) {}
