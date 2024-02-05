package com.microservices.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("transactions")
public class Transaction {

    @Id
    private Long id;
    private String userId;
    private BigDecimal amount;
    private String source;
    private Timestamp transactionTime;
    private String transactionType;
}
