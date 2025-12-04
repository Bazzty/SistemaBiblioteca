<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Usuario" %>
<%
    // 1. Recuperar sesión
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    // 2. Validar: Que exista Y que su rol sea 'usuario'
    if (usuario == null || !"usuario".equalsIgnoreCase(usuario.getRol())) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú de Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-success mb-5 shadow">
    <div class="container">
        <span class="navbar-brand">📚 Biblioteca: Zona de Lectores</span>
        <span class="text-white">Hola, <%= usuario.getNombre() %></span>
    </div>
</nav>

<div class="container">
    <div class="row text-center">
        <div class="col-md-12 mb-4">
            <h2>Bienvenido, <%= usuario.getNombre() %></h2>
            <p class="text-muted">¿Qué quieres leer hoy?</p>
        </div>

        <div class="col-md-6">
            <div class="card shadow p-4 mb-3">
                <h3>📖 Catálogo</h3>
                <p>Busca libros disponibles y solicita un préstamo.</p>
                <a href="../libros" class="btn btn-success btn-lg">
                    Buscar Libros
                </a>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow p-4 mb-3">
                <h3>📅 Mis Préstamos</h3>
                <p>Revisa tus fechas de devolución.</p>
                <a href="../prestamos?action=mis_prestamos" class="btn btn-outline-success btn-lg">
                    Ver Mis Préstamos
                </a>
            </div>
        </div>

        <div class="col-12 mt-4">
            <a href="../logout" class="btn btn-danger">Cerrar Sesión</a>
        </div>
    </div>
</div>

</body>
</html>