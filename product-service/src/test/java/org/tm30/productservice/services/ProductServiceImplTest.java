package org.tm30.productservice.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tm30.productservice.dtos.requests.AddProductRequest;
import org.tm30.productservice.exceptions.ProductNotFoundException;
import org.tm30.productservice.models.Product;
import org.tm30.productservice.repositories.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl(new ModelMapper());
        productService.setProductRepository(productRepository);
    }


    @Test
     public void testGetProduct_ExistingProduct() {
        Long productId = 1L;
        Product product = new Product(null, "Product A", "Description A", 100.0, 10);
        product = productRepository.save(product);

        Product result = productService.getProduct(product.getId());

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    void testGetProduct_NonExistingProduct() {
        Long productId = 1L;

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getProduct(productId));
        assertEquals("Product with id 1 not found", exception.getMessage());
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product(null, "Product A", "Description A", 100.0, 10);
        Product product2 = new Product(null, "Product B", "Description B", 200.0, 20);

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testAddProduct() {
        AddProductRequest request = new AddProductRequest("Product A", 100.0, 10);

        Product result = productService.addProduct(request);

        assertNotNull(result);
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getPrice(), result.getPrice());
        assertEquals(request.getStockQuantity(), result.getStockQuantity());
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product(null, "Product A", "Description A", 100.0, 10);
        existingProduct = productRepository.save(existingProduct);

        AddProductRequest request = new AddProductRequest("Updated Description", 150.0, 15);

        Product result = productService.updateProduct(productId, request);

        assertNotNull(result);
        assertNotEquals(request.getDescription(), result.getDescription());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product(null, "Product A", "Description A", 100.0, 10);
        product = productRepository.save(product);
        Long productId = product.getId();
        productService.deleteProduct(product.getId());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getProduct(productId));
        assertEquals("Product with id 1 not found", exception.getMessage());
    }
}
