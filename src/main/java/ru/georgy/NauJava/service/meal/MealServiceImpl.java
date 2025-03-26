package ru.georgy.NauJava.service.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.georgy.NauJava.model.Meal;
import ru.georgy.NauJava.model.MealProduct;
import ru.georgy.NauJava.repository.MealProductRepository;
import ru.georgy.NauJava.repository.MealRepository;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    
    private final MealRepository mealRepository;
    private final MealProductRepository mealProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public MealServiceImpl(MealRepository mealRepository, 
                           MealProductRepository mealProductRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.mealRepository = mealRepository;
        this.mealProductRepository = mealProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    @Transactional
    public Meal createMealWithProducts(MealDTO mealDTO) {
        Meal meal = new Meal();
        meal.setUser(userRepository.findById(mealDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден")));
        meal.setDateTime(mealDTO.dateTime());
        meal.setMealType(mealDTO.mealType());
        
        Meal savedMeal = mealRepository.save(meal);
        
        List<MealProduct> mealProducts = new ArrayList<>();
        
        for (ProductQuantityDTO productDTO : mealDTO.products()) {
            MealProduct mealProduct = new MealProduct();
            mealProduct.setMeal(savedMeal);
            mealProduct.setProduct(productRepository.findById(productDTO.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Продукт не найден")));
            mealProduct.setQuantity(productDTO.quantity());
            
            mealProductRepository.save(mealProduct);
            mealProducts.add(mealProduct);
        }

        savedMeal.setMealProducts(mealProducts);

        return mealRepository.save(savedMeal);
    }
} 