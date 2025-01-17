package org.tm30.productservice.eventpublishers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.tm30.productservice.exceptions.ProductNotFoundException;
import org.tm30.productservice.models.Product;
import org.tm30.productservice.services.ProductService;


//@RequiredArgsConstructor
@Slf4j
public class ProductMessageProducer {
    private final PulsarClient pulsarClient;
    private final ProductService productService;

    public ProductMessageProducer(PulsarClient pulsarClient, ProductService productService) {
        this.pulsarClient = pulsarClient;
        this.productService = productService;
    }

    public void publishMessage(Long productId) {
        Producer<Product> producer;
        try {
            producer = pulsarClient.newProducer(Schema.JSON(Product.class))
                    .topic("get-product-event")
                    .create();

            Product product = getProduct(productId);

            log.info("Sending request for product with ID: {}", productId);
            producer.send(product);

        } catch (PulsarClientException e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }

    public Product getProduct(Long productId){
        try {
            return productService.getProduct(productId);
        }catch (ProductNotFoundException exception){
            return new Product(null, null, "product not found", 0.0, 0);
        }
    }
}
