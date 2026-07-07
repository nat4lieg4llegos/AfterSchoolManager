CREATE TABLE IF NOT EXISTS detalle_reporte (
                                               id BIGINT NOT NULL AUTO_INCREMENT,
                                               indicador VARCHAR(255) NOT NULL,
    puntaje INT NOT NULL,
    reporte_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_detalle_reporte FOREIGN KEY (reporte_id) REFERENCES reporte_progreso(id)
    );