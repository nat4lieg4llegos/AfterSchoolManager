package com.afterschool.estudiante.repository;

import com.afterschool.estudiante.model.ContactoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {

    boolean existsByTelefonoAndEstudiante_Id(String telefono, Long estudianteId);
}