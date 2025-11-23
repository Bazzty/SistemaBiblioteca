package dao;

import model.Usuario;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // METODO 1: VALIDAR LOGIN (CON DEBUG PARA ENCONTRAR EL ERROR)
    public Usuario validarLogin(String email, String password) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";

        // --- CHIVATOS DE DEBUG ---
        System.out.println("--- [DEBUG] INICIO VALIDAR LOGIN ---");
        System.out.println("1. Intentando buscar email: " + email);
        System.out.println("2. Con contraseña: " + password);

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("3. ¡Conexión a Base de Datos EXITOSA!");

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("4. Resultado: ¡USUARIO ENCONTRADO!");
                    u = new Usuario();
                    u.setId(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getString("rol"));
                    System.out.println("5. Rol detectado: " + u.getRol());
                } else {
                    System.out.println("4. Resultado: NO se encontró ningún usuario con esos datos.");
                }
            }
        } catch (SQLException e) {
            System.out.println("!!! [ERROR GRAVE] FALLÓ LA CONEXIÓN O LA CONSULTA !!!");
            System.out.println("Mensaje de error: " + e.getMessage());
            e.printStackTrace(); // Esto nos mostrará el error rojo en la consola
        }
        System.out.println("--- [DEBUG] FIN ---");
        return u;
    }

    // METODO 2: LISTAR USUARIOS
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}