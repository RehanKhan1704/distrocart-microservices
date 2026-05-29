package com.github.RehanKhan1704;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id",nullable=false)
    Long userId;

    @Column(name = "product_id",nullable=false)
    Long productId;

    @Column(name = "quantity",nullable=false)
    Integer quantity;

    @Column(name = "total_price",nullable=false)
    Double totalPrice;

    @Column(name = "status",nullable=false)
    String paymentStatus;
}