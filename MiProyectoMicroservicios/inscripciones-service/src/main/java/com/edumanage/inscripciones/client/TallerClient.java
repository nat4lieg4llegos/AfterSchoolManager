package com.edumanage.inscripciones.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TallerClient {

    private static final Logger log = LoggerFactory.getLogger(TallerClient.class);

    private final WebClient tallerWebClient;

    public TallerClient(WebClient tallerWebClient) {
        this.tallerWebClient = tallerWebClient;
    }

    // Devuelve true solo si el taller existe (respuesta exitosa).
    // Cualquier error (404, 500, timeout, etc.) se interpreta como que el taller no es valido.
    public boolean existeTaller(Long tallerId) {
        try {
            tallerWebClient.get()
                    .uri("/api/v1/talleres/{id}", tallerId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (Exception e) {
            log.error("No se pudo verificar el taller id={} en talleres-service: {}", tallerId, e.getMessage());
            return false;
        }
    }
}
