package com.github.RehanKhan1704;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    
    @GetMapping("/api/inventories/check-stock")
    Boolean getStocks(
        @RequestParam("productId") Long productId,
     @RequestParam("quantity") Integer quantity);
}
