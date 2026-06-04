package com.github.RehanKhan1704.dto;

import com.github.RehanKhan1704.entity.PaymentStatus;

public record PaymentResponse(

        Long id,

        Long orderId,

        Double amount,

        PaymentStatus status,

        String transactionId
) {
}