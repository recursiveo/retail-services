package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Inventory;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Observed
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCodes);

    @Modifying
    @Query(value = "TRUNCATE TABLE RetailServices.t_inventory", nativeQuery = true)
    void truncateTable();
}
