package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="orderServiceBreaker", fallbackMethod = "fallbackMethodOrderService")
    @TimeLimiter(name = "inventory")       /* if retry is also used then don't give fallback to @TimeLimiter */
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethodOrderService(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.error(runtimeException.getMessage());
        return CompletableFuture.supplyAsync(() -> "Unable to process request at the moment. Please try again after some time");
    }

    public CompletableFuture<String> retryFallback(OrderRequest orderRequest, TimeoutException ex) {
        log.error(ex.getMessage());
        return CompletableFuture.supplyAsync(() -> "Unable to process request at the moment. Please try again after some time!!");
    }
}
