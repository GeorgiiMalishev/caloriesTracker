package ru.georgy.NauJava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

/**
 * Сущность, представляющая пользователя системы.
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * Имя пользователя в системе.
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Электронная почта пользователя.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Хеш пароля пользователя.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Рост пользователя в сантиметрах.
     */
    @Column(name = "height")
    private Double height;

    /**
     * Вес пользователя в килограммах.
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * Пол пользователя.
     */
    @Column(name = "gender")
    private Gender gender;

    /**
     * Дата рождения пользователя.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Роли пользователя
     */
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "roles")
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public @NotNull Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull Set<Role> roles) {
        this.roles = roles;
    }
}