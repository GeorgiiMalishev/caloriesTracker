package ru.georgy.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.georgy.NauJava.model.Recipe;
import ru.georgy.NauJava.model.RecipeProduct;
import ru.georgy.NauJava.model.Product;

import java.util.ArrayList;
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
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        
        Root<Recipe> recipe = query.from(Recipe.class);
        
        Subquery<Long> productExistsSubquery = query.subquery(Long.class);
        Root<RecipeProduct> rpForProduct = productExistsSubquery.from(RecipeProduct.class);
        productExistsSubquery.select(cb.literal(1L))
                .where(
                    cb.and(
                        cb.equal(rpForProduct.get("recipe"), recipe),
                        cb.equal(rpForProduct.get("product").get("id"), productId)
                    )
                );
                
        Join<Recipe, RecipeProduct> recipeProducts = recipe.join("recipeProducts", JoinType.LEFT);
        Join<RecipeProduct, Product> product = recipeProducts.join("product", JoinType.LEFT);
        
        Expression<Number> caloriesPerProduct = cb.prod(
                product.get("calories"), 
                cb.quot(recipeProducts.get("quantity"), 100.0)
        );
        
        query.multiselect(
                recipe,
                cb.sum(caloriesPerProduct).alias("totalCalories")
            )
            .where(cb.exists(productExistsSubquery))
            .groupBy(recipe.get("id"))
            .having(cb.lt(cb.sum(caloriesPerProduct), maxCalories))
            .orderBy(cb.asc(cb.sum(caloriesPerProduct)));
        
        List<Tuple> results = entityManager.createQuery(query).getResultList();
        
        List<Recipe> recipes = new ArrayList<>();
        for (Tuple tuple : results) {
            recipes.add(tuple.get(0, Recipe.class));
        }
        
        return recipes;
    }
} 