package com.afterschool.estudiante.controller;

import com.afterschool.estudiante.dto.ContactoEmergenciaDTO;
import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.dto.EstudianteResponseDTO;
import com.afterschool.estudiante.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@Tag(name = "Estudiantes", description = "Operaciones CRUD para gestión de estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los estudiantes")
    public List<EstudianteResponseDTO> listarTodos() {
        return estudianteService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un estudiante por ID")
    public EstudianteResponseDTO obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo estudiante")
    public ResponseEntity<EstudianteResponseDTO> crear(@Valid @RequestBody EstudianteRequestDTO dto) {
        return new ResponseEntity<>(estudianteService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un estudiante existente")
    public EstudianteResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteRequestDTO dto) {
        return estudianteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un estudiante por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/contactos")
    @Operation(summary = "Agregar un contacto de emergencia a un estudiante existente")
    public ResponseEntity<EstudianteResponseDTO> agregarContacto(
            @PathVariable Long id,
            @Valid @RequestBody ContactoEmergenciaDTO contactoDTO) {

        EstudianteResponseDTO estudianteActualizado = estudianteService.agregarContactoAEstudiante(id, contactoDTO);
        return new ResponseEntity<>(estudianteActualizado, HttpStatus.CREATED);
    }
}