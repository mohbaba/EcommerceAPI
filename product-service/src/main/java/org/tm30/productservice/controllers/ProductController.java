package org.tm30.productservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tm30.productservice.dtos.requests.AddProductRequest;
import org.tm30.productservice.dtos.responses.ProductResponse;
import org.tm30.productservice.models.Product;
import org.tm30.productservice.services.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        Product product = productService.getProduct(id);
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> responses = products.stream()
            .map(product -> modelMapper.map(product, ProductResponse.class))
            .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        Product createdProduct = productService.addProduct(addProductRequest);
        ProductResponse response = modelMapper.map(createdProduct, ProductResponse.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id") Long id,
            @Valid @RequestBody AddProductRequest updateProductRequest) {
        Product updatedProduct = productService.updateProduct(id, updateProductRequest);
        ProductResponse response = modelMapper.map(updatedProduct, ProductResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
