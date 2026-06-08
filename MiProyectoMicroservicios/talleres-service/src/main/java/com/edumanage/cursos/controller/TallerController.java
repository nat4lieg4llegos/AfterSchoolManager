package com.edumanage.cursos.controller;

import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.service.TallerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talleres")
@Tag(name = "Talleres", description = "Operaciones CRUD para gestión de talleres")
public class TallerController {

    private final TallerService tallerService;

    public TallerController(TallerService tallerService) {
        this.tallerService = tallerService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los talleres")
    public ResponseEntity<List<Taller>> listar() {
        return ResponseEntity.ok(tallerService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un taller por ID")
    public ResponseEntity<Taller> obtener(@PathVariable Long id) {
        return tallerService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo taller")
    public ResponseEntity<Taller> crear(@Valid @RequestBody Taller taller) {
        return ResponseEntity.ok(tallerService.guardar(taller));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un taller existente")
    public ResponseEntity<Taller> actualizar(@PathVariable Long id, @Valid @RequestBody Taller taller) {
        return ResponseEntity.ok(tallerService.actualizar(id, taller));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un taller por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}