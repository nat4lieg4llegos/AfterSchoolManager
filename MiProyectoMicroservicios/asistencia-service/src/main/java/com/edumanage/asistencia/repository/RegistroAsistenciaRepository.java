package com.edumanage.asistencia.repository;

import com.edumanage.asistencia.model.RegistroAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {
}