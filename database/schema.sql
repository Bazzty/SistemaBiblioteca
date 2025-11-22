CREATE DATABASE IF NOT EXISTS Biblioteca;
USE Biblioteca;

-- ===========================================
-- 1. TABLA USUARIO
-- ===========================================
CREATE TABLE usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         rol ENUM('admin', 'usuario') DEFAULT 'usuario'
);

-- ===========================================
-- 2. TABLA AUTOR
-- ===========================================
CREATE TABLE autor (
                       id_autor INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(150) NOT NULL
);

-- ===========================================
-- 3. TABLA CATEGORIA
-- ===========================================
CREATE TABLE categoria (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL
);

-- ===========================================
-- 4. TABLA LIBRO
-- ===========================================
CREATE TABLE libro (
                       id_libro INT AUTO_INCREMENT PRIMARY KEY,
                       titulo VARCHAR(200) NOT NULL,
                       id_autor INT NOT NULL,
                       id_categoria INT NOT NULL,
                       stock INT DEFAULT 0,
                       FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
                       FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- ===========================================
-- 5. TABLA PRESTAMO
-- ===========================================
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
