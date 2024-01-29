package com.microservices.productservice.service;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Void> createProduct(ProductRequest request) {
        return Mono.just(Product.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .price(request.getPrice())
                    .build()
                )
                .flatMap(productRepository::save)
                .doOnSuccess(product -> log.info("Product saved to database:: {}", product))
                .then();
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
