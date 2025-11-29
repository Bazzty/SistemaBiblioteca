package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Asegúrate de que el nombre de la BD sea correcto (Mayúscula/Minúscula)
    private static final String URL = "jdbc:mysql://localhost:3307/Biblioteca?serverTimezone=UTC";
    // TUS DATOS DE WINDOWS
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection getConexion() throws SQLException {
        try {
            // ESTA ES LA LÍNEA MÁGICA QUE ARREGLA EL ERROR "NO SUITABLE DRIVER"
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USUARIO, CLAVE);

        } catch (ClassNotFoundException e) {
            System.out.println("!!! ERROR: NO SE ENCUENTRA EL DRIVER MYSQL !!!");
            System.out.println("Debes agregar el .jar a la carpeta WEB-INF/lib");
            e.printStackTrace();
            throw new SQLException("Error de Driver MySQL");
        }
    }
}
