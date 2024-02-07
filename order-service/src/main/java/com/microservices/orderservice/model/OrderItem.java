package com.microservices.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_order_item")
public class OrderItem {

    @Id
    private Long id;
    private String skuCode;
    private Double price;
    private Integer quantity;
    private Long orderId;

    public Double calculateTotalPrice() {
        return price * quantity;
    }
}
