-- Migración V1: Creación inicial de la tabla de reportes de progreso

CREATE TABLE IF NOT EXISTS reporte_progreso (
                                                id BIGINT NOT NULL AUTO_INCREMENT,
                                                estudiante_id BIGINT NOT NULL,
                                                taller_id BIGINT NOT NULL,
                                                observaciones TEXT NOT NULL,
                                                fecha_reporte DATE NOT NULL,
                                                PRIMARY KEY (id)
    );