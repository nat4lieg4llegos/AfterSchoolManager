package com.afterschool.estudiante.controller;

import com.afterschool.estudiante.dto.ContactoEmergenciaDTO;
import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.dto.EstudianteResponseDTO;
import com.afterschool.estudiante.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteResponseDTO> listarTodos() {
        return estudianteService.listarTodos();
    }

    @GetMapping("/{id}")
    public EstudianteResponseDTO obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> crear(@Valid @RequestBody EstudianteRequestDTO dto) {
        return new ResponseEntity<>(estudianteService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public EstudianteResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteRequestDTO dto) {
        return estudianteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/contactos")
    public ResponseEntity<EstudianteResponseDTO> agregarContacto(
            @PathVariable Long id,
            @Valid @RequestBody ContactoEmergenciaDTO contactoDTO) {

        EstudianteResponseDTO estudianteActualizado = estudianteService.agregarContactoAEstudiante(id, contactoDTO);
        return new ResponseEntity<>(estudianteActualizado, HttpStatus.CREATED);
    }
}