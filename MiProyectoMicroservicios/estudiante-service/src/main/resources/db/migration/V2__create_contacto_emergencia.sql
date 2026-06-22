CREATE TABLE IF NOT EXISTS contacto_emergencia (
                                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                   nombre VARCHAR(100) NOT NULL,
    parentesco VARCHAR(50) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    estudiante_id BIGINT NOT NULL,
    CONSTRAINT fk_contacto_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(id)
    );