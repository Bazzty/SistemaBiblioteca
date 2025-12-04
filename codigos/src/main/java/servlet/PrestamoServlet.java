package servlet;

import dao.PrestamoDAO;
import model.Prestamo;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PrestamoServlet", urlPatterns = {"/prestamos"})
public class PrestamoServlet extends HttpServlet {

    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "mis_prestamos";

        switch (action) {
            case "mis_prestamos":
                // Si es usuario normal, ve solo los suyos. Si es admin, ve todos (idUsuario=0 para todos)
                int idUsuarioBusqueda = "admin".equalsIgnoreCase(usuario.getRol()) ? 0 : usuario.getId();

                List<Prestamo> lista = prestamoDAO.listarPrestamos(idUsuarioBusqueda);
                request.setAttribute("listaPrestamos", lista);
                request.getRequestDispatcher("views/misPrestamos.jsp").forward(request, response);
                break;

            case "devolver":
                // Lógica para devolver libro
                int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
                int idLibro = Integer.parseInt(request.getParameter("idLibro"));

                boolean exito = prestamoDAO.devolverLibro(idPrestamo, idLibro);

                // Redirigir para recargar la tabla
                response.sendRedirect("prestamos?action=mis_prestamos&msg=" + (exito ? "DevolucionExitosa" : "ErrorDevolucion"));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // SOLICITAR PRÉSTAMO
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario != null) {
            int idLibro = Integer.parseInt(request.getParameter("idLibro"));
            int idUsuario = usuario.getId();

            boolean resultado = prestamoDAO.registrarPrestamo(idUsuario, idLibro);

            if (resultado) {
                // Éxito: volvemos a la lista de libros con mensaje
                response.sendRedirect("libros?msg=PrestamoExitoso");
            } else {
                // Fallo (sin stock, etc)
                response.sendRedirect("libros?msg=SinStock");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}