package ru.georgy.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.georgy.NauJava.model.RecipeProduct;

@RepositoryRestResource(path = "recipeProducts")
public interface RecipeProductRepository extends CrudRepository<RecipeProduct, Long> {
} 