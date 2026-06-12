package com.edumanage.tutores;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.tutores.model.Tutor;
import java.time.LocalDate;

class TutoresTest {

    Faker faker = new Faker();

    @Test
    void testCrearTutor() {
        // Arrange
        Tutor tutor = new Tutor();
        tutor.setRut("12345678-9");
        tutor.setNombreCompleto(faker.name().fullName());
        tutor.setFechaContratacion(LocalDate.now());

        // Act & Assert
        assertNotNull(tutor);
        assertNotNull(tutor.getRut());
        assertNotNull(tutor.getNombreCompleto());
        assertNotNull(tutor.getFechaContratacion());
    }
}