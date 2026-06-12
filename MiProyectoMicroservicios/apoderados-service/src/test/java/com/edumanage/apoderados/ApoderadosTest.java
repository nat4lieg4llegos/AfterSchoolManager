package com.edumanage.apoderados;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.apoderados.model.Apoderado;

class ApoderadosTest {

    Faker faker = new Faker();

    // CP-01: Registrar Apoderado
    @Test
    void testCrearApoderado() {
        // Arrange
        Apoderado apoderado = new Apoderado();
        apoderado.setRut("12345678-9");
        apoderado.setNombreCompleto(faker.name().fullName());
        apoderado.setCorreo(faker.internet().emailAddress());
        apoderado.setTelefono(faker.phoneNumber().phoneNumber());

        // Act & Assert
        assertNotNull(apoderado);
        assertNotNull(apoderado.getRut());
        assertNotNull(apoderado.getNombreCompleto());
        assertNotNull(apoderado.getCorreo());
        assertTrue(apoderado.getCorreo().contains("@"));
    }
}