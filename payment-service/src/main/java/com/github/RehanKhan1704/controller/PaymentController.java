package com.github.RehanKhan1704.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.RehanKhan1704.dto.PaymentResponse;
import com.github.RehanKhan1704.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse>
    getPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getPayment(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>>
    getAllPayments(
            Pageable pageable) {

        return ResponseEntity.ok(
                service.getAllPayments(pageable)
        );
    }
}