package com.microservices.orderservice.event;

import com.microservices.orderservice.model.OrderItem;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private String orderNumber;
    private Double totalAmount;
    private List<OrderItem> orderItems;

    public void countTotalAmount() {
        totalAmount = orderItems.stream()
                        .mapToDouble(OrderItem::calculateTotalPrice)
                        .sum();
    }
}
