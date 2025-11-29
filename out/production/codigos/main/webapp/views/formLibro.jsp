<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Libro" %>
<%
    // Recuperamos el libro si viene para editar (puede ser null si es nuevo)
    Libro libro = (Libro) request.getAttribute("libro");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= (libro != null) ? "Editar Libro" : "Nuevo Libro" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4><%= (libro != null) ? "Editar Libro" : "Registrar Nuevo Libro" %></h4>
                </div>
                <div class="card-body">

                    <form action="../libros" method="post">

                        <input type="hidden" name="id" value="<%= (libro != null) ? libro.getId() : "" %>">

                        <div class="mb-3">
                            <label>Título del Libro:</label>
                            <input type="text" name="titulo" class="form-control" required
                                   value="<%= (libro != null) ? libro.getTitulo() : "" %>">
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label>ID Autor:</label>
                                <input type="number" name="idAutor" class="form-control" required
                                       value="<%= (libro != null) ? libro.getIdAutor() : "" %>">
                                <small class="text-muted">Ingresa el ID del autor (Ej: 1)</small>
                            </div>

                            <div class="col-md-6 mb-3">
                                <label>ID Categoría:</label>
                                <input type="number" name="idCategoria" class="form-control" required
                                       value="<%= (libro != null) ? libro.getIdCategoria() : "" %>">
                                <small class="text-muted">Ingresa el ID de categoría (Ej: 1)</small>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label>Stock:</label>
                            <input type="number" name="stock" class="form-control" required min="0"
                                   value="<%= (libro != null) ? libro.getStock() : "" %>">
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">
                                <%= (libro != null) ? "Actualizar" : "Guardar" %>
                            </button>
                            <a href="../libros" class="btn btn-secondary">Cancelar</a>
                        </div>

                    </form> </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>