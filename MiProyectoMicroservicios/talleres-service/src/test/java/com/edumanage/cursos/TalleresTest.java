package com.edumanage.cursos;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.model.Horario;
import java.time.LocalTime;

class TalleresTest {

    Faker faker = new Faker();

    // CP-01: Registrar Taller
    @Test
    void testCrearTaller() {
        // Arrange
        Taller taller = new Taller();
        taller.setNombre(faker.educator().course());
        taller.setSede(faker.address().city());
        taller.setCupoMaximo(faker.number().numberBetween(10, 30));

        // Act & Assert
        assertNotNull(taller);
        assertNotNull(taller.getNombre());
        assertNotNull(taller.getSede());
        assertTrue(taller.getCupoMaximo() > 0);
    }

    // CP-02: Registrar Horario
    @Test
    void testCrearHorario() {
        // Arrange
        Taller taller = new Taller();
        taller.setNombre(faker.educator().course());
        taller.setSede(faker.address().city());
        taller.setCupoMaximo(faker.number().numberBetween(10, 30));

        Horario horario = new Horario();
        horario.setTaller(taller);
        horario.setDiaSemana("Lunes");
        horario.setHoraInicio(LocalTime.of(9, 0));
        horario.setHoraFin(LocalTime.of(11, 0));

        // Act & Assert
        assertNotNull(horario);
        assertNotNull(horario.getTaller());
        assertEquals("Lunes", horario.getDiaSemana());
        assertNotNull(horario.getHoraInicio());
        assertTrue(horario.getHoraFin().isAfter(horario.getHoraInicio()));
    }
}