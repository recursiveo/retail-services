package com.example.notificationservice;

import com.example.notificationservice.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "orderPlacedTopic")
    public void handleNotification(OrderEvent orderEvent) {
        // TODO:add needed logic
        log.info("notification sent out for order nuber: {}", orderEvent.getOrderNumber());
    }
}
