package com.edumanage.cursos.service;

import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.repository.TallerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return tallerRepository.save(taller);
    }

    public Taller actualizar(Long id, Taller taller) {
        taller.setId(id);
        return tallerRepository.save(taller);
    }

    public void eliminar(Long id) {
        tallerRepository.deleteById(id);
    }
}