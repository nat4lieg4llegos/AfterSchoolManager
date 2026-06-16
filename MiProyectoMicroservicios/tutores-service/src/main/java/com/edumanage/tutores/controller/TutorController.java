package com.edumanage.tutores.controller;

import com.edumanage.tutores.model.Tutor;
import com.edumanage.tutores.service.TutorService;
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
@RequestMapping("/api/tutores")
@Tag(name = "Tutores", description = "Gestión de tutores del After School")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    @Operation(summary = "Obtener todos los tutores")
    public CollectionModel<EntityModel<Tutor>> obtenerTodos() {
        List<EntityModel<Tutor>> tutores = tutorService.obtenerTodos().stream()
                .map(t -> EntityModel.of(t,
                        linkTo(methodOn(TutorController.class).obtenerPorId(t.getId())).withSelfRel(),
                        linkTo(methodOn(TutorController.class).obtenerTodos()).withRel("tutores")))
                .collect(Collectors.toList());
        return CollectionModel.of(tutores,
                linkTo(methodOn(TutorController.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tutor por ID")
    public ResponseEntity<EntityModel<Tutor>> obtenerPorId(@PathVariable Long id) {
        return tutorService.obtenerPorId(id)
                .map(t -> EntityModel.of(t,
                        linkTo(methodOn(TutorController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(TutorController.class).obtenerTodos()).withRel("tutores")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo tutor")
    public ResponseEntity<Tutor> crear(@Valid @RequestBody Tutor tutor) {
        return ResponseEntity.ok(tutorService.crear(tutor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tutor")
    public ResponseEntity<Tutor> actualizar(@PathVariable Long id, @Valid @RequestBody Tutor tutor) {
        return ResponseEntity.ok(tutorService.actualizar(id, tutor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tutor")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tutorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}