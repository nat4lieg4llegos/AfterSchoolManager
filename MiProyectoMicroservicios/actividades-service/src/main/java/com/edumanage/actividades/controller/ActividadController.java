package com.edumanage.actividades.controller;

import com.edumanage.actividades.model.Actividad;
import com.edumanage.actividades.service.ActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/actividades")
@Tag(name = "Actividades", description = "Gestión de actividades del After School")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @GetMapping
    @Operation(summary = "Obtener todas las actividades")
    public CollectionModel<EntityModel<Actividad>> obtenerTodas() {
        List<EntityModel<Actividad>> actividades = actividadService.obtenerTodas().stream()
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ActividadController.class).obtenerPorId(a.getId())).withSelfRel(),
                        linkTo(methodOn(ActividadController.class).obtenerTodas()).withRel("actividades")))
                .collect(Collectors.toList());
        return CollectionModel.of(actividades,
                linkTo(methodOn(ActividadController.class).obtenerTodas()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener actividad por ID")
    public ResponseEntity<EntityModel<Actividad>> obtenerPorId(@PathVariable Long id) {
        return actividadService.obtenerPorId(id)
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ActividadController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ActividadController.class).obtenerTodas()).withRel("actividades")))
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