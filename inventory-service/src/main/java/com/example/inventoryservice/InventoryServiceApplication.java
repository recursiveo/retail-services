package com.example.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
