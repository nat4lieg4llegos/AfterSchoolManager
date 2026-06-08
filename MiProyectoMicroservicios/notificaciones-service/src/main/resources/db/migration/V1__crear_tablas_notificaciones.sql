CREATE TABLE IF NOT EXISTS notificacion (
                                            id BIGINT NOT NULL AUTO_INCREMENT,
                                            apoderado_id BIGINT NOT NULL,
                                            tipo VARCHAR(50) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio DATETIME NOT NULL,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
    );