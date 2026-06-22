CREATE TABLE historial_estado (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  estado_anterior VARCHAR(50),
                                  estado_nuevo VARCHAR(50) NOT NULL,
                                  fecha_cambio DATETIME NOT NULL,
                                  observacion VARCHAR(255),
                                  inscripcion_id BIGINT NOT NULL,
                                  CONSTRAINT fk_historial_inscripcion FOREIGN KEY (inscripcion_id) REFERENCES inscripcion(id)
);