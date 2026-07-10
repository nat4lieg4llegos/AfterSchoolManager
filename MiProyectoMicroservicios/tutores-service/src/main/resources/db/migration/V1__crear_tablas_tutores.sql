CREATE TABLE IF NOT EXISTS tutor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) NOT NULL UNIQUE,
    nombre_completo VARCHAR(255) NOT NULL,
    fecha_contratacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS especialidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    area VARCHAR(255) NOT NULL,
    anios_experiencia INT,
    tutor_id BIGINT NOT NULL,
    CONSTRAINT fk_especialidad_tutor FOREIGN KEY (tutor_id) REFERENCES tutor(id)
);
