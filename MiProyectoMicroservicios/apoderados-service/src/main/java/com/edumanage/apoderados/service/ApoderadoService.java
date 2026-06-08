package com.edumanage.apoderados.service;

import com.edumanage.apoderados.model.Apoderado;
import com.edumanage.apoderados.repository.ApoderadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return apoderadoRepository.save(apoderado);
    }

    public Apoderado actualizar(Long id, Apoderado datos) {
        Apoderado apoderado = apoderadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));
        apoderado.setRut(datos.getRut());
        apoderado.setNombreCompleto(datos.getNombreCompleto());
        apoderado.setCorreo(datos.getCorreo());
        apoderado.setTelefono(datos.getTelefono());
        return apoderadoRepository.save(apoderado);
    }

    public void eliminar(Long id) {
        apoderadoRepository.deleteById(id);
    }
}