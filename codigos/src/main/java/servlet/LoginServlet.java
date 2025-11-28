package servlet;

import dao.UsuarioDAO;
import model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Esta anotación conecta el formulario HTML con este código Java
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recibimos los datos del formulario
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // 2. Preguntamos al DAO si el usuario existe
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.validarLogin(email, pass);

        if (usuario != null) {
            // 3. ÉXITO: Creamos la sesión y guardamos al usuario
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);

            // Redirigimos a la página principal (que crearemos en un momento)
            response.sendRedirect("views/home.jsp");
        } else {
            // 4. ERROR: Usuario no encontrado
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            // Devolvemos al usuario al login
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}