package com.edumanage.asistencia.controller;

import com.edumanage.asistencia.model.RegistroAsistencia;
import com.edumanage.asistencia.service.RegistroAsistenciaService;
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
@RequestMapping("/api/asistencia")
@Tag(name = "Asistencia", description = "Gestión de registros de asistencia del After School")
public class RegistroAsistenciaController {

    @Autowired
    private RegistroAsistenciaService registroService;

    @GetMapping
    @Operation(summary = "Obtener todos los registros de asistencia")
    public CollectionModel<EntityModel<RegistroAsistencia>> obtenerTodos() {
        List<EntityModel<RegistroAsistencia>> registros = registroService.obtenerTodos().stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(RegistroAsistenciaController.class).obtenerPorId(r.getId())).withSelfRel(),
                        linkTo(methodOn(RegistroAsistenciaController.class).obtenerTodos()).withRel("asistencia")))
                .collect(Collectors.toList());
        return CollectionModel.of(registros,
                linkTo(methodOn(RegistroAsistenciaController.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro de asistencia por ID")
    public ResponseEntity<EntityModel<RegistroAsistencia>> obtenerPorId(@PathVariable Long id) {
        return registroService.obtenerPorId(id)
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(RegistroAsistenciaController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(RegistroAsistenciaController.class).obtenerTodos()).withRel("asistencia")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo registro de asistencia")
    public ResponseEntity<RegistroAsistencia> crear(@Valid @RequestBody RegistroAsistencia registro) {
        return ResponseEntity.ok(registroService.crear(registro));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar registro de asistencia")
    public ResponseEntity<RegistroAsistencia> actualizar(@PathVariable Long id, @Valid @RequestBody RegistroAsistencia registro) {
        return ResponseEntity.ok(registroService.actualizar(id, registro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de asistencia")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}