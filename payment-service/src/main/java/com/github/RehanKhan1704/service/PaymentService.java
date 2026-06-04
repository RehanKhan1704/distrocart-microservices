package com.github.RehanKhan1704.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.RehanKhan1704.dto.PaymentResponse;
import com.github.RehanKhan1704.events.OrderCreatedEvent;

public interface PaymentService {

    PaymentResponse getPayment(Long id);

    Page<PaymentResponse> getAllPayments(
            Pageable pageable);

     public void processPayment(OrderCreatedEvent event);
}