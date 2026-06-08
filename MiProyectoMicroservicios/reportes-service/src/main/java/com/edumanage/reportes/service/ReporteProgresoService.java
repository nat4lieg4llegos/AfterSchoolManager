package com.edumanage.reportes.service;

import com.edumanage.reportes.model.ReporteProgreso;
import com.edumanage.reportes.repository.ReporteProgresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteProgresoService {

    @Autowired
    private ReporteProgresoRepository reporteRepository;

    public List<ReporteProgreso> obtenerTodos() {
        return reporteRepository.findAll();
    }

    public Optional<ReporteProgreso> obtenerPorId(Long id) {
        return reporteRepository.findById(id);
    }

    public ReporteProgreso crear(ReporteProgreso reporte) {
        return reporteRepository.save(reporte);
    }

    public ReporteProgreso actualizar(Long id, ReporteProgreso datos) {
        ReporteProgreso reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        reporte.setEstudianteId(datos.getEstudianteId());
        reporte.setTallerId(datos.getTallerId());
        reporte.setObservaciones(datos.getObservaciones());
        reporte.setFechaReporte(datos.getFechaReporte());
        return reporteRepository.save(reporte);
    }

    public void eliminar(Long id) {
        reporteRepository.deleteById(id);
    }
}