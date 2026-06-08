CREATE TABLE IF NOT EXISTS apoderado (
                                         id BIGINT NOT NULL AUTO_INCREMENT,
                                         rut VARCHAR(20) NOT NULL UNIQUE,
    nombre_completo VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS persona_autorizada (
                                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                                  apoderado_id BIGINT NOT NULL,
                                                  nombre_completo VARCHAR(255) NOT NULL,
    rut VARCHAR(20) NOT NULL,
    parentesco VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_apoderado_autorizado FOREIGN KEY (apoderado_id) REFERENCES apoderado(id) ON DELETE CASCADE
    );