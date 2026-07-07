CREATE TABLE IF NOT EXISTS intento_envio (
                                             id BIGINT NOT NULL AUTO_INCREMENT,
                                             canal VARCHAR(50) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_intento DATETIME,
    notificacion_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_intento_notificacion FOREIGN KEY (notificacion_id) REFERENCES notificacion(id)
    );