package com.example.productservice;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@DynamicPropertySource
	static void setDynamicProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void cleanCollection() {
		mongoTemplate.dropCollection("product");
	}


	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String content = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isCreated());
	}

	@Test
	void shouldReturnAllProducts() throws Exception {
		/* Arrange */
		Product product = Product.builder()
				.name("apple")
				.description("red apple")
				.price(BigDecimal.valueOf(2.5))
				.build();

		mongoTemplate.save(product, "product");

		String response = objectMapper.writeValueAsString(Arrays.asList(product));


		/* Act , Assert */
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpectAll(status().isOk(),
						content().contentType(MediaType.APPLICATION_JSON),
						content().json(response)
//						jsonPath("$[0].name").value("apple"),
//						jsonPath("$[0].price").value("2.5"),
//						jsonPath("$.length()").value(1)
				);
	}

	private ProductRequest getProductRequest() {

        return ProductRequest.builder()
				.name("Dot Pen")
				.description("Its a blue pen")
				.price(BigDecimal.valueOf(1.5))
				.build();
	}

}
