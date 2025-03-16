package ru.georgy.NauJava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgy.NauJava.config.Config;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final Config config;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, Config config) {
        this.productRepository = productRepository;
        this.config = config;
    }

    @Override
    public void createProduct(Long id,
                              String name,
                              int calories,
                              int proteins,
                              int carbs,
                              int fats) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCalories(calories);
        product.setProteins(proteins);
        product.setCarbs(carbs);
        product.setFats(fats);
        productRepository.create(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.read(id);
        if(productOptional.isEmpty())
            throw new IllegalArgumentException("Продукт с ID " + id + " не найден");
        return productOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.delete(id);
    }

    @Override
    public void updateName(Long id, String name) {
        Product product = findById(id);
        product.setName(name);
        productRepository.update(product);
    }

    @Override
    public void updateCalories(Long id, int newCalories) {
        Product product = findById(id);
        product.setCalories(newCalories);
        productRepository.update(product);
    }

    @Override
    public void updateProteins(Long id, int newProtein) {
        Product product = findById(id);
        product.setProteins(newProtein);
        productRepository.update(product);
    }

    @Override
    public void updateCarbs(Long id, int newCarbs) {
        Product product = findById(id);
        product.setCarbs(newCarbs);
        productRepository.update(product);
    }

    @Override
    public void updateFats(Long id, int newFats) {
        Product product = findById(id);
        product.setFats(newFats);
        productRepository.update(product);
    }

    @PostConstruct
    public void init() {
        System.out.println("Название приложения: " + config.getAppName());
        System.out.println("Версия приложения: " + config.getAppVersion());
    }
}
