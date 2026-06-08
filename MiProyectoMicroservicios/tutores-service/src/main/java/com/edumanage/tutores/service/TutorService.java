package com.edumanage.tutores.service;

import com.edumanage.tutores.model.Tutor;
import com.edumanage.tutores.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return tutorRepository.save(tutor);
    }

    public Tutor actualizar(Long id, Tutor datos) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
        tutor.setRut(datos.getRut());
        tutor.setNombreCompleto(datos.getNombreCompleto());
        tutor.setFechaContratacion(datos.getFechaContratacion());
        return tutorRepository.save(tutor);
    }

    public void eliminar(Long id) {
        tutorRepository.deleteById(id);
    }
}