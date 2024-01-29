package com.microservices.orderservice.integration;

import com.microservices.orderservice.helpers.OrderHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class OrderControllerTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("orders")
            .withUsername("root")
            .withPassword("root");

    @Autowired
    WebTestClient webTestClient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        var jdbcUrl = container.getJdbcUrl();
        var r2dbcUrl = jdbcUrl.replace("jdbc", "r2dbc");
        dynamicPropertyRegistry.add("spring.r2dbc.url", () -> r2dbcUrl);
        dynamicPropertyRegistry.add("spring.liquibase.url", () -> jdbcUrl);
    }

    @Test
    void shouldPlaceOrder() {
        var request = OrderHelper.getValidOrderRequest();

        webTestClient.post()
                .uri("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();
    }
}
