package com.example.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(EurekaServer.class, args);
    }
}
