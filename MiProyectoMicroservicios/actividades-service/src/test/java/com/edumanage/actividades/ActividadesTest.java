package com.edumanage.actividades;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.actividades.model.Actividad;
import java.time.LocalDate;

class ActividadesTest {

    Faker faker = new Faker();

    // CP-01: Registrar Actividad
    @Test
    void testCrearActividad() {
        // Arrange
        Actividad actividad = new Actividad();
        actividad.setTallerId(faker.number().randomNumber());
        actividad.setTitulo(faker.educator().course());
        actividad.setDescripcion(faker.lorem().sentence());
        actividad.setFechaProgramada(LocalDate.now().plusDays(faker.number().numberBetween(1, 30)));

        // Act & Assert
        assertNotNull(actividad);
        assertNotNull(actividad.getTitulo());
        assertNotNull(actividad.getDescripcion());
        assertNotNull(actividad.getFechaProgramada());
        assertTrue(actividad.getTallerId() > 0);
    }
}