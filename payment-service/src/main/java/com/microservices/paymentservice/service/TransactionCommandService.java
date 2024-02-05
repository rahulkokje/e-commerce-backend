package com.microservices.paymentservice.service;

import com.microservices.paymentservice.dto.PaymentRequest;
import com.microservices.paymentservice.dto.RefundRequest;
import com.microservices.paymentservice.model.Transaction;
import com.microservices.paymentservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionCommandService {

    private final TransactionRepository transactionRepository;

    public Mono<Transaction> addPayment(PaymentRequest request) {

        return Mono.just(Transaction.builder()
                        .userId(request.getUserId())
                        .amount(request.getAmount())
                        .source(request.getSource())
                        .transactionTime(Timestamp.valueOf(LocalDateTime.now()))
                        .transactionType("PAYMENT")
                        .build()
                )
                .flatMap(transactionRepository::save)
                .doOnSuccess(transaction -> log.info("Created Payment Transaction with ID - {} and amount - {}", transaction.getId(), transaction.getAmount()));
    }

    public Mono<Transaction> addRefund(RefundRequest request) {

        return Mono.just(Transaction.builder()
                        .userId(request.getUserId())
                        .amount(request.getAmount().multiply(BigDecimal.valueOf(-1)))
                        .source(request.getSource())
                        .transactionTime(Timestamp.valueOf(LocalDateTime.now()))
                        .transactionType("REFUND")
                        .build()
                )
                .flatMap(transactionRepository::save)
                .doOnSuccess(transaction -> log.info("Created Refund Transaction with ID - {} and amount - {}", transaction.getId(), transaction.getAmount()));
    }
}
