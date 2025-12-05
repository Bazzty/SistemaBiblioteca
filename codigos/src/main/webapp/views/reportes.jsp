<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null || !"admin".equalsIgnoreCase(usuario.getRol())) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Reportes del Sistema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary mb-4 shadow">
    <div class="container">
        <span class="navbar-brand">📊 Panel de Estadísticas</span>
        <div class="d-flex align-items-center gap-3">
            <span class="text-white">Admin: <strong><%= usuario.getNombre() %></strong></span>
            <a href="jsp/MenuAdmin.jsp" class="btn btn-sm btn-outline-light">Volver al Menú</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-4">Resumen General</h2>

    <div class="row text-center">
        <div class="col-md-4 mb-3">
            <div class="card text-white bg-success shadow h-100">
                <div class="card-header">Total Libros</div>
                <div class="card-body">
                    <h1 class="display-4 fw-bold"><%= request.getAttribute("totalLibros") %></h1>
                    <p class="card-text">Libros registrados en inventario</p>
                </div>
            </div>
        </div>

        <div class="col-md-4 mb-3">
            <div class="card text-white bg-info shadow h-100">
                <div class="card-header">Total Usuarios</div>
                <div class="card-body">
                    <h1 class="display-4 fw-bold"><%= request.getAttribute("totalUsuarios") %></h1>
                    <p class="card-text">Cuentas activas en el sistema</p>
                </div>
            </div>
        </div>

        <div class="col-md-4 mb-3">
            <div class="card text-white bg-warning shadow h-100">
                <div class="card-header text-dark">Préstamos Pendientes</div>
                <div class="card-body text-dark">
                    <h1 class="display-4 fw-bold"><%= request.getAttribute("prestamosPendientes") %></h1>
                    <p class="card-text">Libros sin devolver</p>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-4 text-center">
        <div class="mt-4 text-center">
            <a href="jsp/MenuAdmin.jsp" class="btn btn-secondary btn-lg">Volver al Panel Principal</a>
        </div>
    </div>
</div>

</body>
</html>