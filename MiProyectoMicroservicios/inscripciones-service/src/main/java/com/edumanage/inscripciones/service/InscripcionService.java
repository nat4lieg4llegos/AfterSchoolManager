package com.edumanage.inscripciones.service;

import com.edumanage.inscripciones.model.Inscripcion;
import com.edumanage.inscripciones.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public List<Inscripcion> listarTodos() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> buscarPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public Inscripcion guardar(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion actualizar(Long id, Inscripcion inscripcion) {
        inscripcion.setId(id);
        return inscripcionRepository.save(inscripcion);
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }
}