package com.afterschool.estudiante;

import com.afterschool.estudiante.model.Estudiante;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteServiceTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    // CP-01: Verificar que un estudiante puede crearse correctamente
    @Test
    void testCrearEstudiante() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(faker.name().firstName());
        estudiante.setApellido(faker.name().lastName());
        estudiante.setRut(faker.idNumber().valid());
        estudiante.setFechaNacimiento(LocalDate.of(2010, 5, 15));
        estudiante.setApoderadoId(1L);

        // Act & Assert
        assertNotNull(estudiante);
        assertNotNull(estudiante.getRut());
        assertNotNull(estudiante.getNombre());
        assertNotNull(estudiante.getApellido());
        assertNotNull(estudiante.getFechaNacimiento());
    }

    // CP-02: Verificar que el nombre no sea nulo
    @Test
    void testNombreNoNulo() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(faker.name().firstName());

        // Act & Assert
        assertNotNull(estudiante.getNombre());
        assertFalse(estudiante.getNombre().isEmpty());
    }

    // CP-03: Verificar que el RUT no sea nulo
    @Test
    void testRutNoNulo() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setRut(faker.idNumber().valid());

        // Act & Assert
        assertNotNull(estudiante.getRut());
    }

    // CP-04: Verificar fecha de nacimiento
    @Test
    void testFechaNacimiento() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        LocalDate fecha = LocalDate.of(2012, 3, 20);
        estudiante.setFechaNacimiento(fecha);

        // Act & Assert
        assertNotNull(estudiante.getFechaNacimiento());
        assertEquals(fecha, estudiante.getFechaNacimiento());
    }

    // CP-05: Verificar apoderado asociado
    @Test
    void testApoderadoAsociado() {
        // Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setApoderadoId(1L);

        // Act & Assert
        assertNotNull(estudiante.getApoderadoId());
        assertEquals(1L, estudiante.getApoderadoId());
    }
}