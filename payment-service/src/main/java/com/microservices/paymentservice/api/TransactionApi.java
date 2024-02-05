package com.microservices.paymentservice.api;

import com.microservices.paymentservice.dto.PaymentRequest;
import com.microservices.paymentservice.dto.RefundRequest;
import com.microservices.paymentservice.model.Transaction;
import com.microservices.paymentservice.service.TransactionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionApi {

    private final TransactionCommandService service;

    @PostMapping("/payment")
    public Mono<Transaction> addPayment(
            @RequestBody PaymentRequest request
    ) {
        return service.addPayment(request);
    }

    @PostMapping("/refund")
    public Mono<Transaction> addRefund(
            @RequestBody RefundRequest request
    ) {
        return service.addRefund(request);
    }
}
