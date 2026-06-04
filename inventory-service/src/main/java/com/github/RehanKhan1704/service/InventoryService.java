package com.github.RehanKhan1704.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse getInventoryById(Long id);
    Page<InventoryResponse> getAllInventory(Pageable pageable);
    InventoryResponse updateInventory(Long id, InventoryRequest request);
    void deleteInventory(Long productId);
    Boolean isInStock(Long productId, Integer quantity);
    void deductStock(Long productId, Integer Quantity);
    void reserveStock(Long orderId, Long productId, Integer quantity);
    void releaseStock(Long productId, Integer quantity);
}
