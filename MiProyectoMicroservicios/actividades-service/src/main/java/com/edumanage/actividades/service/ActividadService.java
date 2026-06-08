package com.edumanage.actividades.service;

import com.edumanage.actividades.model.Actividad;
import com.edumanage.actividades.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Actividad> obtenerTodas() {
        return actividadRepository.findAll();
    }

    public Optional<Actividad> obtenerPorId(Long id) {
        return actividadRepository.findById(id);
    }

    public Actividad crear(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    public Actividad actualizar(Long id, Actividad datos) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividad.setTallerId(datos.getTallerId());
        actividad.setTitulo(datos.getTitulo());
        actividad.setDescripcion(datos.getDescripcion());
        actividad.setFechaProgramada(datos.getFechaProgramada());
        return actividadRepository.save(actividad);
    }

    public void eliminar(Long id) {
        actividadRepository.deleteById(id);
    }
}