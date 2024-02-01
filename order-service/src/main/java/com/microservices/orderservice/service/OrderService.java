package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderItem;
import com.microservices.orderservice.repository.OrderItemRepository;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient webClient;

    private Mono<Boolean> checkIfProductIsInStock(String skuCode, Integer orderQuantity) {
        return webClient.get()
                .uri(String.format("/api/v1/inventory?sku-code=%s&order-quantity=%d", skuCode, orderQuantity))
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .map(InventoryResponse::getIsInStock);
    }

    @Transactional
    public Mono<String> placeOrder(OrderRequest request) {
        var newOrder = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        return orderRepository.save(newOrder)
                .flatMap(order -> Flux.fromIterable(request.getOrderItems())
                            .flatMap(orderItemDto -> checkIfProductIsInStock(orderItemDto.getSkuCode(), orderItemDto.getQuantity())
                                    .flatMap(isInStock -> {
                                        if(isInStock) {
                                            var orderItem = OrderItem.builder()
                                                    .skuCode(orderItemDto.getSkuCode())
                                                    .price(orderItemDto.getPrice())
                                                    .quantity(orderItemDto.getQuantity())
                                                    .orderId(order.getId())
                                                    .build();

                                            return orderItemRepository.save(orderItem);
                                        }
                                        log.warn("Item with sku code {} is out of stock.", orderItemDto.getSkuCode());
                                        return Mono.empty();
                                    })
                            )
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
