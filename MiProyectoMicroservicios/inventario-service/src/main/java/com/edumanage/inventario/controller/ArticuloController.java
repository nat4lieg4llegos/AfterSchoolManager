package com.edumanage.inventario.controller;

import com.edumanage.inventario.model.Articulo;
import com.edumanage.inventario.service.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Gestión de inventario del After School")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    @Operation(summary = "Obtener todos los artículos")
    public List<Articulo> obtenerTodos() {
        return articuloService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener artículo por ID")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo artículo")
    public ResponseEntity<Articulo> crear(@Valid @RequestBody Articulo articulo) {
        return ResponseEntity.ok(articuloService.crear(articulo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar artículo")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @Valid @RequestBody Articulo articulo) {
        return ResponseEntity.ok(articuloService.actualizar(id, articulo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artículo")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        articuloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}