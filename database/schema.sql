-- 1. ¡IMPORTANTE! BORRA TODO lo VIEJO
DROP DATABASE IF EXISTS Biblioteca;

-- 2. CREAR BASE DE DATOS LIMPIA
CREATE DATABASE Biblioteca;
USE Biblioteca;

-- 3. CREAR TABLAS (ESTRUCTURA DE SEMANA 3)
CREATE TABLE usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         rol ENUM('admin', 'usuario') DEFAULT 'usuario'
);

CREATE TABLE autor (
                       id_autor INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(150) NOT NULL
);

CREATE TABLE categoria (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL
);

CREATE TABLE libro (
                       id_libro INT AUTO_INCREMENT PRIMARY KEY,
                       titulo VARCHAR(200) NOT NULL,
                       id_autor INT NOT NULL,
                       id_categoria INT NOT NULL,
                       stock INT DEFAULT 0,
    -- Relación correcta: Un autor puede tener muchos libros
                       FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
                       FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE prestamo (
                          id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
                          id_usuario INT NOT NULL,
                          id_libro INT NOT NULL,
                          fecha_prestamo DATE NOT NULL,
                          fecha_devolucion DATE NULL,
                          estado ENUM('pendiente', 'devuelto') DEFAULT 'pendiente',
                          FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
                          FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
);

-- 4. INSERTAR DATOS DE PRUEBA (PARA QUE NO ESTÉ VACÍA)

-- Usuarios
INSERT INTO usuario (nombre, email, password, rol) VALUES ('Bastian Admin', 'admin@biblioteca.cl', 'admin123', 'admin');
INSERT INTO usuario (nombre, email, password, rol) VALUES ('Juan Estudiante', 'juan@correo.cl', 'user123', 'usuario');

-- Autores y Categorías
INSERT INTO autor (nombre) VALUES ('Gabriel García Márquez'), ('J.K. Rowling'), ('Isabel Allende');
INSERT INTO categoria (nombre) VALUES ('Novela'), ('Ciencia Ficción'), ('Historia');

-- Libros
INSERT INTO libro (titulo, id_autor, id_categoria, stock) VALUES ('Cien Años de Soledad', 1, 1, 50);
INSERT INTO libro (titulo, id_autor, id_categoria, stock) VALUES ('Harry Potter', 2, 2, 10);