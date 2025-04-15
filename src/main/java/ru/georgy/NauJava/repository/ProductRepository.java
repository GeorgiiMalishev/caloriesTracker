package ru.georgy.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.Product;

import java.util.List;

@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    
    /**
     * Находит продукты с содержанием белка больше указанного значения
     * и калориями меньше указанного значения.
     * @param minProteins минимальное содержание белка
     * @param maxCalories максимальное количество калорий
     * @return список подходящих продуктов
     */
    List<Product> findByProteinsGreaterThanAndCaloriesLessThan(
            Double minProteins, Double maxCalories);
} 