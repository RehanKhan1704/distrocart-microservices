package com.github.RehanKhan1704.events;

public record PaymentSuccessEvent(

        Long orderId,

        Long productId,

        Integer quantity,

        String transactionId
){
}
