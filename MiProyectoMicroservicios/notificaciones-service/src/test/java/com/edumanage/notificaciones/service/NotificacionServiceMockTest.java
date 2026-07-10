package com.edumanage.notificaciones.service;

import com.edumanage.notificaciones.model.IntentoEnvio;
import com.edumanage.notificaciones.model.Notificacion;
import com.edumanage.notificaciones.repository.NotificacionRepository;
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
class NotificacionServiceMockTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private IntentoEnvio intento(String canal) {
        IntentoEnvio i = new IntentoEnvio();
        i.setCanal(canal);
        i.setEstado("PENDIENTE");
        return i;
    }

    @Test
    void noDebePermitirDosIntentosPorElMismoCanal() {
        // GIVEN: una notificacion con dos intentos, ambos por EMAIL
        Notificacion notificacion = new Notificacion();
        notificacion.setApoderadoId(1L);
        notificacion.setTipo("RECORDATORIO");
        notificacion.setMensaje("Pago pendiente");

        List<IntentoEnvio> intentos = new ArrayList<>();
        intentos.add(intento("EMAIL"));
        intentos.add(intento("EMAIL")); // canal repetido
        notificacion.setIntentos(intentos);

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> notificacionService.crear(notificacion)
        );
        assertTrue(ex.getMessage().contains("EMAIL"));

        verify(notificacionRepository, never()).save(any());
    }

    @Test
    void debePermitirIntentosPorCanalesDistintos() {
        Notificacion notificacion = new Notificacion();
        notificacion.setApoderadoId(1L);
        notificacion.setTipo("RECORDATORIO");
        notificacion.setMensaje("Pago pendiente");

        List<IntentoEnvio> intentos = new ArrayList<>();
        intentos.add(intento("EMAIL"));
        intentos.add(intento("SMS"));
        notificacion.setIntentos(intentos);

        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        Notificacion resultado = notificacionService.crear(notificacion);

        assertNotNull(resultado);
        verify(notificacionRepository, times(1)).save(notificacion);
    }

    @Test
    void debeLanzarExcepcionAlEliminarNotificacionInexistente() {
        when(notificacionRepository.existsById(77L)).thenReturn(false);

        assertThrows(
                NoSuchElementException.class,
                () -> notificacionService.eliminar(77L)
        );

        verify(notificacionRepository, never()).deleteById(any());
    }
}
