package com.example.productservice.config;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    @Override
    public void run(String... args) throws Exception {
        Product product = Product.builder()
                .name("Apple")
                .description("Green Apple")
                .price(BigDecimal.valueOf(3.3))
                .build();

        productRepository.save(product);
    }
}
