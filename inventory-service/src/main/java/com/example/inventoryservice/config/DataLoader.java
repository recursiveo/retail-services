package com.example.inventoryservice.config;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {

        inventoryRepository.truncateTable();

        Inventory desk = new Inventory();
        desk.setSkuCode("DEK1245");
        desk.setQuantity(20);

        Inventory chair = new Inventory();
        chair.setSkuCode("CHR2345");
        chair.setQuantity(40);

        inventoryRepository.saveAll(List.of(desk, chair));

    }
}
