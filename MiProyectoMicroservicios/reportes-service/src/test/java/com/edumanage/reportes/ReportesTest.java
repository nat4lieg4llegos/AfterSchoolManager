package com.edumanage.reportes;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.reportes.model.ReporteProgreso;
import java.time.LocalDate;

class ReportesTest {

    Faker faker = new Faker();

    @Test
    void testCrearReporteProgreso() {
        // Arrange
        ReporteProgreso reporte = new ReporteProgreso();
        reporte.setEstudianteId(faker.number().randomNumber());
        reporte.setTallerId(faker.number().randomNumber());
        reporte.setObservaciones(faker.lorem().sentence());
        reporte.setFechaReporte(LocalDate.now());

        // Act & Assert
        assertNotNull(reporte);
        assertNotNull(reporte.getEstudianteId());
        assertNotNull(reporte.getObservaciones());
        assertNotNull(reporte.getFechaReporte());
    }
}