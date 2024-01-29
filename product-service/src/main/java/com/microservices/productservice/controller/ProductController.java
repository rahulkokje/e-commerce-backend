package com.microservices.productservice.controller;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createProduct(
            @RequestBody ProductRequest request
    ) {
        return productService.createProduct(request);
    }

    @GetMapping
    public Flux<ProductResponse> getAllProducts() {
        return productService.getAllProducts()
                .map(ProductResponse::of);
    }
}
