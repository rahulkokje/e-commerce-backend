package com.microservices.orderservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topics.order-creation-topic}")
    private String orderCreationTopic;

    @Bean
    public NewTopic orderCreationTopic() {
        return TopicBuilder
                .name(orderCreationTopic)
                .build();
    }
}
