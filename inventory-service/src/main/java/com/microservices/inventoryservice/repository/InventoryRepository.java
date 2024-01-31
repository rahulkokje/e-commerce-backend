package com.microservices.inventoryservice.repository;

import com.microservices.inventoryservice.model.Inventory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Inventory, Long> {

    Mono<Inventory> findBySkuCode(String skuCode);
}
