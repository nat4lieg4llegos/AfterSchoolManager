--- Migración V1: Creación inicial de las tablas de talleres y horarios

CREATE TABLE IF NOT EXISTS taller (
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      nombre VARCHAR(150) NOT NULL,
    sede VARCHAR(150) NOT NULL,
    cupo_maximo INT NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS horario (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       taller_id BIGINT NOT NULL,
                                       dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_taller_horario FOREIGN KEY (taller_id) REFERENCES taller(id) ON DELETE CASCADE
    );