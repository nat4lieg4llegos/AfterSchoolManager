package com.afterschool.estudiante.service;

import com.afterschool.estudiante.dto.ContactoEmergenciaDTO;
import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.dto.EstudianteResponseDTO;
import com.afterschool.estudiante.exception.ContactoDuplicadoException;
import com.afterschool.estudiante.exception.EstudianteNoEncontradoException;
import com.afterschool.estudiante.model.ContactoEmergencia;
import com.afterschool.estudiante.model.Estudiante;
import com.afterschool.estudiante.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
                .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con id: " + id));
        return toDTO(e);
    }

    public EstudianteResponseDTO crear(EstudianteRequestDTO dto) {
        Estudiante e = new Estudiante();
        e.setRut(dto.getRut());
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setApoderadoId(dto.getApoderadoId());

        validarContactosDuplicados(dto.getContactos());

        if (dto.getContactos() != null) {
            for (ContactoEmergenciaDTO cDto : dto.getContactos()) {
                ContactoEmergencia c = new ContactoEmergencia();
                c.setNombre(cDto.getNombre());
                c.setParentesco(cDto.getParentesco());
                c.setTelefono(cDto.getTelefono());
                e.agregarContacto(c);
            }
        }

        return toDTO(repository.save(e));
    }

    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {
        Estudiante e = repository.findById(id)
                .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con id: " + id));
        e.setRut(dto.getRut());
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setApoderadoId(dto.getApoderadoId());
        return toDTO(repository.save(e));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EstudianteNoEncontradoException("Estudiante no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    // Agrega un contacto de emergencia a un estudiante existente
    public EstudianteResponseDTO agregarContactoAEstudiante(Long id, ContactoEmergenciaDTO contactoDTO) {
        Estudiante e = repository.findById(id)
                .orElseThrow(() -> new EstudianteNoEncontradoException("Estudiante no encontrado con id: " + id));

        boolean existe = e.getContactos().stream()
                .anyMatch(c -> c.getTelefono().equals(contactoDTO.getTelefono()));
        if (existe) {
            throw new ContactoDuplicadoException(
                    "El estudiante ya tiene un contacto con el telefono (" + contactoDTO.getTelefono() + ")"
            );
        }

        ContactoEmergencia c = new ContactoEmergencia();
        c.setNombre(contactoDTO.getNombre());
        c.setParentesco(contactoDTO.getParentesco());
        c.setTelefono(contactoDTO.getTelefono());
        e.agregarContacto(c);

        return toDTO(repository.save(e));
    }

    // Valida que no haya telefonos repetidos en la lista de contactos
    private void validarContactosDuplicados(List<ContactoEmergenciaDTO> contactos) {
        if (contactos == null || contactos.isEmpty()) {
            return;
        }
        Set<String> telefonos = new HashSet<>();
        for (ContactoEmergenciaDTO c : contactos) {
            if (!telefonos.add(c.getTelefono())) {
                throw new ContactoDuplicadoException(
                        "No se pueden registrar dos contactos con el mismo telefono (" + c.getTelefono() + ")"
                );
            }
        }
    }

    private EstudianteResponseDTO toDTO(Estudiante e) {
        EstudianteResponseDTO dto = new EstudianteResponseDTO();
        dto.setId(e.getId());
        dto.setRut(e.getRut());
        dto.setNombre(e.getNombre());
        dto.setApellido(e.getApellido());
        dto.setFechaNacimiento(e.getFechaNacimiento());
        dto.setApoderadoId(e.getApoderadoId());

        List<ContactoEmergenciaDTO> contactosDTO = new ArrayList<>();
        if (e.getContactos() != null) {
            for (ContactoEmergencia c : e.getContactos()) {
                ContactoEmergenciaDTO cDto = new ContactoEmergenciaDTO();
                cDto.setId(c.getId());
                cDto.setNombre(c.getNombre());
                cDto.setParentesco(c.getParentesco());
                cDto.setTelefono(c.getTelefono());
                contactosDTO.add(cDto);
            }
        }
        dto.setContactos(contactosDTO);

        return dto;
    }
}