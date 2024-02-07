package com.microservices.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("t_order")
public class Order {

    @Id
    private Long id;
    private String orderNumber;
    private String status;

    @Transient
    @ReadOnlyProperty
    private List<OrderItem> orderItems;

    public Double getTotalAmount() {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .reduce(0d, Double::sum);
    }
}
