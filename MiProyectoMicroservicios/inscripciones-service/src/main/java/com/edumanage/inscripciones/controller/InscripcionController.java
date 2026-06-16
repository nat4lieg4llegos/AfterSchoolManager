package com.edumanage.inscripciones.controller;

import com.edumanage.inscripciones.model.Inscripcion;
import com.edumanage.inscripciones.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
    public CollectionModel<EntityModel<Inscripcion>> listar() {
        List<EntityModel<Inscripcion>> inscripciones = inscripcionService.listarTodos().stream()
                .map(i -> EntityModel.of(i,
                        linkTo(methodOn(InscripcionController.class).obtener(i.getId())).withSelfRel(),
                        linkTo(methodOn(InscripcionController.class).listar()).withRel("inscripciones")))
                .collect(Collectors.toList());
        return CollectionModel.of(inscripciones,
                linkTo(methodOn(InscripcionController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una inscripción por ID")
    public ResponseEntity<EntityModel<Inscripcion>> obtener(@PathVariable Long id) {
        return inscripcionService.buscarPorId(id)
                .map(i -> EntityModel.of(i,
                        linkTo(methodOn(InscripcionController.class).obtener(id)).withSelfRel(),
                        linkTo(methodOn(InscripcionController.class).listar()).withRel("inscripciones")))
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