package com.github.RehanKhan1704.dto;

import com.github.RehanKhan1704.entity.PaymentStatus;

import com.github.RehanKhan1704.entity.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderResponse(
    @Schema(description = "Unique identifier of the order", example = "1")
    Long id,
    @Schema(description = "Unique identifier of the product", example = "1")
    Long productId,
    @Schema(description = "Quantity of the product ordered", example = "2")
    Integer quantity,
    @Schema(description = "Total price of the order", example = "19.98")
    Double totalPrice,
    @Schema(description = "Payment status of the order", example = "PENDING")
    PaymentStatus paymentStatus,
    @Schema(description = "Order Status", example = "CANCELLED")
    OrderStatus orderStatus

){
    
}
