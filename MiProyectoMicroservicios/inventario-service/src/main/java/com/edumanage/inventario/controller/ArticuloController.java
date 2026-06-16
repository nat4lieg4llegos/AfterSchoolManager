package com.edumanage.inventario.controller;

import com.edumanage.inventario.model.Articulo;
import com.edumanage.inventario.service.ArticuloService;
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
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Gestion de inventario del After School")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    @Operation(summary = "Obtener todos los articulos")
    public CollectionModel<EntityModel<Articulo>> obtenerTodos() {
        List<EntityModel<Articulo>> articulos = articuloService.obtenerTodos().stream()
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ArticuloController.class).obtenerPorId(a.getId())).withSelfRel(),
                        linkTo(methodOn(ArticuloController.class).obtenerTodos()).withRel("inventario")))
                .collect(Collectors.toList());
        return CollectionModel.of(articulos,
                linkTo(methodOn(ArticuloController.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener articulo por ID")
    public ResponseEntity<EntityModel<Articulo>> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerPorId(id)
                .map(a -> EntityModel.of(a,
                        linkTo(methodOn(ArticuloController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ArticuloController.class).obtenerTodos()).withRel("inventario")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo articulo")
    public ResponseEntity<Articulo> crear(@Valid @RequestBody Articulo articulo) {
        return ResponseEntity.ok(articuloService.crear(articulo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar articulo")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @Valid @RequestBody Articulo articulo) {
        return ResponseEntity.ok(articuloService.actualizar(id, articulo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar articulo")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        articuloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}