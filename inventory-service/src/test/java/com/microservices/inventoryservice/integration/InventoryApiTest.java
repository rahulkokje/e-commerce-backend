package com.microservices.inventoryservice.integration;

import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.model.Inventory;
import com.microservices.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
public class InventoryApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("inventory")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void setDynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        var jdbcUrl = container.getJdbcUrl();
        var r2dbcUrl = jdbcUrl.replace("jdbc", "r2dbc");
        dynamicPropertyRegistry.add("spring.r2dbc.url", () -> r2dbcUrl);
        dynamicPropertyRegistry.add("spring.liquibase.url", () -> jdbcUrl);
    }

    void insertData() {
        var inventoryItem = Inventory.builder()
                .skuCode("IPHONE_13")
                .quantity(2)
                .build();
        inventoryRepository.save(inventoryItem);
    }

    @Test
    void shouldFetchInventoryDetails() {
        webTestClient.get()
                .uri("/api/v1/inventory?sku-code=IPHONE_13&order-quantity=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryResponse.class);
    }
}
