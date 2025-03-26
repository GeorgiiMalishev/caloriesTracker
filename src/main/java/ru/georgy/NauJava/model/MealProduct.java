package ru.georgy.NauJava.model;

import jakarta.persistence.*;

/**
 * Сущность, представляющая связь между приёмом пищи и продуктом.
 */
@Entity
@Table(name = "meal_products")
public class MealProduct {
    /**
     * Идентификатор связи приёма пищи и продукта.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * Приём пищи, к которому относится продукт.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    /**
     * Продукт, который был потреблён.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Количество потреблённого продукта (в граммах).
     */
    @Column(name = "quantity", nullable = false)
    private Double quantity;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}