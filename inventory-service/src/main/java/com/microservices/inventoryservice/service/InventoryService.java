package com.microservices.inventoryservice.service;

import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Mono<InventoryResponse> checkInventoryInStock(String skuCodesList, Integer orderQuantity) {
        log.info("Checking Inventory");
        return inventoryRepository.findBySkuCode(skuCodesList)
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() >= orderQuantity)
                        .build()
                );
    }
}
