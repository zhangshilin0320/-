package com.pet.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PetRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetRegistryApplication.class);
    }
}
