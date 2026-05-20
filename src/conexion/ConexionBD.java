package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = 
        "jdbc:mysql://localhost:3306/netpolix";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "netpolix123";
    
    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                URL, USUARIO, CONTRASENA);
            System.out.println("Conexión exitosa!");
            return con;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " 
                + e.getMessage());
        }
        return null;
    }
    
    public static void main(String[] args) {
        getConexion();
    }
}