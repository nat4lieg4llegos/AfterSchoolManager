CREATE TABLE IF NOT EXISTS estudiante (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          rut VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    apoderado_id BIGINT
    );