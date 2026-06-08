package com.edumanage.reportes.controller;

import com.edumanage.reportes.model.ReporteProgreso;
import com.edumanage.reportes.service.ReporteProgresoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Gestión de reportes de progreso del After School")
public class ReporteProgresoController {

    @Autowired
    private ReporteProgresoService reporteService;

    @GetMapping
    @Operation(summary = "Obtener todos los reportes")
    public List<ReporteProgreso> obtenerTodos() {
        return reporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID")
    public ResponseEntity<ReporteProgreso> obtenerPorId(@PathVariable Long id) {
        return reporteService.obtenerPorId(id)
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