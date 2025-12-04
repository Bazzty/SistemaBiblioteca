<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>👥 Lista de Usuarios</h2>
        <div>
            <a href="../jsp/MenuAdmin.jsp" class="btn btn-secondary">Volver al Menú</a>
            <a href="../usuarios?action=nuevo" class="btn btn-primary">+ Nuevo Usuario</a>
        </div>
    </div>

    <table class="table table-striped card shadow-sm">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
            if (lista != null) {
                for (Usuario u : lista) {
        %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getNombre() %></td>
            <td><%= u.getEmail() %></td>
            <td>
                <span class="badge <%= u.getRol().equals("admin") ? "bg-danger" : "bg-info" %>">
                    <%= u.getRol() %>
                </span>
            </td>
            <td>
                <a href="../usuarios?action=editar&id=<%= u.getId() %>" class="btn btn-sm btn-warning">Editar</a>
                <a href="../usuarios?action=eliminar&id=<%= u.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('¿Borrar usuario?');">Eliminar</a>
            </td>
        </tr>
        <%      }
        } %>
        </tbody>
    </table>
</div>
</body>
</html>
