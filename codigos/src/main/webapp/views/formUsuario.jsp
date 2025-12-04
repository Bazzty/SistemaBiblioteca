<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    // Verificamos si es un registro público (alguien creando cuenta desde el login)
    boolean esRegistroPublico = request.getAttribute("esRegistroPublico") != null;
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= (usuario != null) ? "Editar Usuario" : "Registro de Usuario" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-success text-white">
                    <h4><%= (usuario != null) ? "Editar Usuario" : "Registrarse" %></h4>
                </div>
                <div class="card-body">

                    <%-- BLOQUE NUEVO PARA MOSTRAR ERROR DE DUPLICADO --%>
                        <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>¡Error!</strong> <%= request.getAttribute("error") %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                        <% } %>
                    <%-- FIN BLOQUE ERROR --%>

                    <form action="<%= request.getContextPath() %>/usuarios" method="post">
                        <input type="hidden" name="id" value="<%= (usuario != null) ? usuario.getId() : "" %>">

                        <div class="mb-3">
                            <label>Nombre Completo:</label>
                            <input type="text" name="nombre" class="form-control" required
                                   value="<%= (usuario != null) ? usuario.getNombre() : "" %>">
                        </div>
                        <div class="mb-3">
                            <label>Email:</label>
                            <input type="email" name="email" class="form-control" required
                                   value="<%= (usuario != null) ? usuario.getEmail() : "" %>">
                        </div>
                        <div class="mb-3">
                            <label>Contraseña:</label>
                            <input type="password" name="password" class="form-control" required
                                   value="<%= (usuario != null) ? usuario.getPassword() : "" %>">
                        </div>

                        <%-- Solo mostramos el selector de Rol si NO es registro público --%>
                        <% if (!esRegistroPublico) { %>
                        <div class="mb-3">
                            <label>Rol:</label>
                            <select name="rol" class="form-select">
                                <option value="usuario" <%= (usuario!=null && "usuario".equals(usuario.getRol())) ? "selected" : "" %>>Usuario Normal</option>
                                <option value="admin" <%= (usuario!=null && "admin".equals(usuario.getRol())) ? "selected" : "" %>>Administrador</option>
                            </select>
                        </div>
                        <% } %>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">Guardar</button>
                            <a href="<%= esRegistroPublico ? "../index.jsp" : "../usuarios" %>" class="btn btn-secondary">Cancelar</a>
                        </div>
                    </