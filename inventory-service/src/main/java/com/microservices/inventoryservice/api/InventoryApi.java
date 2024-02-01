package com.microservices.inventoryservice.api;

import com.microservices.inventoryservice.dto.InventoryRequest;
import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryApi {

    private final InventoryService inventoryService;

    @GetMapping
    public Mono<InventoryResponse> checkProductInStock(
        @RequestParam("sku-code") String skuCode,
        @RequestParam("order-quantity") Integer orderQuantity
    ){
        log.info("Checking inventory");
        return inventoryService.checkInventoryInStock(skuCode, orderQuantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> addInventory(
            @RequestBody InventoryRequest request
    ) {
        return inventoryService.addInventory(request);
    }
}
