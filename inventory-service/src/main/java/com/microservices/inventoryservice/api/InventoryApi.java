package com.microservices.inventoryservice.api;

import com.microservices.inventoryservice.dto.InventoryResponse;
import com.microservices.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
