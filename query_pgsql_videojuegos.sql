-- Database: apijdbc

-- parte 2 crear la base de datos
-- DROP DATABASE IF EXISTS apijdbc;

CREATE DATABASE apijdbc;

--parte3 Crear la tabla
drop table videojuegos;
CREATE TABLE videojuegos (
    codigo VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    plataforma VARCHAR(50) NOT NULL,
    precio DOUBLE PRECISION NOT NULL,
    disponible BOOLEAN NOT NULL,
    genero VARCHAR(50),
    PRIMARY KEY (codigo)
);

INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES
('01', 'FIFA', 'PC', 89.99, true, 'deportes')

-- agrega valores adicionales para probar parte 7, funciones JDBC
INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES
('02', 'GOW', 'XBOX', 59.99, true, 'Accion'),
('03', 'FEAR', 'PC', 19.99, true, 'FPS');


SELECT * FROM videojuegos;

