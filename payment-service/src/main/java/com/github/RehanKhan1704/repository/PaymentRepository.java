package com.github.RehanKhan1704.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.RehanKhan1704.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
}
