package org.tm30.orderservice.eventpublishers;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.tm30.orderservice.dtos.requests.GetProductEvent;
import org.tm30.orderservice.dtos.responses.ProductResponse;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class GetUserProducer {
    private final PulsarClient pulsarClient;

    private static final String SERVICE_URL = "pulsar://localhost:6650"; // Pulsar service URL
    private static final String TOPIC_NAME = "persistent://public/default/get-user-topic"; // Pulsar topic

    public GetUserProducer(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }



//    public void publishMessage(Long productId){
//        Producer<GetProductEvent> producer;
//        try {
//            producer = pulsarClient.newProducer(Schema.JSON(GetProductEvent.class))
//                    .topic("product-event")
//                    .create();
//            GetProductEvent getProductEvent = new GetProductEvent(productId);
//            producer.send(getProductEvent);
//
//            producer.close();
//            pulsarClient.close();
//        } catch (PulsarClientException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public CompletableFuture<ProductResponse> publishMessage(Long productId) {
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

        CompletableFuture<ProductResponse> futureResponse = new CompletableFuture<>();
        try {
            Consumer<ProductResponse> consumer = pulsarClient.newConsumer(Schema.JSON(ProductResponse.class))
                    .topic("product-event")
                    .subscriptionName("order-service-subscription")
                    .messageListener((msgConsumer, msg) -> {
                        ProductResponse response = msg.getValue();
                        log.info("Received response for product ID: {}", response.getProductId());

                        futureResponse.complete(response);

                        try {
                            msgConsumer.acknowledge(msg);
                        } catch (PulsarClientException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .subscribe();

            return futureResponse;
        } catch (PulsarClientException e) {
            throw new RuntimeException("Failed to create consumer", e);
        }
    }


}
