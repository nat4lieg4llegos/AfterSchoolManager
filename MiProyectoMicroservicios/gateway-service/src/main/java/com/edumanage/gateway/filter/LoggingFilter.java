package com.edumanage.gateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(-1)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long inicio = System.currentTimeMillis();

        // Registra la peticion ENTRANTE al gateway
        logger.info(">> ENTRADA: {} {}", request.getMethod(), request.getRequestURI());

        // Deja pasar la peticion al microservicio correspondiente
        filterChain.doFilter(request, response);

        long duracion = System.currentTimeMillis() - inicio;

        // Registra la respuesta SALIENTE con su codigo y tiempo
        logger.info("<< SALIDA: {} {} -> status {} ({} ms)",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duracion);
    }
}