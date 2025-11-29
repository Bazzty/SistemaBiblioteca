<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Libro" %>
<%@ page import="model.Usuario" %>

<%
    // VALIDACIÓN DE SEGURIDAD (Requisito: Uso de sesiones - 10 pts)
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Inventario de Libros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary mb-4 shadow">
    <div class="container">
        <span class="navbar-brand">📚 Biblioteca: Panel de Control</span>
        <div class="d-flex align-items-center gap-3">
            <span class="text-white">Usuario: <strong><%= usuario.getNombre() %></strong></span>
            <a href="home.jsp" class="btn btn-sm btn-outline-light">Volver al Menú</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>📚 Listado de Libros</h2>
        <a href="../libros?action=nuevo" class="btn btn-success">
            + Agregar Nuevo Libro
        </a>
    </div>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Autor (ID)</th>
                        <th>Categoría (ID)</th>
                        <th>Stock</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Recibimos la lista desde el Servlet
                        List<Libro> lista = (List<Libro>) request.getAttribute("listaLibros");

                        if (lista != null && !lista.isEmpty()) {
                            for (Libro l : lista) {
                    %>
                    <tr>
                        <td><%= l.getId() %></td>
                        <td><%= l.getTitulo() %></td>
                        <td><%= l.getIdAutor() %></td>
                        <td><%= l.getIdCategoria() %></td>
                        <td>
                            <% if(l.getStock() > 0) { %>
                                <span class="badge bg-success"><%= l.getStock() %></span>
                            <% } else { %>
                                <span class="badge bg-danger">Agotado</span>
                            <% } %>
                        </td>
                        <td class="text-center">
                            <a href="../libros?action=editar&id=<%= l.getId() %>" class="btn btn-sm btn-warning">✏️ Editar</a>
                            <a href="../libros?action=eliminar&id=<%= l.getId() %>"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('¿Estás seguro de borrar este libro?');">🗑️ Borrar</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center p-4">
                            <p class="text-muted">No hay libros registrados en el sistema.</p>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>