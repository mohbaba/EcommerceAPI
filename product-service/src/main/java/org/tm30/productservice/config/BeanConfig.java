package org.tm30.productservice.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tm30.productservice.repositories.ProductRepository;

@Configuration
public class BeanConfig {


    @Bean
    public PulsarClient pulsarClient() throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
            .serviceUrl("pulsar://localhost:6650")
            .build();
        return pulsarClient;
    }
}
