package com.microservices.orderservice.api;

import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/{orderNumber}")
    public Mono<Order> getOrderDetails(
            @PathVariable String orderNumber
    ) {
        return orderService.getOrderDetails(orderNumber);
    }
}
