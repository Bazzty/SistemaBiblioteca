<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Prestamo" %>
<%@ page import="model.Usuario" %>

<%
    // 1. VALIDACIÓN DE SEGURIDAD
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    // 2. LÓGICA DE NAVEGACIÓN
    String contexto = request.getContextPath();
    boolean esAdmin = "admin".equalsIgnoreCase(usuario.getRol());
    String linkVolver = esAdmin ? contexto + "/jsp/MenuAdmin.jsp" : contexto + "/jsp/MenuUsuario.jsp";
%>

<!DOCTYPE html>
<html>
<head>
    <title>Mis Préstamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-success mb-4 shadow">
    <div class="container">
        <span class="navbar-brand">📚 Biblioteca: Gestión de Préstamos</span>
        <div class="d-flex align-items-center gap-3">
            <span class="text-white">Usuario: <strong><%= usuario.getNombre() %></strong></span>
            <a href="<%= linkVolver %>" class="btn btn-sm btn-outline-light">Volver al Menú</a>
        </div>
    </div>
</nav>

<div class="container">

    <%-- Mensajes de feedback tras devolver --%>
    <% String msg = request.getParameter("msg");
        if ("DevolucionExitosa".equals(msg)) { %>
    <div class="alert alert-success">✅ ¡Libro devuelto correctamente! El stock ha aumentado.</div>
    <% } else if ("ErrorDevolucion".equals(msg)) { %>
    <div class="alert alert-danger">❌ Hubo un error al intentar devolver el libro.</div>
    <% } %>

    <div class="card shadow-sm">
        <div class="card-header bg-white">
            <h4 class="mb-0">
                <%= esAdmin ? "📦 Historial de Todos los Préstamos (Admin)" : "📅 Mis Préstamos Activos e Historial" %>
            </h4>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Libro</th>
                    <%-- El admin necesita saber QUIÉN tiene el libro --%>
                    <% if(esAdmin) { %> <th>Usuario Prestamista</th> <% } %>

                    <th>Fecha Préstamo</th>
                    <th>Fecha Devolución</th>
                    <th>Estado</th>
                    <th class="text-center">Acción</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Prestamo> lista = (List<Prestamo>) request.getAttribute("listaPrestamos");
                    if (lista != null && !lista.isEmpty()) {
                        for (Prestamo p : lista) {
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><strong><%= p.getTituloLibro() %></strong></td>

                    <% if(esAdmin) { %>
                    <td><%= p.getNombreUsuario() %></td>
                    <% } %>

                    <td><%= p.getFechaPrestamo() %></td>
                    <td><%= (p.getFechaDevolucion() != null) ? p.getFechaDevolucion() : "-" %></td>

                    <td>
                        <% if("pendiente".equalsIgnoreCase(p.getEstado())) { %>
                        <span class="badge bg-warning text-dark">Pendiente</span>
                        <% } else { %>
                        <span class="badge bg-success">Devuelto</span>
                        <% } %>
                    </td>

                    <td class="text-center">
                        <%-- Botón DEVOLVER solo si está pendiente --%>
                        <% if("pendiente".equalsIgnoreCase(p.getEstado())) { %>
                        <a href="${pageContext.request.contextPath}/prestamos?action=devolver&idPrestamo=<%= p.getId() %>&idLibro=<%= p.getIdLibro() %>"
                           class="btn btn-sm btn-outline-success"
                           onclick="return confirm('¿Confirmar devolución de: <%= p.getTituloLibro() %>?');">
                            🔄 Devolver
                        </a>
                        <% } else { %>
                        <span class="text-muted small">Finalizado</span>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="<%= esAdmin ? 7 : 6 %>" class="text-center p-4">
                        <p class="text-muted">No se encontraron registros de préstamos.</p>
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