package ru.georgy.NauJava.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.georgy.NauJava.service.meal.MealInput;
import ru.georgy.NauJava.service.meal.MealResponse;
import ru.georgy.NauJava.service.meal.MealService;

@RestController
@RequestMapping("api/meals")
public class MealRestController {
    private final MealService mealService;

    @Autowired
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    public MealResponse createMealWithProducts(@Valid @RequestBody MealInput mealInput) {
        return mealService.createMealWithProducts(mealInput);
    }
}
