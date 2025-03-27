package ru.georgy.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.georgy.NauJava.model.Product;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    
    private final EntityManager entityManager;
    
    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public List<Product> findProductsByProteinsAndCalories(Double minProteins, Double maxCalories) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        
        Predicate proteinsPredicate = cb.greaterThan(productRoot.get("proteins"), minProteins);
        Predicate caloriesPredicate = cb.lessThan(productRoot.get("calories"), maxCalories);
        
        query.select(productRoot)
             .where(cb.and(proteinsPredicate, caloriesPredicate))
             .orderBy(
                 cb.desc(productRoot.get("proteins")), 
                 cb.asc(productRoot.get("calories"))
             );
        
        return entityManager.createQuery(query).getResultList();
    }
} 