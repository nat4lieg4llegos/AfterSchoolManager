package com.edumanage.inscripciones;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.inscripciones.model.Inscripcion;
import java.time.LocalDateTime;

class InscripcionesTest {

    Faker faker = new Faker();

    @Test
    void testCrearInscripcion() {
        // Arrange
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(faker.number().randomNumber());
        inscripcion.setTallerId(faker.number().randomNumber());
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcion.setEstado("ACTIVA");

        // Act & Assert
        assertNotNull(inscripcion);
        assertNotNull(inscripcion.getEstudianteId());
        assertNotNull(inscripcion.getTallerId());
        assertNotNull(inscripcion.getFechaInscripcion());
        assertEquals("ACTIVA", inscripcion.getEstado());
    }
}