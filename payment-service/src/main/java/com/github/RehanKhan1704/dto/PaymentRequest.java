package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequest(

        @NotNull
        Long orderId,

        @NotNull
        @Positive
        Double amount
) {
}
