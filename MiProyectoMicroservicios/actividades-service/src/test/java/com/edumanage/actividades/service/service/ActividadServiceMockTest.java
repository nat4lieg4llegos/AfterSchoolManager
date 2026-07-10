package com.edumanage.actividades.service;

import com.edumanage.actividades.model.Actividad;
import com.edumanage.actividades.model.MaterialRequerido;
import com.edumanage.actividades.repository.ActividadRepository;
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
class ActividadServiceMockTest {

    @Mock
    private ActividadRepository actividadRepository;

    @InjectMocks
    private ActividadService actividadService;

    private MaterialRequerido material(String nombre) {
        MaterialRequerido m = new MaterialRequerido();
        m.setNombreMaterial(nombre);
        m.setCantidadNecesaria(1);
        return m;
    }

    @Test
    void noDebePermitirMaterialesRepetidos() {
        Actividad actividad = new Actividad();
        actividad.setTallerId(1L);
        actividad.setTitulo("Armar circuito basico");

        List<MaterialRequerido> materiales = new ArrayList<>();
        materiales.add(material("Cables"));
        materiales.add(material("Cables")); // repetido

        actividad.setMateriales(materiales);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> actividadService.crear(actividad)
        );
        assertTrue(ex.getMessage().contains("Cables"));

        verify(actividadRepository, never()).save(any());
    }

    @Test
    void debePermitirMaterialesDistintos() {
        Actividad actividad = new Actividad();
        actividad.setTallerId(1L);
        actividad.setTitulo("Armar circuito basico");

        List<MaterialRequerido> materiales = new ArrayList<>();
        materiales.add(material("Cables"));
        materiales.add(material("Resistencias"));
        actividad.setMateriales(materiales);

        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividad);

        Actividad resultado = actividadService.crear(actividad);

        assertNotNull(resultado);
        verify(actividadRepository, times(1)).save(actividad);
    }

    @Test
    void debeLanzarExcepcionAlActualizarActividadInexistente() {
        when(actividadRepository.findById(10L)).thenReturn(java.util.Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> actividadService.actualizar(10L, new Actividad())
        );
    }
}
