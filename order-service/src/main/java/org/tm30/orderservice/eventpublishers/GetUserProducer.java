package org.tm30.orderservice.eventpublishers;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.tm30.orderservice.dtos.requests.GetProductEvent;

@Slf4j
public class GetUserProducer {
    private final PulsarClient pulsarClient;

    private static final String SERVICE_URL = "pulsar://localhost:6650"; // Pulsar service URL
    private static final String TOPIC_NAME = "persistent://public/default/get-user-topic"; // Pulsar topic

    public GetUserProducer(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }



    public void publishMessage(Long productId){
        Producer<GetProductEvent> producer;
        try {
            producer = pulsarClient.newProducer(Schema.JSON(GetProductEvent.class))
                    .topic("product-event")
                    .create();
            GetProductEvent getProductEvent = new GetProductEvent(productId);
            producer.send(getProductEvent);

            producer.close();
            pulsarClient.close();
        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }
    }


}
