package com.edumanage.asistencia.service;

import com.edumanage.asistencia.model.ObservacionAsistencia;
import com.edumanage.asistencia.model.RegistroAsistencia;
import com.edumanage.asistencia.repository.RegistroAsistenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroAsistenciaServiceMockTest {

    @Mock
    private RegistroAsistenciaRepository registroRepository;

    @InjectMocks
    private RegistroAsistenciaService registroAsistenciaService;

    @Test
    void noDebePermitirSalidaAntesQueElIngreso() {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEstudianteId(1L);
        registro.setTallerId(1L);
        registro.setFechaHoraIngreso(LocalDateTime.of(2026, 7, 10, 17, 0));
        registro.setFechaHoraSalida(LocalDateTime.of(2026, 7, 10, 16, 0)); // antes del ingreso

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> registroAsistenciaService.crear(registro)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("salida"));

        verify(registroRepository, never()).save(any());
    }

    @Test
    void noDebePermitirObservacionesDelMismoTipoRepetidas() {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEstudianteId(1L);
        registro.setTallerId(1L);
        registro.setFechaHoraIngreso(LocalDateTime.of(2026, 7, 10, 16, 0));
        registro.setFechaHoraSalida(LocalDateTime.of(2026, 7, 10, 17, 30));

        ObservacionAsistencia o1 = new ObservacionAsistencia();
        o1.setTipo("ATRASO");
        ObservacionAsistencia o2 = new ObservacionAsistencia();
        o2.setTipo("ATRASO"); // repetido

        List<ObservacionAsistencia> observaciones = new ArrayList<>();
        observaciones.add(o1);
        observaciones.add(o2);
        registro.setObservaciones(observaciones);

        assertThrows(
                IllegalArgumentException.class,
                () -> registroAsistenciaService.crear(registro)
        );

        verify(registroRepository, never()).save(any());
    }

    @Test
    void debeCrearRegistroConFechasYObservacionesValidas() {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEstudianteId(1L);
        registro.setTallerId(1L);
        registro.setFechaHoraIngreso(LocalDateTime.of(2026, 7, 10, 16, 0));
        registro.setFechaHoraSalida(LocalDateTime.of(2026, 7, 10, 17, 30));

        when(registroRepository.save(any(RegistroAsistencia.class))).thenReturn(registro);

        RegistroAsistencia resultado = registroAsistenciaService.crear(registro);

        assertNotNull(resultado);
        verify(registroRepository, times(1)).save(registro);
    }
}
