package org.tm30.productservice.eventconsumers;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.tm30.productservice.dtos.requests.GetProductEvent;
import org.tm30.productservice.eventpublishers.ProductMessageProducer;
import org.tm30.productservice.exceptions.ProductNotFoundException;
import org.tm30.productservice.services.ProductService;

@Slf4j
public class GetProductConsumer {
    private final PulsarClient pulsarClient;
    private Consumer<GetProductEvent> consumer;
    private final ProductMessageProducer productMessageProducer;


    public GetProductConsumer(PulsarClient pulsarClient, ProductMessageProducer productMessageProducer) {
        this.pulsarClient = pulsarClient;
        this.productMessageProducer = productMessageProducer;

    }

    @PostConstruct
    public void consumeGetProductMessage(){
        try {
            consumer = pulsarClient.newConsumer(Schema.JSON(GetProductEvent.class))
                    .topic("product-event")
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

    private void processMessage(Message<GetProductEvent> msg) {
        GetProductEvent product = msg.getValue();
        productMessageProducer.publishMessage(product.getProductId());
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
