package com.edumanage.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TalleresApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalleresApplication.class, args);
    }
}