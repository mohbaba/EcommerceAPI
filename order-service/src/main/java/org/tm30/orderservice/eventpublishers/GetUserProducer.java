package org.tm30.orderservice.eventpublishers;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.tm30.orderservice.dtos.requests.GetProductEvent;
import org.tm30.orderservice.dtos.responses.Product;
import org.tm30.orderservice.exceptions.OrderNotFoundException;

@Slf4j
public class GetUserProducer {
    private final PulsarClient pulsarClient;

    private Consumer<Product> consumer;

    public GetUserProducer(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }



    public void publishMessage(Long productId) {
        Producer<GetProductEvent> producer;
        try {
            producer = pulsarClient.newProducer(Schema.JSON(GetProductEvent.class))
                    .topic("product-event")
                    .create();

            GetProductEvent getProductEvent = new GetProductEvent(productId);
            log.info("Sending request for product with ID: {}", productId);
            producer.send(getProductEvent);

        } catch (PulsarClientException e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }

     public void consumeGetProductMessage(){
        try {
            consumer = pulsarClient.newConsumer(Schema.JSON(Product.class))
                    .topic("get-product-event")
                    .subscriptionName("get-product-consumer")
                    .messageListener((consumer, msg) -> {
                        try {
                            processMessage(msg);
                            log.info("Message received");
                            consumer.acknowledge(msg);
                            consumer.close();
                        } catch (PulsarClientException e) {
                            consumer.negativeAcknowledge(msg);
                        }
                    })
                    .subscribe();
        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(Message<Product> msg) {
        Product product = msg.getValue();
        if (product.getProductId() == null)
            throw new OrderNotFoundException("Product does not exist");

    }

    @PreDestroy
    public void shutdown() {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (PulsarClientException e) {
                System.err.println("Failed to close Pulsar consumer: " + e.getMessage());
            }
        }
    }

}
