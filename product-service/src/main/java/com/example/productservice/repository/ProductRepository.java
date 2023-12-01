package com.example.productservice.repository;

import com.example.productservice.model.Product;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface ProductRepository extends MongoRepository<Product, String> {
}
