package com.afterschool.estudiante.service;

import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.dto.EstudianteResponseDTO;
import com.afterschool.estudiante.model.Estudiante;
import com.afterschool.estudiante.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public List<EstudianteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EstudianteResponseDTO obtenerPorId(Long id) {
        Estudiante e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
        return toDTO(e);
    }

    public EstudianteResponseDTO crear(EstudianteRequestDTO dto) {
        Estudiante e = new Estudiante();
        e.setRut(dto.getRut());
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setApoderadoId(dto.getApoderadoId());
        return toDTO(repository.save(e));
    }

    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {
        Estudiante e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
        e.setRut(dto.getRut());
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setApoderadoId(dto.getApoderadoId());
        return toDTO(repository.save(e));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private EstudianteResponseDTO toDTO(Estudiante e) {
        EstudianteResponseDTO dto = new EstudianteResponseDTO();
        dto.setId(e.getId());
        dto.setRut(e.getRut());
        dto.setNombre(e.getNombre());
        dto.setApellido(e.getApellido());
        dto.setFechaNacimiento(e.getFechaNacimiento());
        dto.setApoderadoId(e.getApoderadoId());
        return dto;
    }
}