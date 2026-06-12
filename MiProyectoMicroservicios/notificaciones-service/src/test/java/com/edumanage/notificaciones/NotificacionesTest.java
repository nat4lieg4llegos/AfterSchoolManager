package com.edumanage.notificaciones;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.edumanage.notificaciones.model.Notificacion;
import java.time.LocalDateTime;

class NotificacionesTest {

    Faker faker = new Faker();

    @Test
    void testCrearNotificacion() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setApoderadoId(faker.number().randomNumber());
        notificacion.setTipo("AVISO");
        notificacion.setMensaje(faker.lorem().sentence());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setLeida(false);

        // Act & Assert
        assertNotNull(notificacion);
        assertNotNull(notificacion.getTipo());
        assertNotNull(notificacion.getMensaje());
        assertNotNull(notificacion.getFechaEnvio());
        assertFalse(notificacion.getLeida());
    }
}