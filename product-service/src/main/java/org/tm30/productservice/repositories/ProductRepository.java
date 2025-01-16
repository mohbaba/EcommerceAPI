package org.tm30.productservice.repositories;

import org.springframework.stereotype.Repository;
import org.tm30.productservice.exceptions.ProductNotFoundException;
import org.tm30.productservice.models.Product;

import java.util.*;

@Repository
public class ProductRepository {
    private final Map<Long, Product> productRepo = new HashMap<>();
    private long nextId = 1;

    public Product save(Product product) {
        if (product.getId() != null) {
            update(product.getId(), product);
        } else {
            product.setId(nextId++);
        }
        productRepo.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productRepo.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productRepo.values());
    }

    public Product update(Long id, Product product) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        product.setId(id);
        productRepo.put(id, product);
        return product;
    }

    public void delete(Long id) {
        if (!productRepo.containsKey(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepo.remove(id);
    }

    public void clear() {
        productRepo.clear();
        nextId = 1;
    }
}
