<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Biblioteca</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">

<div class="card p-4 shadow" style="width: 400px;">
    <h3 class="text-center mb-4">Biblioteca Universitaria</h3>

    <%-- Mensaje de Error (si el login falla) --%>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <%-- Mensaje de Éxito (si se registra correctamente) --%>
    <% if (request.getParameter("mensaje") != null && request.getParameter("mensaje").equals("registrado")) { %>
    <div class="alert alert-success">
        ¡Registro exitoso! Ahora puedes ingresar.
    </div>
    <% } %>

    <form action="login" method="post">
        <div class="mb-3">
            <label>Email:</label>
            <input type="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Contraseña:</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary w-100 mb-3">Ingresar</button>

        <div class="text-center">
            <a href="usuarios?action=registro" class="text-decoration-none">
                ¿No tienes cuenta? <strong>Créate una aquí</strong>
            </a>
        </div>
    </form>
</div>

</body>
</html>