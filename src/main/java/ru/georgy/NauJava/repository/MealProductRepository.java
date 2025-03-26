package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.MealProduct;

public interface MealProductRepository extends CrudRepository<MealProduct, Long> {
} 