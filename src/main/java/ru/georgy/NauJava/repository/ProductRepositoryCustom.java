package ru.georgy.NauJava.repository;

import ru.georgy.NauJava.model.Product;
import java.util.List;

public interface ProductRepositoryCustom {
    
    /**
     * Находит продукты с содержанием белка больше указанного значения
     * и калориями меньше указанного значения.
     * 
     * @param minProteins минимальное содержание белка
     * @param maxCalories максимальное количество калорий
     * @return список подходящих продуктов
     */
    List<Product> findProductsByProteinsAndCalories(Double minProteins, Double maxCalories);
} 