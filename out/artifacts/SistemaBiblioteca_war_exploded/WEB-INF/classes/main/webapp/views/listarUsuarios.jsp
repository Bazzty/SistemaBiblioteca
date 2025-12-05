<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Usuario" %>

<%
    // 1. RECUPERAR USUARIO DE SESIÓN
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

    // 2. VALIDACIÓN DE SEGURIDAD (Solo admin)
    // Si no hay usuario o no es admin, redirigir a index.jsp (sin "../")
    if (usuarioLogueado == null || !"admin".equalsIgnoreCase(usuarioLogueado.getRol())) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary mb-4 shadow">
    <div class="container">
        <span class="navbar-brand">🏛️ Administración de Usuarios</span>
        <div class="d-flex align-items-center gap-3">
            <span class="text-white">Admin: <strong><%= usuarioLogueado.getNombre() %></strong></span>
            <%-- Usamos request.contextPath para asegurar que vuelva correctamente al menú --%>
            <a href="${pageContext.request.contextPath}/jsp/MenuAdmin.jsp" class="btn btn-sm btn-outline-light">Volver al Menú</a>
        </div>
    </div>
</nav>

<div class="container">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>👥 Listado de Usuarios</h2>
        <%-- CORREGIDO: Quitamos "../" porque ya estamos en el servlet /usuarios --%>
        <a href="usuarios?action=nuevo" class="btn btn-success">
            + Nuevo Usuario
        </a>
    </div>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
                    if (lista != null && !lista.isEmpty()) {
                        for (Usuario u : lista) {
                %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNombre() %></td>
                    <td><%= u.getEmail() %></td>
                    <td>
                        <%-- Badge de color según el rol --%>
                        <span class="badge <%= "admin".equalsIgnoreCase(u.getRol()) ? "bg-danger" : "bg-info text-dark" %>">
                                <%= u.getRol().toUpperCase() %>
                        </span>
                    </td>
                    <td class="text-center">
                        <%-- CORREGIDO: Quitamos "../" en los enlaces de Editar y Eliminar --%>
                        <a href="usuarios?action=editar&id=<%= u.getId() %>" class="btn btn-sm btn-warning">✏️ Editar</a>

                        <%-- Evitar que el admin se borre a sí mismo por error --%>
                        <% if(u.getId() != usuarioLogueado.getId()) { %>
                        <a href="usuarios?action=eliminar&id=<%= u.getId() %>"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('¿Estás seguro de borrar al usuario <%= u.getNombre() %>?');">
                            🗑️ Eliminar
                        </a>
                        <% } else { %>
                        <button class="btn btn-sm btn-secondary" disabled>Tú</button>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center p-4">
                        <p class="text-muted">No hay usuarios registrados.</p>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3 text-center">
        <%-- CORREGIDO: Ruta segura al menú --%>
        <a href="${pageContext.request.contextPath}/jsp/MenuAdmin.jsp" class="btn btn-secondary">
            &larr; Volver al Menú Principal
        </a>
    </div>
</div>

</body>
</html>