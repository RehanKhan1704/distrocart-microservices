package com.github.RehanKhan1704.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.RehanKhan1704.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

    Optional<Inventory> findByProductId(Long productId);

}
