package com.edumanage.notificaciones.service;

import com.edumanage.notificaciones.model.IntentoEnvio;
import com.edumanage.notificaciones.model.Notificacion;
import com.edumanage.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> obtenerPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    public Notificacion crear(Notificacion notificacion) {
        validarIntentos(notificacion);
        // Conecta cada intento con su notificacion (relacion bidireccional)
        if (notificacion.getIntentos() != null) {
            notificacion.getIntentos().forEach(i -> i.setNotificacion(notificacion));
        }
        return notificacionRepository.save(notificacion);
    }

    public Notificacion actualizar(Long id, Notificacion datos) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Notificacion no encontrada con id " + id));
        notificacion.setApoderadoId(datos.getApoderadoId());
        notificacion.setTipo(datos.getTipo());
        notificacion.setMensaje(datos.getMensaje());
        notificacion.setFechaEnvio(datos.getFechaEnvio());
        notificacion.setLeida(datos.getLeida());
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new NoSuchElementException("Notificacion no encontrada con id " + id);
        }
        notificacionRepository.deleteById(id);
    }

    // Regla de negocio: una notificacion no puede tener dos intentos por el mismo canal
    private void validarIntentos(Notificacion notificacion) {
        if (notificacion.getIntentos() == null || notificacion.getIntentos().isEmpty()) {
            return;
        }
        for (int i = 0; i < notificacion.getIntentos().size(); i++) {
            for (int j = i + 1; j < notificacion.getIntentos().size(); j++) {
                IntentoEnvio a = notificacion.getIntentos().get(i);
                IntentoEnvio b = notificacion.getIntentos().get(j);
                if (a.getCanal() != null && a.getCanal().equalsIgnoreCase(b.getCanal())) {
                    throw new IllegalArgumentException(
                            "La notificacion no puede tener dos intentos por el mismo canal (" + a.getCanal() + ")"
                    );
                }
            }
        }
    }
}