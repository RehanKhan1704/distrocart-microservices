package com.github.RehanKhan1704.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.RehanKhan1704.dto.OrderRequest;
import com.github.RehanKhan1704.dto.OrderResponse;
import com.github.RehanKhan1704.service.OrderService;
import com.github.RehanKhan1704.service.OrderServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
    name = "Order APIs",
    description = "Order Management APIs"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(
    summary = "Get All Orders",
    description = "Fetch all orders with pagination"
    )
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @Operation(
    summary = "Get Orders By Product Id",
    description = "Fetches all orders associated with a specific product id")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Orders Found"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No Orders Found for the Product"
        )
    })
    @Cacheable(value = "orders", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrdersByProductId(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrdersById(id));
    }

    @Operation(
    summary = "Create Order",
    description =
      "Creates an order after stock validation")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Order Created Successfully"),
        @ApiResponse(
            responseCode = "503",
            description = "Inventory Service Unavailable"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid Order Request"
        )
    })
    @PostMapping()
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request).toString());
    }

    @Operation(
    summary = "Delete Order",
    description = "Deletes an existing order")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Order Deleted Successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Order Not Found"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
