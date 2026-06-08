CREATE TABLE IF NOT EXISTS actividad (
                                         id BIGINT NOT NULL AUTO_INCREMENT,
                                         taller_id BIGINT NOT NULL,
                                         titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_programada DATE NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS material_requerido (
                                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                                  actividad_id BIGINT NOT NULL,
                                                  nombre_material VARCHAR(255) NOT NULL,
    cantidad_necesaria INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_actividad_material FOREIGN KEY (actividad_id) REFERENCES actividad (id) ON DELETE CASCADE
    );