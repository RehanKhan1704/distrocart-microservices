package com.github.RehanKhan1704.entity;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;

import java.io.Serializable;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "reserved_quantity",nullable = false)
    @DefaultValue("0")
private Integer reservedQuantity;
}