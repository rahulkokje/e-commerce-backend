package com.microservices.orderservice.event.publisher;

import com.microservices.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${spring.kafka.topics.order-creation-topic}")
    private String orderCreatedEventTopic;

    public void publish(OrderCreatedEvent event) {

        Message<OrderCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, orderCreatedEventTopic)
                .build();

        kafkaTemplate.send(message);
        log.info("OrderCreated event published with payload - {}", message);
    }
}
