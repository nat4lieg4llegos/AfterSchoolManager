CREATE TABLE IF NOT EXISTS taller (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(100) NOT NULL,
    sede VARCHAR(100) NOT NULL,
    cupo_maximo INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS horario (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       taller_id BIGINT NOT NULL,
                                       dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    CONSTRAINT fk_horario_taller FOREIGN KEY (taller_id) REFERENCES taller(id)
    );