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
    void deleteInventory(Long id);
    Boolean isInStock(Long id, Integer quantity);
    void deductStock(Long id, Integer Quantity);
    void reserveStock(Long id, Integer quantity);
}
