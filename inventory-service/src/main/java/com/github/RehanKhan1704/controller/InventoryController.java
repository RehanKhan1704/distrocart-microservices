package com.github.RehanKhan1704.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;
import com.github.RehanKhan1704.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Inventory ApIs",
    description = "Inventory Management ApIs"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {
    
    private final InventoryService inventoryService;

    @Operation(
        summary = "Get All Products",
        description = "Print All the inventory items"
    )
    @ApiResponses(
        @ApiResponse(
            responseCode = "200",
            description = "Fetch items with pagination"
        )
    )
    @GetMapping
    public ResponseEntity<Page<InventoryResponse>> getAllInventory(Pageable pageable){
        return ResponseEntity.ok(inventoryService.getAllInventory(pageable));
    }

    @Operation(
        summary = "Check Stock Availability",
        description = "Checks whether requested quantity is available"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Stock Check Successful"
        )
})
    @GetMapping("/check-stock")
    public ResponseEntity<Boolean> checkStock(@RequestParam("productId") Long productId, @RequestParam ("quantity") Integer quantity){
        return ResponseEntity.ok(inventoryService.isInStock(productId, quantity));
    }

    @Operation(
        summary = "Get Inventory Item",
        description = "Get Inventory Item by Id"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Item fetched Succesfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Item not found"
        )
    })
    @GetMapping("/{id}")
    @Cacheable(value = "inventory", key = "#id")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @Operation(
        summary = "create new Item",
        description = "Add new Item to the inventory"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Product created successfully"
    )
    })
    @PostMapping
    public ResponseEntity<InventoryResponse> createProduct(@Valid @RequestBody InventoryRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
 }

    @Operation(
        summary = "Update Inventory Item",
        description = "Update Inventory Item"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Product updated succesfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "PRoduct Not Found"
        )
    })
    @PutMapping("/{id}")
    @CacheEvict(value = "inventory",key = "#id")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable("id") Long id, @Valid @RequestBody InventoryRequest request){
        return ResponseEntity.ok(inventoryService.updateInventory(id, request));
    }
    @Operation(
        summary = "Delete Inventory item",
        description = "Delete Inventory by id"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Delete successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Item not Found"
        )
})
    @CacheEvict(value = "inventory",key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable("id") Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary ="Reserve Stock",
        description = "Reserved stock after order completion"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Stock reserved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Insufficient stock available"
        )
    })
    @PostMapping("/reserve")
    public ResponseEntity<Void> reserveStock(@RequestParam("orderId") Long orderId, @RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity){
        inventoryService.reserveStock(orderId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Deduct Stock",
        description = "Deduct stock for a product after order confirmation"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Stock deducted successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Insufficient reserved stock to deduct"
        )
    })
    @PostMapping("/deduct")
public ResponseEntity<Void> deductStock(
        @RequestParam("productId") Long productId,
        @RequestParam("quantity") Integer quantity){

    inventoryService.deductStock(
            productId,
            quantity
    );

    return ResponseEntity.ok().build();
}

    @Operation(
        summary = "Release Stock",
        description = "Release reserved stock for a product"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Stock released successfully"
        )
    })
    @PostMapping("/release")
    public ResponseEntity<Void> releaseStock(
        @RequestParam("productId") Long productId,
        @RequestParam("quantity") Integer quantity){

    inventoryService.releaseStock(
            productId,
            quantity
    );

    return ResponseEntity.ok().build();
}
}
