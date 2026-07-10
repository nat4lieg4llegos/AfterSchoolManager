package com.edumanage.reportes.service;

import com.edumanage.reportes.model.DetalleReporte;
import com.edumanage.reportes.model.ReporteProgreso;
import com.edumanage.reportes.repository.ReporteProgresoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteProgresoServiceMockTest {

    @Mock
    private ReporteProgresoRepository reporteRepository;

    @InjectMocks
    private ReporteProgresoService reporteProgresoService;

    private DetalleReporte detalle(String indicador, int puntaje) {
        DetalleReporte d = new DetalleReporte();
        d.setIndicador(indicador);
        d.setPuntaje(puntaje);
        return d;
    }

    @Test
    void noDebePermitirIndicadoresRepetidos() {
        ReporteProgreso reporte = new ReporteProgreso();
        reporte.setEstudianteId(1L);
        reporte.setTallerId(1L);

        List<DetalleReporte> detalles = new ArrayList<>();
        detalles.add(detalle("Puntualidad", 8));
        detalles.add(detalle("Puntualidad", 6)); // mismo indicador repetido
        reporte.setDetalles(detalles);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reporteProgresoService.crear(reporte)
        );
        assertTrue(ex.getMessage().contains("Puntualidad"));

        verify(reporteRepository, never()).save(any());
    }

    @Test
    void debePermitirIndicadoresDistintos() {
        ReporteProgreso reporte = new ReporteProgreso();
        reporte.setEstudianteId(1L);
        reporte.setTallerId(1L);

        List<DetalleReporte> detalles = new ArrayList<>();
        detalles.add(detalle("Puntualidad", 8));
        detalles.add(detalle("Participacion", 9));
        reporte.setDetalles(detalles);

        when(reporteRepository.save(any(ReporteProgreso.class))).thenReturn(reporte);

        ReporteProgreso resultado = reporteProgresoService.crear(reporte);

        assertNotNull(resultado);
        verify(reporteRepository, times(1)).save(reporte);
    }

    @Test
    void debeLanzarExcepcionAlEliminarReporteInexistente() {
        when(reporteRepository.existsById(20L)).thenReturn(false);

        assertThrows(
                NoSuchElementException.class,
                () -> reporteProgresoService.eliminar(20L)
        );
    }
}
