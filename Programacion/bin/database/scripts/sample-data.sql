SET FOREIGN_KEY_CHECKS = 0;

-- BORRADO DE DATOS
DELETE FROM PRESTAMOS;
DELETE FROM EJEMPLARES;
DELETE FROM USUARIOS;
DELETE FROM PENALIZACIONES;
DELETE FROM LIBROS_AUTORES;
DELETE FROM AUTORES;
DELETE FROM LIBROS;

SET FOREIGN_KEY_CHECKS = 1;


-- =========================
-- INSERTS LIBROS
-- =========================

INSERT INTO LIBROS VALUES 
(11, 'Crónica de una muerte', 9788439734130, 'Random House', 'Realismo mágico', 7),
(12, 'Fahrenheit 451', 9788490325070, 'Debolsillo', 'Distopía', 9),
(13, 'El resplandor', 9788497593748, 'Debolsillo', 'Terror', 5),
(14, 'Sapiens', 9788499926223, 'Debate', 'Ensayo', 25),
(15, 'Dune', 9788497596824, 'Debolsillo', 'Ciencia ficción', 11);


-- =========================
-- INSERTS AUTORES
-- =========================

INSERT INTO AUTORES VALUES
(11, 'Isabel', 'Allende', 'Chile'),
(12, 'Ray', 'Bradbury', 'EEUU'),
(13, 'Jorge Luis', 'Borges', 'Argentina'),
(14, 'Albert', 'Camus', 'Argelia'),
(15, 'Virginia', 'Woolf', 'Reino Unido');


-- =========================
-- INSERTS LIBROS_AUTORES
-- =========================

INSERT INTO LIBROS_AUTORES VALUES
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15);


-- =========================
-- INSERTS PENALIZACIONES
-- =========================

INSERT INTO PENALIZACIONES VALUES
(1, 'Manchas', 4),
(2, 'Anotaciones', 6),
(3, 'Extracción de hojas', 25),
(4, 'No devolver multimedia', 5),
(5, 'Daño código barras', 3);


-- =========================
-- INSERTS USUARIOS
-- =========================

INSERT INTO USUARIOS VALUES
(10, '01234567K', 'Roberto Sanz', 600000010, 'rob10', 10123456789, NULL, NULL),
(11, '11122233L', 'Ana Belén', 600000011, 'anaB11', 11122334455, NULL, NULL),
(12, '22233344M', 'Pedro Picazo', 600000012, 'pedroP', 22233445566, NULL, NULL),
(13, '33344455N', 'Marta Sánchez', 600000013, 'martaS', 33344556677, NULL, NULL),
(14, '44455566O', 'Diego Alva', 600000014, 'diegoA', 44455667788, NULL, NULL);


-- =========================
-- INSERTS EJEMPLARES
-- =========================

INSERT INTO EJEMPLARES VALUES
(11, 'Nuevo', 11),
(12, 'Usado', 11),
(13, 'Dañado', 12),
(14, 'Nuevo', 12),
(15, 'Aceptable', 13);


-- =========================
-- INSERTS PRESTAMOS
-- =========================

INSERT INTO PRESTAMOS VALUES
(11, '2025-11-10', '2025-11-17', 11, 10),
(12, '2025-11-11', '2025-11-18', 12, 11),
(13, '2025-11-12', '2025-11-19', 13, 12),
(14, '2025-11-13', '2025-11-20', 14, 13),
(15, '2025-11-14', '2025-11-21', 15, 14);