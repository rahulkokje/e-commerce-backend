package com.microservices.orderservice.helpers;

import com.microservices.orderservice.dto.OrderItemDto;
import com.microservices.orderservice.dto.OrderRequest;

import java.math.BigDecimal;
import java.util.List;

public class OrderHelper {

    public static OrderRequest getValidOrderRequest() {
        var item1 = OrderItemDto.builder()
                .skuCode("IPHONE15_BLACK")
                .price(BigDecimal.valueOf(999.99))
                .quantity(1)
                .build();

        var item2 = OrderItemDto.builder()
                .skuCode("AIRPODS_PRO")
                .price(BigDecimal.valueOf(249.99))
                .quantity(1)
                .build();

        var orderItemsList = List.of(item1, item2);

        return OrderRequest.builder()
                .orderItems(orderItemsList)
                .build();
    }
}
