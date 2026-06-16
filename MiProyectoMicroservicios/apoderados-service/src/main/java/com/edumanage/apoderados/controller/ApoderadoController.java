package com.edumanage.apoderados.controller;

import com.edumanage.apoderados.model.Apoderado;
import com.edumanage.apoderados.service.ApoderadoService;
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
@RequestMapping("/api/apoderados")
@Tag(name = "Apoderados", description = "Gestión de apoderados del After School")
public class ApoderadoController {

    @Autowired
    private ApoderadoService apoderadoService;

    @GetMapping
    @Operation(summary = "Obtener todos los apoderados")
    public CollectionModel<EntityModel<Apoderado>> obtenerTodos() {
        List<EntityModel<Apoderado>> apoderados = apoderadoService.obtenerTodos().stream()
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ApoderadoController.class).obtenerPorId(a.getId())).withSelfRel(),
                        linkTo(methodOn(ApoderadoController.class).obtenerTodos()).withRel("apoderados")))
                .collect(Collectors.toList());
        return CollectionModel.of(apoderados,
                linkTo(methodOn(ApoderadoController.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener apoderado por ID")
    public ResponseEntity<EntityModel<Apoderado>> obtenerPorId(@PathVariable Long id) {
        return apoderadoService.obtenerPorId(id)
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ApoderadoController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ApoderadoController.class).obtenerTodos()).withRel("apoderados")))
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