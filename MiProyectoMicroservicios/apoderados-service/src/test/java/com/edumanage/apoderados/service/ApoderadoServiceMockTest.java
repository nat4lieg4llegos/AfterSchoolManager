package com.edumanage.apoderados.service;

import com.edumanage.apoderados.model.Apoderado;
import com.edumanage.apoderados.model.PersonaAutorizada;
import com.edumanage.apoderados.repository.ApoderadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApoderadoServiceMockTest {

    @Mock
    private ApoderadoRepository apoderadoRepository;

    @InjectMocks
    private ApoderadoService apoderadoService;

    @Test
    void noDebePermitirCrearApoderadoConRutYaExistente() {
        Apoderado apoderado = new Apoderado();
        apoderado.setRut("15111111-1");
        apoderado.setNombreCompleto("Carla Perez");

        when(apoderadoRepository.existsByRut("15111111-1")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> apoderadoService.crear(apoderado)
        );
        assertTrue(ex.getMessage().contains("15111111-1"));

        verify(apoderadoRepository, never()).save(any());
    }

    @Test
    void noDebePermitirAutorizarDosVecesAlaMismaPersona() {
        Apoderado apoderado = new Apoderado();
        apoderado.setRut("16222222-2");
        apoderado.setNombreCompleto("Luis Soto");

        when(apoderadoRepository.existsByRut("16222222-2")).thenReturn(false);

        PersonaAutorizada p1 = new PersonaAutorizada();
        p1.setRut("17333333-3");
        p1.setNombreCompleto("Tio Autorizado");

        PersonaAutorizada p2 = new PersonaAutorizada();
        p2.setRut("17333333-3"); // mismo rut repetido
        p2.setNombreCompleto("Tio Autorizado Duplicado");

        List<PersonaAutorizada> personas = new ArrayList<>();
        personas.add(p1);
        personas.add(p2);
        apoderado.setPersonasAutorizadas(personas);

        assertThrows(
                IllegalArgumentException.class,
                () -> apoderadoService.crear(apoderado)
        );

        verify(apoderadoRepository, never()).save(any());
    }

    @Test
    void debeCrearApoderadoConDatosValidos() {
        Apoderado apoderado = new Apoderado();
        apoderado.setRut("18444444-4");
        apoderado.setNombreCompleto("Marcela Rojas");

        when(apoderadoRepository.existsByRut("18444444-4")).thenReturn(false);
        when(apoderadoRepository.save(any(Apoderado.class))).thenReturn(apoderado);

        Apoderado resultado = apoderadoService.crear(apoderado);

        assertNotNull(resultado);
        verify(apoderadoRepository, times(1)).save(apoderado);
    }
}
