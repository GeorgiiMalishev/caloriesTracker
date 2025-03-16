package ru.georgy.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.georgy.NauJava.model.Product;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepository implements CrudRepository<Product, Long> {

    private final List<Product> products;

    @Autowired
    public ProductRepository(List<Product> products) {
        this.products = products;
    }

    @Override
    public void create(Product product) {
        if(read(product.getId()).isEmpty()) {
            products.add(product);
        }
        else{
            throw new IllegalArgumentException("Продукт с ID " + product.getId() + " уже существует");
        }
    }

    @Override
    public Optional<Product> read(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Product product) {
        Optional<Product> existingProductOpt = read(product.getId());
        if(existingProductOpt.isEmpty()){
            throw new IllegalArgumentException("Продукт с ID " + product.getId() + " не существует");
        }

        delete(product.getId());
        create(product);
    }

    @Override
    public void delete(Long id) {
        Optional<Product> existingProductOpt = read(id);
        if(existingProductOpt.isEmpty()){
            throw new IllegalArgumentException("Продукт с ID " + id + " не существует");
        }

        products.remove(existingProductOpt.get());
    }
}
