package com.edumanage.apoderados.controller;

import com.edumanage.apoderados.model.Apoderado;
import com.edumanage.apoderados.service.ApoderadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apoderados")
@Tag(name = "Apoderados", description = "Gestión de apoderados del After School")
public class ApoderadoController {

    @Autowired
    private ApoderadoService apoderadoService;

    @GetMapping
    @Operation(summary = "Obtener todos los apoderados")
    public List<Apoderado> obtenerTodos() {
        return apoderadoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener apoderado por ID")
    public ResponseEntity<Apoderado> obtenerPorId(@PathVariable Long id) {
        return apoderadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo apoderado")
    public ResponseEntity<Apoderado> crear(@Valid @RequestBody Apoderado apoderado) {
        return ResponseEntity.ok(apoderadoService.crear(apoderado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar apoderado")
    public ResponseEntity<Apoderado> actualizar(@PathVariable Long id, @Valid @RequestBody Apoderado apoderado) {
        return ResponseEntity.ok(apoderadoService.actualizar(id, apoderado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar apoderado")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        apoderadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}