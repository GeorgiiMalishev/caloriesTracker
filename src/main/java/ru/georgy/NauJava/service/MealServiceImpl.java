package ru.georgy.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.georgy.NauJava.model.Meal;
import ru.georgy.NauJava.model.MealProduct;
import ru.georgy.NauJava.repository.MealProductRepository;
import ru.georgy.NauJava.repository.MealRepository;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    
    private final MealRepository mealRepository;
    private final MealProductRepository mealProductRepository;
    
    @Autowired
    public MealServiceImpl(MealRepository mealRepository, MealProductRepository mealProductRepository) {
        this.mealRepository = mealRepository;
        this.mealProductRepository = mealProductRepository;
    }
    
    @Override
    @Transactional
    public Meal createMealWithProducts(Meal meal, List<MealProduct> products) {
        Meal savedMeal = mealRepository.save(meal);
        double totalCalories = 0.0;
        
        for (MealProduct mealProduct : products) {
            mealProduct.setMeal(savedMeal);
            mealProductRepository.save(mealProduct);
            
            totalCalories += (mealProduct.getQuantity() * mealProduct.getProduct().getCalories()) / 100.0;
        }

        savedMeal.setTotalCalories(totalCalories);
        return mealRepository.save(savedMeal);
    }
} 