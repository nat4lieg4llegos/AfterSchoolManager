package com.edumanage.notificaciones.NotificacionesTest.repository;

import com.edumanage.notificaciones.NotificacionesTest.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
}