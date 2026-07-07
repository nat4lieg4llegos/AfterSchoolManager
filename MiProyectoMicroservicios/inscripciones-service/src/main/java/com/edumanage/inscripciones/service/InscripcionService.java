package com.edumanage.inscripciones.service;

import com.edumanage.inscripciones.client.TallerClient;
import com.edumanage.inscripciones.model.HistorialEstado;
import com.edumanage.inscripciones.model.Inscripcion;
import com.edumanage.inscripciones.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final TallerClient tallerClient;

    // Transiciones de estado válidas: estado actual -> estados a los que puede pasar
    private static final Map<String, Set<String>> TRANSICIONES_VALIDAS = Map.of(
            "PENDIENTE", Set.of("CONFIRMADA", "CANCELADA"),
            "CONFIRMADA", Set.of("SUSPENDIDA", "FINALIZADA", "CANCELADA"),
            "SUSPENDIDA", Set.of("CONFIRMADA", "CANCELADA"),
            "CANCELADA", Set.of(),
            "FINALIZADA", Set.of()
    );

    public InscripcionService(InscripcionRepository inscripcionRepository, TallerClient tallerClient) {
        this.inscripcionRepository = inscripcionRepository;
        this.tallerClient = tallerClient;
    }

    public List<Inscripcion> listarTodos() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> buscarPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public Inscripcion guardar(Inscripcion inscripcion) {
        // Validacion entre microservicios: el taller debe existir en talleres-service
        if (!tallerClient.existeTaller(inscripcion.getTallerId())) {
            throw new IllegalArgumentException(
                    "No se puede inscribir: el taller con id " + inscripcion.getTallerId() + " no existe"
            );
        }

        HistorialEstado primerEstado = new HistorialEstado();
        primerEstado.setEstadoAnterior(null);
        primerEstado.setEstadoNuevo(inscripcion.getEstado());
        primerEstado.setFechaCambio(LocalDateTime.now());
        primerEstado.setObservacion("Inscripción creada");

        inscripcion.agregarHistorial(primerEstado);

        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion actualizar(Long id, Inscripcion inscripcionActualizada) {
        Inscripcion existente = inscripcionRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("No existe una inscripción con id " + id));

        String estadoActual = existente.getEstado();
        String estadoNuevo = inscripcionActualizada.getEstado();

        if (estadoNuevo != null && !estadoNuevo.equals(estadoActual)) {
            validarTransicion(estadoActual, estadoNuevo);

            HistorialEstado cambio = new HistorialEstado();
            cambio.setEstadoAnterior(estadoActual);
            cambio.setEstadoNuevo(estadoNuevo);
            cambio.setFechaCambio(LocalDateTime.now());
            cambio.setObservacion("Cambio de estado");

            existente.agregarHistorial(cambio);
            existente.setEstado(estadoNuevo);
        }

        existente.setEstudianteId(inscripcionActualizada.getEstudianteId());
        existente.setTallerId(inscripcionActualizada.getTallerId());
        existente.setFechaInscripcion(inscripcionActualizada.getFechaInscripcion());

        return inscripcionRepository.save(existente);
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }

    private void validarTransicion(String estadoActual, String estadoNuevo) {
        Set<String> permitidos = TRANSICIONES_VALIDAS.get(estadoActual);

        if (permitidos == null) {
            throw new IllegalArgumentException("Estado actual desconocido: " + estadoActual);
        }
        if (!permitidos.contains(estadoNuevo)) {
            throw new IllegalArgumentException(
                    "No se puede cambiar de '" + estadoActual + "' a '" + estadoNuevo + "'"
            );
        }
    }
}