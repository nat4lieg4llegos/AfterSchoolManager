package com.edumanage.inscripciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient tallerWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }
}