-- Migración V1: Creación inicial de las tablas del servicio de autenticación

-- Tabla principal de usuarios
CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );

-- Tabla de sesiones vinculada a usuarios
-- usuario_id es la FK que apunta a la tabla usuario
CREATE TABLE IF NOT EXISTS sesion_usuario (
                                              id BIGINT NOT NULL AUTO_INCREMENT,
                                              fecha_inicio DATETIME,
                                              fecha_fin DATETIME,
                                              dispositivo VARCHAR(255),
    usuario_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_sesion_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
    );