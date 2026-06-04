package com.github.RehanKhan1704.exception;

public class OutOfStockException
        extends RuntimeException {

    public OutOfStockException(
            Long productId){

        super(
          "Product out of stock: "
          + productId
        );
    }
}