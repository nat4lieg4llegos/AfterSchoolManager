package com.edumanage.tutores.service;

import com.edumanage.tutores.model.Especialidad;
import com.edumanage.tutores.model.Tutor;
import com.edumanage.tutores.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public List<Tutor> obtenerTodos() {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> obtenerPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor crear(Tutor tutor) {
        // Regla de negocio: el RUT no puede estar repetido
        if (tutorRepository.existsByRut(tutor.getRut())) {
            throw new IllegalArgumentException("Ya existe un tutor con el RUT " + tutor.getRut());
        }
        validarEspecialidades(tutor);
        // Conecta cada especialidad con su tutor (relacion bidireccional)
        if (tutor.getEspecialidades() != null) {
            tutor.getEspecialidades().forEach(e -> e.setTutor(tutor));
        }
        return tutorRepository.save(tutor);
    }

    public Tutor actualizar(Long id, Tutor datos) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tutor no encontrado con id " + id));
        tutor.setRut(datos.getRut());
        tutor.setNombreCompleto(datos.getNombreCompleto());
        tutor.setFechaContratacion(datos.getFechaContratacion());
        return tutorRepository.save(tutor);
    }

    public void eliminar(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new NoSuchElementException("Tutor no encontrado con id " + id);
        }
        tutorRepository.deleteById(id);
    }

    // Regla de negocio: un tutor no puede tener la misma area de especialidad repetida
    private void validarEspecialidades(Tutor tutor) {
        if (tutor.getEspecialidades() == null || tutor.getEspecialidades().isEmpty()) {
            return;
        }
        for (int i = 0; i < tutor.getEspecialidades().size(); i++) {
            for (int j = i + 1; j < tutor.getEspecialidades().size(); j++) {
                Especialidad a = tutor.getEspecialidades().get(i);
                Especialidad b = tutor.getEspecialidades().get(j);
                if (a.getArea() != null && a.getArea().equalsIgnoreCase(b.getArea())) {
                    throw new IllegalArgumentException(
                            "El tutor no puede tener la especialidad '" + a.getArea() + "' repetida"
                    );
                }
            }
        }
    }
}