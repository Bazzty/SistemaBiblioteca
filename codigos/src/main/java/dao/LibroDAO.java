package dao;

import model.Libro;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // 1. INSERTAR (Create)
    public boolean insertarLibro(Libro libro) {
        String sql = "INSERT INTO libro (titulo, id_autor, id_categoria, stock) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getIdAutor());
            ps.setInt(3, libro.getIdCategoria());
            ps.setInt(4, libro.getStock());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. LISTAR (Read) - CON NOMBRES DE AUTOR Y CATEGORÍA
    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();

        // Usamos JOIN para traer el nombre del autor y de la categoría
        String sql = "SELECT l.*, a.nombre AS nombre_autor, c.nombre AS nombre_categoria " +
                "FROM libro l " +
                "JOIN autor a ON l.id_autor = a.id_autor " +
                "JOIN categoria c ON l.id_categoria = c.id_categoria";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setIdAutor(rs.getInt("id_autor"));
                l.setIdCategoria(rs.getInt("id_categoria"));
                l.setStock(rs.getInt("stock"));

                // --- AQUÍ GUARDAMOS LOS NOMBRES QUE TRAJIMOS DE LA BD ---
                l.setNombreAutor(rs.getString("nombre_autor"));
                l.setNombreCategoria(rs.getString("nombre_categoria"));

                lista.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. ACTUALIZAR (Update)
    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libro SET titulo=?, id_autor=?, id_categoria=?, stock=? WHERE id_libro=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getIdAutor());
            ps.setInt(3, libro.getIdCategoria());
            ps.setInt(4, libro.getStock());
            ps.setInt(5, libro.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. ELIMINAR (Delete)
    public boolean eliminarLibro(int id) {
        String sql = "DELETE FROM libro WHERE id_libro=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Extra: Obtener por ID (Útil para cargar el formulario de edición)
    public Libro obtenerPorId(int id) {
        String sql = "SELECT * FROM libro WHERE id_libro=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Libro(
                            rs.getInt("id_libro"),
                            rs.getString("titulo"),
                            rs.getInt("id_autor"),
                            rs.getInt("id_categoria"),
                            rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. VALIDAR EXISTENCIA DE AUTOR
    public boolean existeAutor(int idAutor) {
        String sql = "SELECT id_autor FROM autor WHERE id_autor = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Retorna true si encuentra el ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. VALIDAR EXISTENCIA DE CATEGORÍA
    public boolean existeCategoria(int idCategoria) {
        String sql = "SELECT id_categoria FROM categoria WHERE id_categoria = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Retorna true si encuentra el ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}