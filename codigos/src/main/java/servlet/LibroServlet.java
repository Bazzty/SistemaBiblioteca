package main.java.servlet;

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

        String idStr = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        int idAutor = Integer.parseInt(request.getParameter("idAutor"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIdAutor(idAutor);
        libro.setIdCategoria(idCategoria);
        libro.setStock(stock);

        if (idStr == null || idStr.isEmpty()) {
            libroDAO.insertarLibro(libro); // Nuevo
        } else {
            libro.setId(Integer.parseInt(idStr));
            libroDAO.actualizarLibro(libro); // Editar
        }

        response.sendRedirect("libros");
    }
}