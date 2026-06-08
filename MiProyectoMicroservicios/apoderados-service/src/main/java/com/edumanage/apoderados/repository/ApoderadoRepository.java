package com.edumanage.apoderados.repository;

import com.edumanage.apoderados.model.Apoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoderadoRepository extends JpaRepository<Apoderado, Long> {
}