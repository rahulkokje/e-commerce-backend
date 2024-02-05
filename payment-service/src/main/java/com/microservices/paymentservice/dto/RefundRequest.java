package com.microservices.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {

    private String userId;
    private BigDecimal amount;
    private String source;
}
