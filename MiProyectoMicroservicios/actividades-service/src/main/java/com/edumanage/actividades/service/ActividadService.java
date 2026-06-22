package com.edumanage.actividades.service;

import com.edumanage.actividades.model.Actividad;
import com.edumanage.actividades.model.MaterialRequerido;
import com.edumanage.actividades.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        // Regla de negocio: no se permiten dos materiales con el mismo nombre
        validarMateriales(actividad);
        // Conecta cada material con su actividad (relacion bidireccional)
        if (actividad.getMateriales() != null) {
            actividad.getMateriales().forEach(m -> m.setActividad(actividad));
        }
        return actividadRepository.save(actividad);
    }

    public Actividad actualizar(Long id, Actividad datos) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Actividad no encontrada con id " + id));
        actividad.setTallerId(datos.getTallerId());
        actividad.setTitulo(datos.getTitulo());
        actividad.setDescripcion(datos.getDescripcion());
        actividad.setFechaProgramada(datos.getFechaProgramada());
        return actividadRepository.save(actividad);
    }

    public void eliminar(Long id) {
        if (!actividadRepository.existsById(id)) {
            throw new NoSuchElementException("Actividad no encontrada con id " + id);
        }
        actividadRepository.deleteById(id);
    }

    // Regla de negocio: no se puede repetir el mismo material en una actividad
    private void validarMateriales(Actividad actividad) {
        if (actividad.getMateriales() == null || actividad.getMateriales().isEmpty()) {
            return;
        }
        for (int i = 0; i < actividad.getMateriales().size(); i++) {
            for (int j = i + 1; j < actividad.getMateriales().size(); j++) {
                MaterialRequerido a = actividad.getMateriales().get(i);
                MaterialRequerido b = actividad.getMateriales().get(j);
                if (a.getNombreMaterial() != null
                        && a.getNombreMaterial().equalsIgnoreCase(b.getNombreMaterial())) {
                    throw new IllegalArgumentException(
                            "La actividad no puede tener el material '" + a.getNombreMaterial() + "' repetido"
                    );
                }
            }
        }
    }
}