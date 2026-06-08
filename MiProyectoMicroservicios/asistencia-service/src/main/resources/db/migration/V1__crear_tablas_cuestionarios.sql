-- Migración V1: Creación inicial de la tabla de registros de asistencia

CREATE TABLE IF NOT EXISTS registro_asistencia (
                                                   id BIGINT NOT NULL AUTO_INCREMENT,
                                                   estudiante_id BIGINT NOT NULL,
                                                   taller_id BIGINT NOT NULL,
                                                   fecha_hora_ingreso DATETIME NOT NULL,
                                                   fecha_hora_salida DATETIME,
                                                   quien_retira VARCHAR(255),
    PRIMARY KEY (id)
    );