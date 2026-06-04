package com.github.RehanKhan1704.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;
import com.github.RehanKhan1704.entity.Inventory;
import com.github.RehanKhan1704.events.InventoryFailedEvent;
import com.github.RehanKhan1704.events.InventoryReservedEvent;
import com.github.RehanKhan1704.exception.InsufficientStockException;
import com.github.RehanKhan1704.exception.InventoryNotFoundException;
import com.github.RehanKhan1704.kafka.InventoryProducer;
import com.github.RehanKhan1704.mapper.InventoryMapper;
import com.github.RehanKhan1704.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final InventoryProducer inventoryProducer;

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest request){
        Inventory inventory =  inventoryMapper.toEntity(request);
        return inventoryMapper.toResponseDto(inventoryRepository.save(inventory));
    }

    @Transactional(readOnly = true)
    @Override
    public InventoryResponse getInventoryById(Long id){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new InventoryNotFoundException(id));
        return inventoryMapper.toResponseDto(inventory);
    }

    @Override
    public Page<InventoryResponse> getAllInventory(Pageable pageable){
        return inventoryRepository.findAll(pageable).map(inventoryMapper::toResponseDto);
    }
    
    @Override
    public InventoryResponse updateInventory(Long id, InventoryRequest request){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new InventoryNotFoundException(id));
        inventory.setProductId(request.productId());
        inventory.setQuantity(request.quantity());
        inventory.setReservedQuantity(0);
        return inventoryMapper.toResponseDto(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id){
        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new InventoryNotFoundException(id));
        inventoryRepository.delete(inventory);
    }
    
    @Override
    public Boolean isInStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findByProductId(productId)
        .orElseThrow(() -> new InventoryNotFoundException(productId));
        int available = inventory.getQuantity() - inventory.getReservedQuantity();
        return available >= quantity;
    }

    @Override
    public void reserveStock(Long orderId, Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findByProductId(productId)
        .orElseThrow(() -> new InventoryNotFoundException(productId));
        if(isInStock(productId, quantity)){
            inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
            inventoryRepository.save(inventory);
            inventoryProducer.publishReserved(
                new InventoryReservedEvent(
                        orderId,
                        productId,
                        quantity
                )
        );
        } else {
            inventoryProducer.publishFailed(
                new InventoryFailedEvent(
                        orderId,
                        productId,
                        "Insufficient Stock"
                )
        );
        }
    }

    @Override
    public void deductStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findByProductId(productId)
        .orElseThrow(() -> new InventoryNotFoundException(productId));
        if(inventory.getReservedQuantity() >= quantity){
            inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
        } else {
            throw new InsufficientStockException(productId);
        }
    }

    @Override
    public void releaseStock(Long productId, Integer quantity){
        Inventory inventory = inventoryRepository.findByProductId(productId)
        .orElseThrow(() -> new InventoryNotFoundException(productId));
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
