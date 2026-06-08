package com.edumanage.inscripciones.controller;

import com.edumanage.inscripciones.model.Inscripcion;
import com.edumanage.inscripciones.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "Operaciones CRUD para gestión de inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las inscripciones")
    public ResponseEntity<List<Inscripcion>> listar() {
        return ResponseEntity.ok(inscripcionService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una inscripción por ID")
    public ResponseEntity<Inscripcion> obtener(@PathVariable Long id) {
        return inscripcionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva inscripción")
    public ResponseEntity<Inscripcion> crear(@Valid @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(inscripcionService.guardar(inscripcion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una inscripción existente")
    public ResponseEntity<Inscripcion> actualizar(@PathVariable Long id, @Valid @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(inscripcionService.actualizar(id, inscripcion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una inscripción por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}