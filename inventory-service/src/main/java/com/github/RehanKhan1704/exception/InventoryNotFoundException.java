package com.github.RehanKhan1704.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(Long id){
        super("Inventory does not exist "+id);
    }
}
