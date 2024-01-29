package com.microservices.productservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.productservice.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
public class ProductControllerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongo.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() {
        var productRequest = getProductRequest();

        webTestClient.post()
                        .uri("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(productRequest))
                        .exchange()
                        .expectStatus().isCreated();
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone 15")
                .description("Iphone 15")
                .price(BigDecimal.valueOf(999.99))
                .build();
    }
}
