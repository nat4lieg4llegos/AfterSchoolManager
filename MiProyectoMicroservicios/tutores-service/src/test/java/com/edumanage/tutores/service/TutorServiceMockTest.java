package com.edumanage.tutores.service;

import com.edumanage.tutores.model.Especialidad;
import com.edumanage.tutores.model.Tutor;
import com.edumanage.tutores.repository.TutorRepository;
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
class TutorServiceMockTest {

    @Mock
    private TutorRepository tutorRepository;

    @InjectMocks
    private TutorService tutorService;

    @Test
    void noDebePermitirCrearTutorConRutYaExistente() {
        Tutor tutor = new Tutor();
        tutor.setRut("11111111-1");
        tutor.setNombreCompleto("Pamela Cocq");

        when(tutorRepository.existsByRut("11111111-1")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> tutorService.crear(tutor)
        );
        assertTrue(ex.getMessage().contains("11111111-1"));

        verify(tutorRepository, never()).save(any());
    }

    @Test
    void noDebePermitirEspecialidadesRepetidas() {
        Tutor tutor = new Tutor();
        tutor.setRut("22222222-2");
        tutor.setNombreCompleto("Jorge Araya");

        when(tutorRepository.existsByRut("22222222-2")).thenReturn(false);

        Especialidad e1 = new Especialidad();
        e1.setArea("Robotica");
        Especialidad e2 = new Especialidad();
        e2.setArea("ROBOTICA"); // misma area, distinto formato

        List<Especialidad> especialidades = new ArrayList<>();
        especialidades.add(e1);
        especialidades.add(e2);
        tutor.setEspecialidades(especialidades);

        assertThrows(
                IllegalArgumentException.class,
                () -> tutorService.crear(tutor)
        );

        verify(tutorRepository, never()).save(any());
    }

    @Test
    void debeCrearTutorSiRutYEspecialidadesSonValidos() {
        Tutor tutor = new Tutor();
        tutor.setRut("33333333-3");
        tutor.setNombreCompleto("Natalie Gallegos");

        when(tutorRepository.existsByRut("33333333-3")).thenReturn(false);
        when(tutorRepository.save(any(Tutor.class))).thenReturn(tutor);

        Tutor resultado = tutorService.crear(tutor);

        assertNotNull(resultado);
        verify(tutorRepository, times(1)).save(tutor);
    }
}
