package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.Meal;

@RepositoryRestResource(path = "meals")
public interface MealRepository extends CrudRepository<Meal, Long> {
} 