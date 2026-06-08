package com.edumanage.reportes.repository;

import com.edumanage.reportes.model.ReporteProgreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteProgresoRepository extends JpaRepository<ReporteProgreso, Long> {
}