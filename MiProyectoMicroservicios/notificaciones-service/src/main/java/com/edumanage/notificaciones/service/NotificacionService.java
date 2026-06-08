package com.edumanage.notificaciones.service;

import com.edumanage.notificaciones.model.Notificacion;
import com.edumanage.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return notificacionRepository.save(notificacion);
    }

    public Notificacion actualizar(Long id, Notificacion datos) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada"));
        notificacion.setApoderadoId(datos.getApoderadoId());
        notificacion.setTipo(datos.getTipo());
        notificacion.setMensaje(datos.getMensaje());
        notificacion.setFechaEnvio(datos.getFechaEnvio());
        notificacion.setLeida(datos.getLeida());
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(Long id) {
        notificacionRepository.deleteById(id);
    }
}