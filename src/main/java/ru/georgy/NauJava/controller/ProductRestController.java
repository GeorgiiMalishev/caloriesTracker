package ru.georgy.NauJava.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.repository.ProductRepository;

import java.util.List;

@RestController("api/products")
public class ProductRestController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> findProductsByProteinsAndCalories(
            @RequestParam @NotNull(message = "Минимальное значение белка должно быть указано") Double minProteins,
            @RequestParam @NotNull(message = "Максимальное значение калорий должно быть указано") Double maxCalories) {

        return productRepository.findProductsByProteinsAndCalories(minProteins, maxCalories);
    }
}
