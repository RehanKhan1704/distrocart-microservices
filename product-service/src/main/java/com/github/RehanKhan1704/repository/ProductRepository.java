package com.github.RehanKhan1704.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.RehanKhan1704.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
