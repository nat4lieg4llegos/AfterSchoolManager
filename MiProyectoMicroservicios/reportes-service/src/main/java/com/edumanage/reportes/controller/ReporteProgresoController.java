package com.edumanage.reportes.controller;

import com.edumanage.reportes.model.ReporteProgreso;
import com.edumanage.reportes.service.ReporteProgresoService;
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
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Gestión de reportes de progreso del After School")
public class ReporteProgresoController {

    @Autowired
    private ReporteProgresoService reporteService;

    @GetMapping
    @Operation(summary = "Obtener todos los reportes")
    public CollectionModel<EntityModel<ReporteProgreso>> obtenerTodos() {
        List<EntityModel<ReporteProgreso>> reportes = reporteService.obtenerTodos().stream()
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(ReporteProgresoController.class).obtenerPorId(r.getId())).withSelfRel(),
                        linkTo(methodOn(ReporteProgresoController.class).obtenerTodos()).withRel("reportes")))
                .collect(Collectors.toList());
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteProgresoController.class).obtenerTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID")
    public ResponseEntity<EntityModel<ReporteProgreso>> obtenerPorId(@PathVariable Long id) {
        return reporteService.obtenerPorId(id)
                .map(r -> EntityModel.of(r,
                        linkTo(methodOn(ReporteProgresoController.class).obtenerPorId(id)).withSelfRel(),
                        linkTo(methodOn(ReporteProgresoController.class).obtenerTodos()).withRel("reportes")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo reporte")
    public ResponseEntity<ReporteProgreso> crear(@Valid @RequestBody ReporteProgreso reporte) {
        return ResponseEntity.ok(reporteService.crear(reporte));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte")
    public ResponseEntity<ReporteProgreso> actualizar(@PathVariable Long id, @Valid @RequestBody ReporteProgreso reporte) {
        return ResponseEntity.ok(reporteService.actualizar(id, reporte));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}