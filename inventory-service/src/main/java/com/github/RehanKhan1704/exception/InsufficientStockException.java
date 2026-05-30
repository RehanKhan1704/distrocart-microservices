package com.github.RehanKhan1704.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId) {
        super("Insufficient stock for productId: " + productId);
    }
}
