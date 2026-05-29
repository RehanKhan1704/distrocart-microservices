package com.github.RehanKhan1704;

import org.springframework.stereotype.Service;

import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public void print(){
        
    }

    public InventoryResponse createInventory(InventoryRequest request){
        
        Inventory inventory =  inventoryMapper.toEntity(request);
        Inventory saved = inventoryRepository.save(inventory);
        return inventoryMapper.toResponseDto(saved);

    }

    public InventoryResponse getByProductId(Long productId){
        Inventory inventory = inventoryRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found for productId: " + productId));
        return inventoryMapper.toResponseDto(inventory);
    }

    public boolean isInStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Inventory not found for productId: " + productId));
        int avaliable = inventory.getQuantity() -inventory.getReservedQuantity();
        return avaliable >= quantity;
    }

    public void reserveStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Inventory not found for productId: " + productId));
        if(isInStock(productId, quantity)){
            inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
            inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Insufficient stock for productId: " + productId);
        }
    }

    public void deductStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Inventory not found for productId: " + productId));
        if(inventory.getReservedQuantity() >= quantity){
            inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Insufficient reserved stock for productId: " + productId);
        }
    }
}
