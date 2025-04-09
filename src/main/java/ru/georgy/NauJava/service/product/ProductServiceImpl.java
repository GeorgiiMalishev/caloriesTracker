package ru.georgy.NauJava.service.product;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgy.NauJava.config.Config;
import ru.georgy.NauJava.mapper.ProductMapper;
import ru.georgy.NauJava.model.Product;
import ru.georgy.NauJava.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final Config config;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, Config config, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.config = config;
        this.productMapper = productMapper;
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
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty())
            throw new IllegalArgumentException("Продукт с ID " + id + " не найден");
        return productOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateName(Long id, String name) {
        Product product = findById(id);
        product.setName(name);
        productRepository.save(product);
    }

    @Override
    public void updateCalories(Long id, int newCalories) {
        Product product = findById(id);
        product.setCalories(newCalories);
        productRepository.save(product);
    }

    @Override
    public void updateProteins(Long id, int newProtein) {
        Product product = findById(id);
        product.setProteins(newProtein);
        productRepository.save(product);
    }

    @Override
    public void updateCarbs(Long id, int newCarbs) {
        Product product = findById(id);
        product.setCarbs(newCarbs);
        productRepository.save(product);
    }

    @Override
    public void updateFats(Long id, int newFats) {
        Product product = findById(id);
        product.setFats(newFats);
        productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductInput productInput) {
        Product product = productMapper.toEntity(productInput);
        return productRepository.save(product);
    }

    @PostConstruct
    public void init() {
        System.out.println("Название приложения: " + config.getAppName());
        System.out.println("Версия приложения: " + config.getAppVersion());
    }
}
