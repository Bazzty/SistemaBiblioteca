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

    // 1. VALIDAR LOGIN (Ya lo tenías, lo dejamos igual)
    public Usuario validarLogin(String email, String password) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    // 2. INSERTAR USUARIO (NUEVO) - Create
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol()); // 'admin' o 'usuario'

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. LISTAR USUARIOS (Ya lo tenías, lo dejamos igual) - Read
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

    // 4. ACTUALIZAR USUARIO (NUEVO) - Update
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, email=?, password=?, rol=? WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. ELIMINAR USUARIO (NUEVO) - Delete
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. OBTENER POR ID (NUEVO) - Para cargar el formulario de edición
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getString("rol"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 7. VERIFICAR SI EXISTE EMAIL (NUEVO)
    public boolean existeEmail(String email) {
        String sql = "SELECT id_usuario FROM usuario WHERE email = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                // Si rs.next() es true, significa que encontró al menos un usuario con ese email
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 8. VERIFICAR EMAIL EXCEPTO UN ID (Para evitar duplicados al editar)
    public boolean existeEmail(String email, int idExcluir) {
        // Buscamos si hay ALGUIEN con ese email, pero que NO sea yo (id_usuario != ?)
        String sql = "SELECT id_usuario FROM usuario WHERE email = ? AND id_usuario != ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setInt(2, idExcluir);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si encuentra algo, es que el email ya lo usa OTRO
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}