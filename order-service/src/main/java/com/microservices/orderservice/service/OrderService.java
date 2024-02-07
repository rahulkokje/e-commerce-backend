package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderItemDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.event.OrderCreatedEvent;
import com.microservices.orderservice.event.publisher.OrderCreatedEventPublisher;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderItem;
import com.microservices.orderservice.repository.OrderItemRepository;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderCreatedEventPublisher orderCreatedEventPublisher;

    private Mono<Order> saveOrderItems(Order order, List<OrderItemDto> orderItemsDtoList) {

        return Flux.fromIterable(orderItemsDtoList)
                .map(orderItemDto -> OrderItem.builder()
                        .orderId(order.getId())
                        .skuCode(orderItemDto.getSkuCode())
                        .price(orderItemDto.getPrice())
                        .quantity(orderItemDto.getQuantity())
                        .build()
                )
                .flatMap(orderItemRepository::save)
                .collectList()
                .map(orderItems -> {
                    order.setOrderItems(orderItems);
                    return order;
                });
    }

    @Transactional
    public Mono<String> placeOrder(OrderRequest request) {

        var newOrder = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status("ORDER_CREATED")
                .build();

        return orderRepository.save(newOrder)
                .flatMap(order -> saveOrderItems(order, request.getOrderItems()))
                .map(order -> {
                        var orderCreatedEvent = OrderCreatedEvent.builder()
                                .orderNumber(order.getOrderNumber())
                                .totalAmount(order.getTotalAmount())
                                .orderItems(order.getOrderItems())
                                .build();

                        log.info("Publishing OrderCreatedEvent");
                        orderCreatedEventPublisher.publish(orderCreatedEvent);

                        return order.getOrderNumber();
                    }
                );
    }

    @Transactional
    public Mono<Order> getOrderDetails(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .flatMap(order -> orderItemRepository.findByOrderId(order.getId())
                                    .collectList()
                                    .map(orderItems -> {
                                        order.setOrderItems(orderItems);
                                        return order;
                                    })
                );
    }
}
