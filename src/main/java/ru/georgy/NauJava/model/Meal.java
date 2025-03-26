package ru.georgy.NauJava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая приём пищи пользователя.
 */
@Entity
@Table(name = "meals")
public class Meal {

    /**
     * Идентификатор приёма пищи.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Пользователь, который совершил приём пищи.
     */
    @ManyToOne
    private User user;

    /**
     * Дата и время приёма пищи.
     */
    @Column(nullable = false)
    private LocalDateTime dateTime;

    /**
     * Тип приёма пищи (например, завтрак, обед, ужин).
     */
    @Column(name = "meal_type", nullable = false)
    private MealType mealType;

    /**
     * Общая калорийность приёма пищи.
     */
    @Column(name = "total_calories")
    private Double totalCalories;

    /**
     * Примечание к приёму пищи.
     */
    @Column
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}