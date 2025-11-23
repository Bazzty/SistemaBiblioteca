package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Obtener la sesión actual si existe
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. Destruir la sesión (Borrar usuario logueado)
            session.invalidate();
        }

        // 3. Redirigir al Login (index.jsp)
        response.sendRedirect("index.jsp");
    }
}