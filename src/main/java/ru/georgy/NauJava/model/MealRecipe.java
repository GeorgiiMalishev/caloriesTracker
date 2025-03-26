package ru.georgy.NauJava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сущность, представляющая связь между приёмом пищи и рецептом блюда.
 */
@Entity
@Table(name = "meal_recipes")
public class MealRecipe {
    /**
     * Идентификатор связи приёма пищи и рецепта.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Приём пищи, к которому относится рецепт.
     */
    @ManyToOne
    private Meal meal;

    /**
     * Рецепт, который был использован в приёме пищи.
     */
    @ManyToOne
    private Recipe recipe;

    /**
     * Размер порции блюда (коэффициент от полного рецепта).
     */
    @Column(name = "serving_size", nullable = false)
    private Double servingSize;

    /**
     * Примечание к связи приёма пищи и рецепта.
     */
    @Column
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Double getServingSize() {
        return servingSize;
    }

    public void setServingSize(Double servingSize) {
        this.servingSize = servingSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}