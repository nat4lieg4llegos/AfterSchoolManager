package com.edumanage.inscripciones.service;

import com.edumanage.inscripciones.client.TallerClient;
import com.edumanage.inscripciones.model.Inscripcion;
import com.edumanage.inscripciones.repository.InscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith activa Mockito para esta clase de test (necesario para que @Mock funcione)
@ExtendWith(MockitoExtension.class)
class InscripcionServiceMockTest {

    // @Mock crea una version "falsa" del Repository y del TallerClient.
    // No tocan la base de datos real ni hacen llamadas HTTP reales a talleres-service.
    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private TallerClient tallerClient;

    // El service real, pero construido a mano inyectando los mocks de arriba
    // (en vez de @InjectMocks, se instancia directo para que quede mas claro)
    private InscripcionService inscripcionService;

    @BeforeEach
    void setUp() {
        inscripcionService = new InscripcionService(inscripcionRepository, tallerClient);
    }

    @Test
    void noDebePermitirInscripcionSiElTallerNoExiste() {
        // GIVEN: una inscripcion para un taller que NO existe
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(1L);
        inscripcion.setTallerId(999L);
        inscripcion.setEstado("PENDIENTE");

        // Le decimos al mock: "cuando te pregunten si el taller 999 existe, responde false"
        when(tallerClient.existeTaller(999L)).thenReturn(false);

        // WHEN + THEN: al intentar guardar, debe lanzar excepcion y el mensaje debe mencionar el id del taller
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> inscripcionService.guardar(inscripcion)
        );
        assertTrue(ex.getMessage().contains("999"));

        // Verificamos que, como el taller no existia, NUNCA se llego a guardar en la base de datos
        verify(inscripcionRepository, never()).save(any());
    }

    @Test
    void debeGuardarInscripcionSiElTallerExiste() {
        // GIVEN: una inscripcion para un taller que SI existe
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(1L);
        inscripcion.setTallerId(5L);
        inscripcion.setEstado("PENDIENTE");

        when(tallerClient.existeTaller(5L)).thenReturn(true);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcion);

        // WHEN
        Inscripcion resultado = inscripcionService.guardar(inscripcion);

        // THEN: se guardo correctamente y se le agrego el primer registro de historial
        assertNotNull(resultado);
        assertEquals(1, resultado.getHistorial().size());
        verify(inscripcionRepository, times(1)).save(inscripcion);
    }

    @Test
    void noDebePermitirTransicionDeCanceladaAConfirmada() {
        // GIVEN: una inscripcion ya existente y CANCELADA (estado final, sin salidas)
        Inscripcion existente = new Inscripcion();
        existente.setId(10L);
        existente.setEstado("CANCELADA");
        when(inscripcionRepository.findById(10L)).thenReturn(java.util.Optional.of(existente));

        Inscripcion cambio = new Inscripcion();
        cambio.setEstado("CONFIRMADA"); // transicion invalida

        // WHEN + THEN
        assertThrows(
                IllegalArgumentException.class,
                () -> inscripcionService.actualizar(10L, cambio)
        );
    }

    @Test
    void debeLanzarExcepcionSiLaInscripcionNoExiste() {
        when(inscripcionRepository.findById(123L)).thenReturn(java.util.Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> inscripcionService.actualizar(123L, new Inscripcion())
        );
    }
}
