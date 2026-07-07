package com.edumanage.reportes.service;

import com.edumanage.reportes.model.DetalleReporte;
import com.edumanage.reportes.model.ReporteProgreso;
import com.edumanage.reportes.repository.ReporteProgresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        validarDetalles(reporte);
        // Conecta cada detalle con su reporte (relacion bidireccional)
        if (reporte.getDetalles() != null) {
            reporte.getDetalles().forEach(d -> d.setReporte(reporte));
        }
        return reporteRepository.save(reporte);
    }

    public ReporteProgreso actualizar(Long id, ReporteProgreso datos) {
        ReporteProgreso reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reporte no encontrado con id " + id));
        reporte.setEstudianteId(datos.getEstudianteId());
        reporte.setTallerId(datos.getTallerId());
        reporte.setObservaciones(datos.getObservaciones());
        reporte.setFechaReporte(datos.getFechaReporte());
        return reporteRepository.save(reporte);
    }

    public void eliminar(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new NoSuchElementException("Reporte no encontrado con id " + id);
        }
        reporteRepository.deleteById(id);
    }

    // Regla de negocio: un reporte no puede tener el mismo indicador repetido
    private void validarDetalles(ReporteProgreso reporte) {
        if (reporte.getDetalles() == null || reporte.getDetalles().isEmpty()) {
            return;
        }
        for (int i = 0; i < reporte.getDetalles().size(); i++) {
            for (int j = i + 1; j < reporte.getDetalles().size(); j++) {
                DetalleReporte a = reporte.getDetalles().get(i);
                DetalleReporte b = reporte.getDetalles().get(j);
                if (a.getIndicador() != null && a.getIndicador().equalsIgnoreCase(b.getIndicador())) {
                    throw new IllegalArgumentException(
                            "El reporte no puede tener el indicador '" + a.getIndicador() + "' repetido"
                    );
                }
            }
        }
    }
}