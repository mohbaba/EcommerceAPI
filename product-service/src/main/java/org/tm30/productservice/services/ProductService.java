package org.tm30.productservice.services;

import org.tm30.productservice.dtos.requests.AddProductRequest;
import org.tm30.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> getAllProducts();
    Product addProduct(AddProductRequest productRequest);
    Product updateProduct(Long id, AddProductRequest updateProductRequest);
    void deleteProduct(Long id);

}
