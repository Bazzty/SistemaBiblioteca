package dao;

import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteDAO {

    // 1. Contar Total de Libros
    public int contarLibros() {
        return contar("SELECT COUNT(*) FROM libro");
    }

    // 2. Contar Total de Usuarios
    public int contarUsuarios() {
        return contar("SELECT COUNT(*) FROM usuario");
    }

    // 3. Contar Préstamos Activos (Pendientes)
    // (Este funcionará cuando tengas la tabla prestamo llena)
    public int contarPrestamosPendientes() {
        return contar("SELECT COUNT(*) FROM prestamo WHERE estado = 'pendiente'");
    }

    // Método auxiliar privado para no repetir código
    private int contar(String sql) {
        int total = 0;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}