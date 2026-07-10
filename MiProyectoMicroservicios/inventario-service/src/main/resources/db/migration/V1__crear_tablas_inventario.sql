CREATE TABLE IF NOT EXISTS articulo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS movimiento_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    fecha_movimiento DATETIME,
    articulo_id BIGINT NOT NULL,
    CONSTRAINT fk_movimiento_articulo FOREIGN KEY (articulo_id) REFERENCES articulo(id)
);
