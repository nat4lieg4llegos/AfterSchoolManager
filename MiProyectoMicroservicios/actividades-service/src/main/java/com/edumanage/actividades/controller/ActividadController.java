package com.edumanage.actividades.controller;

import com.edumanage.actividades.model.Actividad;
import com.edumanage.actividades.service.ActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@Tag(name = "Actividades", description = "Gestión de actividades del After School")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    @Operation(summary = "Obtener todas las actividades")
    public List<Actividad> obtenerTodas() {
        return actividadService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener actividad por ID")
    public ResponseEntity<Actividad> obtenerPorId(@PathVariable Long id) {
        return actividadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva actividad")
    public ResponseEntity<Actividad> crear(@Valid @RequestBody Actividad actividad) {
        return ResponseEntity.ok(actividadService.crear(actividad));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar actividad")
    public ResponseEntity<Actividad> actualizar(@PathVariable Long id, @Valid @RequestBody Actividad actividad) {
        return ResponseEntity.ok(actividadService.actualizar(id, actividad));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar actividad")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}