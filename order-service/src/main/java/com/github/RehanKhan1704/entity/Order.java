package com.github.RehanKhan1704.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "total_price")
    Double totalPrice;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @Column(name = "created_at",nullable = false,updatable = false)
    LocalDateTime createdAt;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @PrePersist
    public void prePersist(){
    createdAt = LocalDateTime.now();
    }
}