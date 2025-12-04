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
import java.util.List;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "registro": // Para el botón "Crear cuenta" del Login
                request.setAttribute("esRegistroPublico", true); // Marca especial
                request.getRequestDispatcher("views/formUsuario.jsp").forward(request, response);
                break;
            case "nuevo": // Para el Admin creando usuarios
                request.getRequestDispatcher("views/formUsuario.jsp").forward(request, response);
                break;
            case "editar": // Para el Admin editando
                int id = Integer.parseInt(request.getParameter("id"));
                Usuario usuario = usuarioDAO.obtenerPorId(id);
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("views/formUsuario.jsp").forward(request, response);
                break;
            case "eliminar": // Para el Admin borrando
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                usuarioDAO.eliminarUsuario(idEliminar);
                response.sendRedirect("usuarios");
                break;
            default: // "listar" (Solo Admin debería ver esto)
                List<Usuario> lista = usuarioDAO.listarUsuarios();
                request.setAttribute("listaUsuarios", lista);
                request.getRequestDispatcher("views/listarUsuarios.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");

        if (rol == null || rol.isEmpty()) rol = "usuario";

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword(password);
        u.setRol(rol);

        // --- BLOQUE LÓGICO COMPLETO ---

        // CASO 1: USUARIO NUEVO (Crear)
        if (idStr == null || idStr.isEmpty() || "0".equals(idStr)) {

            if (usuarioDAO.existeEmail(email)) {
                mostrarError(request, response, u, "El correo " + email + " ya está registrado.");
                return;
            }
            usuarioDAO.insertarUsuario(u);

        }
        // CASO 2: USUARIO EXISTENTE (Editar)
        else {
            int id = Integer.parseInt(idStr);
            u.setId(id);

            // Validamos: ¿El email lo usa OTRO usuario?
            if (usuarioDAO.existeEmail(email, id)) {
                mostrarError(request, response, u, "El correo " + email + " ya pertenece a otra cuenta.");
                return;
            }
            usuarioDAO.actualizarUsuario(u);
        }

        // Redirección Final
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp?mensaje=registrado");
        } else {
            response.sendRedirect("usuarios");
        }
    }

    // Método auxiliar para no repetir código al mostrar error
    private void mostrarError(HttpServletRequest request, HttpServletResponse response, Usuario u, String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);
        request.setAttribute("usuario", u);

        // Mantener la marca de registro público si corresponde
        String rol = u.getRol();
        boolean esRegistroPublico = "usuario".equals(rol) && (request.getSession(false) == null || request.getSession(false).getAttribute("usuarioLogueado") == null);
        if(esRegistroPublico) request.setAttribute("esRegistroPublico", true);

        request.getRequestDispatcher("views/formUsuario.jsp").forward(request, response);
    }

}