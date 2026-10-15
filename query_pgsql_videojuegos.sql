-- Database: apijdbc

-- parte 2 crear la base de datos
-- DROP DATABASE IF EXISTS apijdbc;

CREATE DATABASE apijdbc;

--parte3 Crear la tabla
CREATE TABLE videojuegos (
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    plataforma VARCHAR(50) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    disponible BOOLEAN NOT NULL,
    genero VARCHAR(50),
    PRIMARY KEY (codigo)
);

INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES
('01', 'FIFA', 'PC', 89.99, true, 'deportes')

SELECT * FROM videojuegos;

