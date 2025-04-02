package ru.georgy.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.georgy.NauJava.model.Recipe;
import ru.georgy.NauJava.repository.RecipeRepository;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeRestController {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeRestController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public List<Recipe> findRecipesWithProductAndCalories(
            @RequestParam Long productId,
            @RequestParam Double maxCalories){
        
        return recipeRepository.findRecipesWithProductAndCalories(productId, maxCalories);
    }
}
