<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%
    // Validar que haya sesión iniciada
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Administración</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary mb-5 shadow">
    <div class="container">
        <span class="navbar-brand">🏛️ Sistema de Biblioteca</span>
        <span class="text-white">Hola, <%= usuario.getNombre() %></span>
    </div>
</nav>

<div class="container">
    <div class="row text-center">
        <div class="col-md-12 mb-4">
            <h2>Bienvenido al Panel de Control</h2>
            <p class="text-muted">Selecciona una opción para empezar</p>
        </div>

        <div class="col-md-6">
            <div class="card shadow p-4">
                <h3>📚 Gestión de Libros</h3>
                <p>Agregar, editar, eliminar y listar libros del inventario.</p>
                <a href="../libros" class="btn btn-primary btn-lg">Ir a Inventario</a>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow p-4">
                <h3>🚪 Salir</h3>
                <p>Cerrar la sesión actual de forma segura.</p>
                <a href="../logout" class="btn btn-danger btn-lg">Cerrar Sesión</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>