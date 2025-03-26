package ru.georgy.NauJava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сущность, представляющая рецепт блюда.
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    /**
     * Идентификатор рецепта.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Название рецепта.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Пользователь, создавший рецепт.
     */
    @ManyToOne
    private User user;

    /**
     * Общая калорийность блюда.
     */
    @Column(name = "total_calories")
    private Double totalCalories;

    /**
     * Общее содержание белков в блюде (в граммах).
     */
    @Column(name = "total_proteins")
    private Double totalProteins;

    /**
     * Общее содержание углеводов в блюде (в граммах).
     */
    @Column(name = "total_carbohydrates")
    private Double totalCarbohydrates;

    /**
     * Общее содержание жиров в блюде (в граммах).
     */
    @Column(name = "total_fats")
    private Double totalFats;

    /**
     * Общий вес блюда (в граммах).
     */
    @Column(name = "total_weight")
    private Double totalWeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Double getTotalProteins() {
        return totalProteins;
    }

    public void setTotalProteins(Double totalProteins) {
        this.totalProteins = totalProteins;
    }

    public Double getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(Double totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public Double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(Double totalFats) {
        this.totalFats = totalFats;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }
}