<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Libro" %>
<%@ page import="model.Usuario" %>

<%
    // 1. VALIDACIÓN DE SEGURIDAD
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../index.jsp");
        return;
    }

    // 2. LÓGICA DE MENÚ (Volver atrás)
    String contexto = request.getContextPath();
    String linkVolver = contexto + "/jsp/MenuUsuario.jsp";
    boolean esAdmin = "admin".equalsIgnoreCase(usuario.getRol());

    if (esAdmin) {
        linkVolver = contexto + "/jsp/MenuAdmin.jsp";
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
            <a href="<%= linkVolver %>" class="btn btn-sm btn-outline-light">Volver al Menú</a>
        </div>
    </div>
</nav>

<div class="container">

    <% if ("PrestamoExitoso".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-success">¡Libro solicitado con éxito! Revisa "Mis Préstamos".</div>
    <% } else if ("SinStock".equals(request.getParameter("msg"))) { %>
    <div class="alert alert-danger">Error: No queda stock de ese libro o ya ocurrió un error.</div>
    <% } %>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>📚 Listado de Libros</h2>

        <%-- Botón de agregar solo para Admin --%>
        <% if(esAdmin) { %>
        <a href="libros?action=nuevo" class="btn btn-success"> + Agregar Nuevo Libro </a>
        <% } %>
    </div>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Autor</th>      <th>Categoría</th>  <th>Stock</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Libro> lista = (List<Libro>) request.getAttribute("listaLibros");

                    if (lista != null && !lista.isEmpty()) {
                        for (Libro l : lista) {
                %>
                <tr>
                    <td><%= l.getId() %></td>
                    <td><%= l.getTitulo() %></td>

                    <td><%= l.getNombreAutor() %></td>
                    <td><%= l.getNombreCategoria() %></td>

                    <td>
                        <% if(l.getStock() > 0) { %>
                        <span class="badge bg-success"><%= l.getStock() %></span>
                        <% } else { %>
                        <span class="badge bg-danger">Agotado</span>
                        <% } %>
                    </td>

                    <td class="text-center">
                        <%-- OPCIONES DE ADMIN --%>
                        <% if(esAdmin) { %>
                            <a href="libros?action=editar&id=<%= l.getId() %>" class="btn btn-sm btn-warning">✏️</a>
                            <a href="libros?action=eliminar&id=<%= l.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('¿Borrar este libro?');">🗑️</a>

                        <%-- OPCIONES DE USUARIO (SOLICITAR) --%>
                        <% } else { %>
                        <% if(l.getStock() > 0) { %>
                        <form action="${pageContext.request.contextPath}/prestamos" method="post" style="display:inline;">
                            <input type="hidden" name="idLibro" value="<%= l.getId() %>">
                            <button type="submit" class="btn btn-sm btn-primary">
                                📖 Solicitar
                            </button>
                        </form>
                        <% } else { %>
                        <button class="btn btn-sm btn-secondary" disabled>No disponible</button>
                        <% } %>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6" class="text-center p-4">
                        <p class="text-muted">No hay libros registrados.</p>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3 text-center">
        <a href="<%= linkVolver %>" class="btn btn-secondary">Volver al Menú Principal</a>
    </div>
</div>

</body>
</html>