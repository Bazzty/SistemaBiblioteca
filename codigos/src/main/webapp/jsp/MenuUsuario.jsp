<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Usuario" %>
<%
    // verifica sesión y rol.
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    // Redirigir si no está logueado o si no es Usuario
    if (usuario == null || !"Usuario".equals(usuario.getRol())) {
        response.sendRedirect(request.getContextPath() + "/jsp/views/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú de Usuario</title>
</head>
<body>
<h1>Bienvenido, Usuario <%= usuario.getNombre() %></h1>
<p>Aplicación para solicitar y gestionar tus préstamos.</p>

<h2>Operaciones</h2>
<ul>
    <li><a href="#">Buscar Libros y Solicitar Préstamo</a></li>
    <li><a href="#">Ver mis Préstamos Activos y Devolver</a></li>
</ul>

<%-- Enlace absoluto al LogoutServlet --%>
<a href="<%= request.getContextPath() %>/logout">Cerrar Sesión</a>
</body>
</html>