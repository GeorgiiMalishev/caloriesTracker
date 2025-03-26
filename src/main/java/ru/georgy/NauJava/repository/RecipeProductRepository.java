package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.RecipeProduct;

public interface RecipeProductRepository extends CrudRepository<RecipeProduct, Long> {
} 