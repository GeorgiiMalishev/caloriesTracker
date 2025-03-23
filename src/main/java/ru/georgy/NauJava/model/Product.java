package ru.georgy.NauJava.model;

/**
 * Класс, представляющий сущность продукта
 */
public class Product {
    /**
     * Идентификатор продукта
     */
    private Long id;

    /**
     * Название продукта
     */
    private String name;

    /**
     * Калории продукта на 100гр
     */
    private double calories;

    /**
     * Белки продукта на 100гр
     */
    private double proteins;

    /**
     * Углеводы продукта на 100гр
     */
    private double carbs;

    /**
     * Жиры продукта на 100гр
     */
    private double fats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id +
                "\nname=" + name +
                "\ncalories=" + calories +
                "\nproteins=" + proteins +
                "\ncarbs=" + carbs +
                "\nfats=" + fats ;
    }
}
