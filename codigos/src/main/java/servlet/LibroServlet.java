package servlet;

import dao.LibroDAO;
import model.Libro;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LibroServlet", urlPatterns = {"/libros"})
public class LibroServlet extends HttpServlet {

    private LibroDAO libroDAO = new LibroDAO();

    // GET: Para mostrar la lista o el formulario
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "nuevo":
                request.getRequestDispatcher("views/formLibro.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Libro libro = libroDAO.obtenerPorId(idEditar);
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("views/formLibro.jsp").forward(request, response);
                break;
            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                libroDAO.eliminarLibro(idEliminar);
                response.sendRedirect("libros"); // Recargar lista
                break;
            default: // "listar"
                List<Libro> lista = libroDAO.listarLibros();
                request.setAttribute("listaLibros", lista);
                request.getRequestDispatcher("views/listarLibros.jsp").forward(request, response);
                break;
        }
    }

    // POST: Para guardar o actualizar
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Recibir datos
            String idStr = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            // Usamos try-catch por si el usuario deja vacío o pone letras
            int idAutor = Integer.parseInt(request.getParameter("idAutor"));
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            // 2. Crear el objeto Libro (para guardarlo o devolverlo si hay error)
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setIdAutor(idAutor);
            libro.setIdCategoria(idCategoria);
            libro.setStock(stock);

            if (idStr != null && !idStr.isEmpty()) {
                libro.setId(Integer.parseInt(idStr));
            }

            // --- VALIDACIONES DE EXISTENCIA ---
            boolean existeAutor = libroDAO.existeAutor(idAutor);
            boolean existeCategoria = libroDAO.existeCategoria(idCategoria);

            if (!existeAutor || !existeCategoria) {
                // Construimos el mensaje de error
                StringBuilder errorMsg = new StringBuilder();
                if (!existeAutor) errorMsg.append("El Autor con ID ").append(idAutor).append(" no existe. ");
                if (!existeCategoria) errorMsg.append("La Categoría con ID ").append(idCategoria).append(" no existe.");

                // Enviamos el error y el libro de vuelta al formulario
                request.setAttribute("error", errorMsg.toString());
                request.setAttribute("libro", libro);
                request.getRequestDispatcher("views/formLibro.jsp").forward(request, response);
                return; // ¡IMPORTANTE! Detener aquí para no guardar
            }

            // 3. Guardar en Base de Datos (Si pasó las validaciones)
            if (idStr == null || idStr.isEmpty()) {
                libroDAO.insertarLibro(libro); // Nuevo
            } else {
                libroDAO.actualizarLibro(libro); // Editar
            }

            response.sendRedirect("libros");

        } catch (NumberFormatException e) {
            // Capturar error si ingresan letras en campos numéricos
            request.setAttribute("error", "Error: Los campos ID Autor, Categoría y Stock deben ser números válidos.");
            // Intentar recuperar lo que se pueda para no limpiar todo el form (opcional)
            request.getRequestDispatcher("views/formLibro.jsp").forward(request, response);
        }
    }
}