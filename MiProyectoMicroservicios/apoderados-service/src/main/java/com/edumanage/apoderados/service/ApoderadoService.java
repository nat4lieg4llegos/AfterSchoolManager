package com.edumanage.apoderados.service;

import com.edumanage.apoderados.model.Apoderado;
import com.edumanage.apoderados.model.PersonaAutorizada;
import com.edumanage.apoderados.repository.ApoderadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ApoderadoService {

    @Autowired
    private ApoderadoRepository apoderadoRepository;

    public List<Apoderado> obtenerTodos() {
        return apoderadoRepository.findAll();
    }

    public Optional<Apoderado> obtenerPorId(Long id) {
        return apoderadoRepository.findById(id);
    }

    public Apoderado crear(Apoderado apoderado) {
        // Regla de negocio: el RUT del apoderado no puede estar repetido
        if (apoderadoRepository.existsByRut(apoderado.getRut())) {
            throw new IllegalArgumentException("Ya existe un apoderado con el RUT " + apoderado.getRut());
        }
        validarPersonasAutorizadas(apoderado);
        // Conecta cada persona autorizada con su apoderado (relacion bidireccional)
        if (apoderado.getPersonasAutorizadas() != null) {
            apoderado.getPersonasAutorizadas().forEach(p -> p.setApoderado(apoderado));
        }
        return apoderadoRepository.save(apoderado);
    }

    public Apoderado actualizar(Long id, Apoderado datos) {
        Apoderado apoderado = apoderadoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Apoderado no encontrado con id " + id));
        apoderado.setRut(datos.getRut());
        apoderado.setNombreCompleto(datos.getNombreCompleto());
        apoderado.setCorreo(datos.getCorreo());
        apoderado.setTelefono(datos.getTelefono());
        return apoderadoRepository.save(apoderado);
    }

    public void eliminar(Long id) {
        if (!apoderadoRepository.existsById(id)) {
            throw new NoSuchElementException("Apoderado no encontrado con id " + id);
        }
        apoderadoRepository.deleteById(id);
    }

    // Regla de negocio: no se puede autorizar dos veces a la misma persona (mismo RUT)
    private void validarPersonasAutorizadas(Apoderado apoderado) {
        if (apoderado.getPersonasAutorizadas() == null || apoderado.getPersonasAutorizadas().isEmpty()) {
            return;
        }
        for (int i = 0; i < apoderado.getPersonasAutorizadas().size(); i++) {
            for (int j = i + 1; j < apoderado.getPersonasAutorizadas().size(); j++) {
                PersonaAutorizada a = apoderado.getPersonasAutorizadas().get(i);
                PersonaAutorizada b = apoderado.getPersonasAutorizadas().get(j);
                if (a.getRut() != null && a.getRut().equalsIgnoreCase(b.getRut())) {
                    throw new IllegalArgumentException(
                            "No se puede autorizar dos veces a la misma persona (RUT " + a.getRut() + ")"
                    );
                }
            }
        }
    }
}