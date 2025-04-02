package ru.georgy.NauJava.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.georgy.NauJava.model.Meal;
import ru.georgy.NauJava.model.MealProduct;
import ru.georgy.NauJava.model.User;
import ru.georgy.NauJava.service.meal.MealInput;
import ru.georgy.NauJava.service.meal.MealResponse;
import ru.georgy.NauJava.service.meal.ProductQuantityDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "products", source = "mealProducts")
    MealResponse toResponse(Meal meal);

    @Mapping(target = "productId", source = "product.id")
    ProductQuantityDTO toProductQuantityDTO(MealProduct mealProduct);

    default List<ProductQuantityDTO> mealProductsToProductQuantityDTOs(List<MealProduct> mealProducts) {
        return mealProducts.stream()
                .map(this::toProductQuantityDTO)
                .toList();
    }

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "mealProducts", ignore = true)
    Meal toEntity(MealInput request);

    default Meal toEntityWithUser(MealInput request, User user) {
        Meal meal = toEntity(request);
        meal.setUser(user);
        return meal;
    }
}