package com.microservices.orderservice.repository;


import com.microservices.orderservice.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
    Mono<Order> findByOrderNumber(String orderNumber);
}
