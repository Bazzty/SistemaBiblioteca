package servlet;

import dao.ReporteDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/reportes"})
public class ReporteServlet extends HttpServlet {

    private ReporteDAO reporteDAO = new ReporteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener datos
        int totalLibros = reporteDAO.contarLibros();
        int totalUsuarios = reporteDAO.contarUsuarios();
        int prestamosPendientes = reporteDAO.contarPrestamosPendientes();

        // 2. Pasarlos a la vista
        request.setAttribute("totalLibros", totalLibros);
        request.setAttribute("totalUsuarios", totalUsuarios);
        request.setAttribute("prestamosPendientes", prestamosPendientes);

        // 3. Mostrar la página
        request.getRequestDispatcher("views/reportes.jsp").forward(request, response);
    }
}