package servlet;

import dao.UsuarioDAO;
import model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// HEMOS BORRADO LA LÍNEA @WebServlet PORQUE YA LO CONFIGURAMOS EN web.xml
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recibimos los datos
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // 2. Consultamos al DAO
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.validarLogin(email, pass);

        if (usuario != null) {
            // 3. ÉXITO
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);

            // Redirección según ROL
            if ("admin".equalsIgnoreCase(usuario.getRol())) {
                System.out.println("Login exitoso: Redirigiendo a Panel ADMIN");
                response.sendRedirect("jsp/MenuAdmin.jsp");
            }
            else {
                System.out.println("Login exitoso: Redirigiendo a Panel USUARIO");
                response.sendRedirect("jsp/MenuUsuario.jsp");
            }

        } else {
            // 4. ERROR
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}