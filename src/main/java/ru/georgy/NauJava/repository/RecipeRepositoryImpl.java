package ru.georgy.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.georgy.NauJava.model.Recipe;
import ru.georgy.NauJava.model.RecipeProduct;

import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    
    private final EntityManager entityManager;
    
    @Autowired
    public RecipeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public List<Recipe> findRecipesWithProductAndCalories(Long productId, Double maxCalories) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        
        Root<Recipe> recipeRoot = query.from(Recipe.class);
        
        Subquery<RecipeProduct> subquery = query.subquery(RecipeProduct.class);
        Root<RecipeProduct> rpRoot = subquery.from(RecipeProduct.class);
        
        subquery.select(rpRoot)
                .where(
                    cb.and(
                        cb.equal(rpRoot.get("recipe"), recipeRoot),
                        cb.equal(rpRoot.get("product").get("id"), productId)
                    )
                );
        
        Predicate caloriesPredicate = cb.lessThan(recipeRoot.get("totalCalories"), maxCalories);
        Predicate productPredicate = cb.exists(subquery);
        
        query.select(recipeRoot)
             .where(cb.and(caloriesPredicate, productPredicate))
             .orderBy(cb.asc(recipeRoot.get("totalCalories")));
        
        return entityManager.createQuery(query).getResultList();
    }
} 