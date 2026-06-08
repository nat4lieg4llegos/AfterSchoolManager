package com.edumanage.notificaciones.controller;

import com.edumanage.notificaciones.model.Notificacion;
import com.edumanage.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Gestión de notificaciones del After School")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones")
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID")
    public ResponseEntity<Notificacion> obtenerPorId(@PathVariable Long id) {
        return notificacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva notificación")
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.crear(notificacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Long id, @Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.actualizar(id, notificacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}