CREATE TABLE IF NOT EXISTS observacion_asistencia (
                                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                                      tipo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    registro_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_observacion_registro FOREIGN KEY (registro_id) REFERENCES registro_asistencia(id)
    );