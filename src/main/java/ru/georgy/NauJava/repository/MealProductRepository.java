package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.MealProduct;

@RepositoryRestResource(path = "mealProducts")
public interface MealProductRepository extends CrudRepository<MealProduct, Long> {
} 