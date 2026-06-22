package com.edumanage.asistencia.service;

import com.edumanage.asistencia.model.ObservacionAsistencia;
import com.edumanage.asistencia.model.RegistroAsistencia;
import com.edumanage.asistencia.repository.RegistroAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        validarFechas(registro);
        validarObservaciones(registro);
        // Conecta cada observacion con su registro (relacion bidireccional)
        if (registro.getObservaciones() != null) {
            registro.getObservaciones().forEach(o -> o.setRegistro(registro));
        }
        return registroRepository.save(registro);
    }

    public RegistroAsistencia actualizar(Long id, RegistroAsistencia datos) {
        RegistroAsistencia registro = registroRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro no encontrado con id " + id));
        registro.setEstudianteId(datos.getEstudianteId());
        registro.setTallerId(datos.getTallerId());
        registro.setFechaHoraIngreso(datos.getFechaHoraIngreso());
        registro.setFechaHoraSalida(datos.getFechaHoraSalida());
        registro.setQuienRetira(datos.getQuienRetira());
        return registroRepository.save(registro);
    }

    public void eliminar(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new NoSuchElementException("Registro no encontrado con id " + id);
        }
        registroRepository.deleteById(id);
    }

    // Regla de negocio: la hora de salida no puede ser anterior a la de ingreso
    private void validarFechas(RegistroAsistencia registro) {
        if (registro.getFechaHoraSalida() != null
                && registro.getFechaHoraIngreso() != null
                && registro.getFechaHoraSalida().isBefore(registro.getFechaHoraIngreso())) {
            throw new IllegalArgumentException(
                    "La hora de salida no puede ser anterior a la hora de ingreso"
            );
        }
    }

    // Regla de negocio: no se permiten dos observaciones del mismo tipo
    private void validarObservaciones(RegistroAsistencia registro) {
        if (registro.getObservaciones() == null || registro.getObservaciones().isEmpty()) {
            return;
        }
        for (int i = 0; i < registro.getObservaciones().size(); i++) {
            for (int j = i + 1; j < registro.getObservaciones().size(); j++) {
                ObservacionAsistencia a = registro.getObservaciones().get(i);
                ObservacionAsistencia b = registro.getObservaciones().get(j);
                if (a.getTipo() != null && a.getTipo().equalsIgnoreCase(b.getTipo())) {
                    throw new IllegalArgumentException(
                            "El registro no puede tener dos observaciones del mismo tipo (" + a.getTipo() + ")"
                    );
                }
            }
        }
    }
}