package com.edumanage.cursos.service;

import com.edumanage.cursos.model.Horario;
import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.repository.TallerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TallerService {

    private final TallerRepository tallerRepository;

    public TallerService(TallerRepository tallerRepository) {
        this.tallerRepository = tallerRepository;
    }

    public List<Taller> listarTodos() {
        return tallerRepository.findAll();
    }

    public Optional<Taller> buscarPorId(Long id) {
        return tallerRepository.findById(id);
    }

    public Taller guardar(Taller taller) {
        validarHorarios(taller);
        // Conecta cada horario con su taller (relacion bidireccional)
        if (taller.getHorarios() != null) {
            taller.getHorarios().forEach(h -> h.setTaller(taller));
        }
        return tallerRepository.save(taller);
    }

    public Taller actualizar(Long id, Taller tallerActualizado) {
        Taller existente = tallerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe un taller con id " + id));

        existente.setNombre(tallerActualizado.getNombre());
        existente.setSede(tallerActualizado.getSede());
        existente.setCupoMaximo(tallerActualizado.getCupoMaximo());

        return tallerRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!tallerRepository.existsById(id)) {
            throw new NoSuchElementException("No existe un taller con id " + id);
        }
        tallerRepository.deleteById(id);
    }

    // Regla de negocio: un taller no puede tener dos horarios el mismo dia a la misma hora de inicio
    private void validarHorarios(Taller taller) {
        if (taller.getHorarios() == null || taller.getHorarios().isEmpty()) {
            return;
        }
        for (int i = 0; i < taller.getHorarios().size(); i++) {
            for (int j = i + 1; j < taller.getHorarios().size(); j++) {
                Horario a = taller.getHorarios().get(i);
                Horario b = taller.getHorarios().get(j);
                if (a.getDiaSemana().equalsIgnoreCase(b.getDiaSemana())
                        && a.getHoraInicio().equals(b.getHoraInicio())) {
                    throw new IllegalArgumentException(
                            "No se pueden registrar dos horarios el mismo dia (" + a.getDiaSemana()
                                    + ") a la misma hora de inicio (" + a.getHoraInicio() + ")"
                    );
                }
            }
        }
    }
}