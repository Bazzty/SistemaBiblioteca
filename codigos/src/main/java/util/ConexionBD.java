package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?serverTimezone=UTC";

    private static final String USUARIO = "root";
    private static final String CLAVE = "Benjamin2002910";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public static void main(String[] args) {
        try {
            getConexion();
            System.out.println("Conexión exitosa!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

