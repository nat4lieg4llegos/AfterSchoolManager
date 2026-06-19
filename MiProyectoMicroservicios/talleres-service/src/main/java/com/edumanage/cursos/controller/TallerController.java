package com.edumanage.cursos.controller;

import com.edumanage.cursos.model.Taller;
import com.edumanage.cursos.service.TallerService;
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
@RequestMapping("/api/v1/talleres")
@Tag(name = "Talleres", description = "Operaciones CRUD para gestión de talleres")
public class TallerController {

    private final TallerService tallerService;

    public TallerController(TallerService tallerService) {
        this.tallerService = tallerService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los talleres")
    public CollectionModel<EntityModel<Taller>> listar() {
        List<EntityModel<Taller>> talleres = tallerService.listarTodos().stream()
                .map(t -> EntityModel.of(t,
                        linkTo(methodOn(TallerController.class).obtener(t.getId())).withSelfRel(),
                        linkTo(methodOn(TallerController.class).listar()).withRel("talleres")))
                .collect(Collectors.toList());
        return CollectionModel.of(talleres,
                linkTo(methodOn(TallerController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un taller por ID")
    public ResponseEntity<EntityModel<Taller>> obtener(@PathVariable Long id) {
        return tallerService.buscarPorId(id)
                .map(t -> EntityModel.of(t,
                        linkTo(methodOn(TallerController.class).obtener(id)).withSelfRel(),
                        linkTo(methodOn(TallerController.class).listar()).withRel("talleres")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo taller")
    public ResponseEntity<Taller> crear(@Valid @RequestBody Taller taller) {
        return ResponseEntity.ok(tallerService.guardar(taller));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un taller existente")
    public ResponseEntity<Taller> actualizar(@PathVariable Long id, @Valid @RequestBody Taller taller) {
        return ResponseEntity.ok(tallerService.actualizar(id, taller));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un taller por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tallerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}