package ru.georgy.NauJava.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "id")
    private Long id;

    /**
     * Пользователь, который совершил приём пищи.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Дата и время приёма пищи.
     */
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    /**
     * Тип приёма пищи (например, завтрак, обед, ужин).
     */
    @Column(name = "meal_type", nullable = false)
    private MealType mealType;

    /**
     * Примечание к приёму пищи.
     */
    @Column(name = "note")
    private String note;

    /**
     * Список продуктов, включенных в данный прием пищи.
     */
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealProduct> mealProducts = new ArrayList<>();

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<MealProduct> getMealProducts() {
        return mealProducts;
    }

    public void setMealProducts(List<MealProduct> mealProducts) {
        this.mealProducts = mealProducts;
    }

    /**
     * Добавляет продукт к приему пищи.
     * 
     * @param mealProduct продукт для добавления
     */
    public void addMealProduct(MealProduct mealProduct) {
        mealProducts.add(mealProduct);
        mealProduct.setMeal(this);
    }

    /**
     * Удаляет продукт из приема пищи.
     * 
     * @param mealProduct продукт для удаления
     */
    public void removeMealProduct(MealProduct mealProduct) {
        mealProducts.remove(mealProduct);
        mealProduct.setMeal(null);
    }
}