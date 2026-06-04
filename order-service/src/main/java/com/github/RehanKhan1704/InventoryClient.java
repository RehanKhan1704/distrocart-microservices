package com.github.RehanKhan1704;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    
    @GetMapping("/api/inventories/check-stock")
    Boolean getStocks(
        @RequestParam("productId") Long productId,
        @RequestParam("quantity") Integer quantity
    );

     @PostMapping("/api/inventories/reserve")
    void reserveStock(
        @RequestParam("orderId") Long orderId,
         @RequestParam("productId") Long productId,
         @RequestParam("quantity") Integer quantity
    );

        @PostMapping("/api/inventories/release")
        void releaseStock(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity);

        @PostMapping("/api/inventories/deduct")
        void deductStock(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity);
}
