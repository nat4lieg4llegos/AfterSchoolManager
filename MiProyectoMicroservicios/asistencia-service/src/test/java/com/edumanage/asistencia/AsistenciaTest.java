package com.edumanage.asistencia;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.asistencia.model.RegistroAsistencia;
import java.time.LocalDateTime;

class AsistenciaTest {

    Faker faker = new Faker();

    // CP-01: Registrar Asistencia
    @Test
    void testCrearRegistroAsistencia() {
        // Arrange
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEstudianteId(faker.number().randomNumber());
        registro.setTallerId(faker.number().randomNumber());
        registro.setFechaHoraIngreso(LocalDateTime.now());
        registro.setFechaHoraSalida(LocalDateTime.now().plusHours(2));
        registro.setQuienRetira(faker.name().fullName());

        // Act & Assert
        assertNotNull(registro);
        assertNotNull(registro.getEstudianteId());
        assertNotNull(registro.getTallerId());
        assertNotNull(registro.getFechaHoraIngreso());
        assertTrue(registro.getFechaHoraSalida().isAfter(registro.getFechaHoraIngreso()));
    }
}