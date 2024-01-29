package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderItem;
import com.microservices.orderservice.repository.OrderItemRepository;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Mono<String> placeOrder(OrderRequest request) {
        var newOrder = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        return orderRepository.save(newOrder)
                .flatMap(order -> Flux.fromIterable(request.getOrderItems())
                            .map(orderItemDto -> OrderItem.builder()
                                    .skuCode(orderItemDto.getSkuCode())
                                    .price(orderItemDto.getPrice())
                                    .quantity(orderItemDto.getQuantity())
                                    .orderId(order.getId())
                                    .build()
                            )
                            .flatMap(orderItemRepository::save)
                            .then(Mono.just(order.getOrderNumber()))
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
