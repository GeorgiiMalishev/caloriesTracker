package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.georgy.NauJava.model.Meal;

public interface MealRepository extends CrudRepository<Meal, Long> {
} 