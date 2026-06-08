package com.edumanage.asistencia.service;

import com.edumanage.asistencia.model.RegistroAsistencia;
import com.edumanage.asistencia.repository.RegistroAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroAsistenciaService {

    @Autowired
    private RegistroAsistenciaRepository registroRepository;

    public List<RegistroAsistencia> obtenerTodos() {
        return registroRepository.findAll();
    }

    public Optional<RegistroAsistencia> obtenerPorId(Long id) {
        return registroRepository.findById(id);
    }

    public RegistroAsistencia crear(RegistroAsistencia registro) {
        return registroRepository.save(registro);
    }

    public RegistroAsistencia actualizar(Long id, RegistroAsistencia datos) {
        RegistroAsistencia registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        registro.setEstudianteId(datos.getEstudianteId());
        registro.setTallerId(datos.getTallerId());
        registro.setFechaHoraIngreso(datos.getFechaHoraIngreso());
        registro.setFechaHoraSalida(datos.getFechaHoraSalida());
        registro.setQuienRetira(datos.getQuienRetira());
        return registroRepository.save(registro);
    }

    public void eliminar(Long id) {
        registroRepository.deleteById(id);
    }
}