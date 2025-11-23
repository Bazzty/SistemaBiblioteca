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

    // METODO 1: VALIDAR LOGIN (Este es el que faltaba para entrar)
    public Usuario validarLogin(String email, String password) {
        Usuario u = null;
        // Corrección: Tabla 'usuario' (singular) y columnas correctas
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    // Corrección: La columna en tu BD es 'id_usuario'
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

    // METODO 2: LISTAR (Para mostrar usuarios más adelante)
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario"; // Corrección: singular

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario")); // Corrección: id_usuario
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