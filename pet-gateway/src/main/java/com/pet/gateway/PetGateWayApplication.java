package com.pet.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient // 开启服务的发现客户端
@EnableZuulProxy  // 开启网关
public class PetGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetGateWayApplication.class);
    }
}
