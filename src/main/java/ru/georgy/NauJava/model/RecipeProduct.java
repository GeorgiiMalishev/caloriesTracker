package ru.georgy.NauJava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Сущность, представляющая связь между рецептом и продуктом.
 */
@Entity
@Table(name = "recipe_products")
public class RecipeProduct {
    /**
     * Идентификатор связи рецепта и продукта.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Рецепт, к которому относится продукт.
     */
    @ManyToOne
    private Recipe recipe;

    /**
     * Продукт, используемый в рецепте.
     */
    @ManyToOne
    private Product product;

    /**
     * Количество продукта, используемого в рецепте (в граммах).
     */
    @Column(nullable = false)
    private Double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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