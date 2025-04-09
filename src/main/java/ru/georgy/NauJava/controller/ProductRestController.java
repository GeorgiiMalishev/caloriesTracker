package ru.georgy.NauJava.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.repository.ProductRepository;
import ru.georgy.NauJava.service.product.ProductInput;
import ru.georgy.NauJava.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findProductsByProteinsAndCalories(
            @RequestParam @NotNull(message = "Минимальное значение белка должно быть указано") Double minProteins,
            @RequestParam @NotNull(message = "Максимальное значение калорий должно быть указано") Double maxCalories) {

        return productRepository.findProductsByProteinsAndCalories(minProteins, maxCalories);
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductInput productInput){
        return productService.createProduct(productInput);
    }
}
