package com.edumanage.tutores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TutoresApplication {
    public static void main(String[] args) {
        SpringApplication.run(TutoresApplication.class, args);
    }
}