package org.tm30.productservice.services;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm30.productservice.dtos.requests.AddProductRequest;
import org.tm30.productservice.exceptions.ProductNotFoundException;
import org.tm30.productservice.models.Product;
import org.tm30.productservice.repositories.ProductRepository;

import java.util.List;

//@AllArgsConstructor
@Service
@Setter
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Product getProduct(Long id) {
        log.info("Product Service currently running get product");
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %d not found", id)));
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Product Service currently running get all product");
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(AddProductRequest productRequest) {
        log.info("Product Service currently running add product");
        Product product = modelMapper.map(productRequest, Product.class);
        log.info("Product successfully added");
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, AddProductRequest updateProductRequest) {
        log.info("Product Service currently running update product");
        Product existingProduct = getProduct(id);
        log.info("Product successfully updated");
        return productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Product Service currently running delete product");
        Product product = getProduct(id);
        productRepository.delete(id);
        log.info("Product successfully deleted");

    }


}
