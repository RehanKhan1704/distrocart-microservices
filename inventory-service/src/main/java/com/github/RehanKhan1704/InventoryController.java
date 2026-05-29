package com.github.RehanKhan1704;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    
    private final InventoryService inventoryService;

    @GetMapping("")
    public void getAllProducts(){
        inventoryService.print();
    }

    @GetMapping("/check-stock")
    public ResponseEntity<Boolean> checkStock(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity){
        return ResponseEntity.ok(inventoryService.isInStock(productId, quantity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getProductById(@PathVariable("id") Long id){
        return ResponseEntity.ok(inventoryService.getByProductId(id));
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createProduct(@Valid @RequestBody InventoryRequest request){
    return ResponseEntity.ok(inventoryService.createInventory(request));
 }}
