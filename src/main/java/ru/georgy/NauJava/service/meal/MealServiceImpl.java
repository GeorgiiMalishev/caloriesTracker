package ru.georgy.NauJava.service.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.georgy.NauJava.model.Meal;
import ru.georgy.NauJava.model.MealProduct;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.repository.MealProductRepository;
import ru.georgy.NauJava.repository.MealRepository;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.repository.UserRepository;
import ru.georgy.NauJava.mapper.MealMapper;
import ru.georgy.NauJava.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    
    private final MealRepository mealRepository;
    private final MealProductRepository mealProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final MealMapper mealMapper;
    
    @Autowired
    public MealServiceImpl(MealRepository mealRepository, 
                           MealProductRepository mealProductRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           MealMapper mealMapper) {
        this.mealRepository = mealRepository;
        this.mealProductRepository = mealProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mealMapper = mealMapper;
    }
    
    @Override
    @Transactional
    public MealResponse createMealWithProducts(MealInput mealInput) {
        User user = userRepository.findById(mealInput.userId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь", mealInput.userId()));
        Meal meal = mealMapper.toEntityWithUser(mealInput, user);
        Meal savedMeal = mealRepository.save(meal);
        
        List<MealProduct> mealProducts = new ArrayList<>();
        
        for (ProductQuantityDTO productDTO : mealInput.products()) {
            MealProduct mealProduct = new MealProduct();
            mealProduct.setMeal(savedMeal);
            mealProduct.setProduct(productRepository.findById(productDTO.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Продукт", productDTO.productId())));
            mealProduct.setQuantity(productDTO.quantity());
            
            mealProductRepository.save(mealProduct);
            mealProducts.add(mealProduct);
        }

        savedMeal.setMealProducts(mealProducts);

        return mealMapper.toResponse(mealRepository.save(savedMeal));
    }
} 