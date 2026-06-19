package com.afterschool.estudiante.controller;

import com.afterschool.estudiante.dto.EstudianteRequestDTO;
import com.afterschool.estudiante.dto.EstudianteResponseDTO;
import com.afterschool.estudiante.service.EstudianteService;
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
@RequestMapping("/api/v1/estudiantes")
@Tag(name = "Estudiantes", description = "Gestion de estudiantes del AfterSchool")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los estudiantes")
    public CollectionModel<EntityModel<EstudianteResponseDTO>> listar() {
        List<EntityModel<EstudianteResponseDTO>> estudiantes = service.listarTodos().stream()
                .map(e -> EntityModel.of(e,
                        linkTo(methodOn(EstudianteController.class).obtener(e.getId())).withSelfRel(),
                        linkTo(methodOn(EstudianteController.class).listar()).withRel("estudiantes")))
                .collect(Collectors.toList());
        return CollectionModel.of(estudiantes,
                linkTo(methodOn(EstudianteController.class).listar()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por ID")
    public ResponseEntity<EntityModel<EstudianteResponseDTO>> obtener(@PathVariable Long id) {
        EstudianteResponseDTO dto = service.obtenerPorId(id);
        EntityModel<EstudianteResponseDTO> model = EntityModel.of(dto,
                linkTo(methodOn(EstudianteController.class).obtener(id)).withSelfRel(),
                linkTo(methodOn(EstudianteController.class).listar()).withRel("estudiantes"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo estudiante")
    public ResponseEntity<EstudianteResponseDTO> crear(@Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.status(201).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante")
    public ResponseEntity<EstudianteResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}