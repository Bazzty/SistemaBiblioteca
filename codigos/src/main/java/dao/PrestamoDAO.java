package dao;

import model.Prestamo;
import util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // 1. REGISTRAR UN PRÉSTAMO (Transacción: Insertar Prestamo + Descontar Stock)
    public boolean registrarPrestamo(int idUsuario, int idLibro) {
        Connection con = null;
        PreparedStatement psInsertar = null;
        PreparedStatement psStock = null;

        try {
            con = ConexionBD.getConexion();
            // Desactivamos el auto-commit para manejar la transacción manualmente
            con.setAutoCommit(false);

            // A) Verificar si hay stock disponible primero
            if (!hayStock(idLibro, con)) {
                return false; // No hay stock, cancelamos
            }

            // B) Insertar el préstamo
            String sqlInsert = "INSERT INTO prestamo (id_usuario, id_libro, fecha_prestamo, estado) VALUES (?, ?, CURDATE(), 'pendiente')";
            psInsertar = con.prepareStatement(sqlInsert);
            psInsertar.setInt(1, idUsuario);
            psInsertar.setInt(2, idLibro);
            psInsertar.executeUpdate();

            // C) Descontar Stock del Libro
            String sqlUpdate = "UPDATE libro SET stock = stock - 1 WHERE id_libro = ?";
            psStock = con.prepareStatement(sqlUpdate);
            psStock.setInt(1, idLibro);
            psStock.executeUpdate();

            // Si todo salió bien, confirmamos los cambios
            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback(); // Si falla algo, deshacemos todo
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (psInsertar != null) psInsertar.close();
                if (psStock != null) psStock.close();
                if (con != null) con.close();
            } catch (Exception e) {}
        }
    }

    // Método auxiliar para verificar stock
    private boolean hayStock(int idLibro, Connection con) throws SQLException {
        String sql = "SELECT stock FROM libro WHERE id_libro = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock") > 0;
            }
        }
        return false;
    }

    // 2. LISTAR PRÉSTAMOS (Para el admin o historial del usuario)
    public List<Prestamo> listarPrestamos(int idUsuario) {
        List<Prestamo> lista = new ArrayList<>();
        // Esta consulta une tablas para obtener el nombre del libro y del usuario
        String sql = "SELECT p.*, l.titulo, u.nombre " +
                "FROM prestamo p " +
                "JOIN libro l ON p.id_libro = l.id_libro " +
                "JOIN usuario u ON p.id_usuario = u.id_usuario ";

        // Si mandamos un ID de usuario > 0, filtramos por ese usuario.
        if (idUsuario > 0) {
            sql += "WHERE p.id_usuario = ? ";
        }

        sql += "ORDER BY p.fecha_prestamo DESC";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (idUsuario > 0) {
                ps.setInt(1, idUsuario);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                p.setEstado(rs.getString("estado"));

                // Nombres extra
                p.setTituloLibro(rs.getString("titulo"));
                p.setNombreUsuario(rs.getString("nombre"));

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. DEVOLVER LIBRO (Este es el que faltaba)
    public boolean devolverLibro(int idPrestamo, int idLibro) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false); // Transacción

            // A) Actualizar estado del préstamo
            String sqlPrestamo = "UPDATE prestamo SET estado = 'devuelto', fecha_devolucion = CURDATE() WHERE id_prestamo = ?";
            PreparedStatement psP = con.prepareStatement(sqlPrestamo);
            psP.setInt(1, idPrestamo);
            psP.executeUpdate();

            // B) Aumentar stock del libro
            String sqlLibro = "UPDATE libro SET stock = stock + 1 WHERE id_libro = ?";
            PreparedStatement psL = con.prepareStatement(sqlLibro);
            psL.setInt(1, idLibro);
            psL.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { if(con!=null) con.rollback(); } catch(Exception ex){}
            return false;
        } finally {
            try { if(con!=null) con.close(); } catch(Exception ex){}
        }
    }
}