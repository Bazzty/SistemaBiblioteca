<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%
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
            <p class="text-muted">Selecciona una opción</p>
        </div>

        <div class="col-md-4">
            <div class="card shadow p-4 mb-3">
                <h3>📚 Libros</h3>
                <p>Inventario y Stock.</p>
                <a href="../libros" class="btn btn-primary">Ir a Inventario</a>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow p-4 mb-3">
                <h3>👥 Usuarios</h3>
                <p>Administrar clientes.</p>
                <a href="../usuarios" class="btn btn-info text-white">Ver Usuarios</a>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow p-4 mb-3">
                <h3>📦 Préstamos</h3>
                <p>Historial y Devoluciones.</p>
                <a href="../prestamos?action=mis_prestamos" class="btn btn-warning">
                    Ver Todos
                </a>
            </div>
        </div>

        <div class="col-12 mt-4">
            <a href="../logout" class="btn btn-danger btn-lg">Cerrar Sesión</a>
        </div>
    </div>
</div>

</body>
</html>