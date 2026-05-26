package dao;

import conexion.ConexionBD;
import modelo.Calificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Calificacion_Dao {

    public void inicializarCalificacion(int idVideo) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT IGNORE INTO calificacion (idVideo) VALUES (?)");
            ps.setInt(1, idVideo);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error inicializar calificacion: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public Calificacion obtenerPorVideo(int idVideo) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return null;
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM calificacion WHERE idVideo = ?");
            ps.setInt(1, idVideo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Calificacion c = new Calificacion();
                c.setId(rs.getInt("id"));
                c.setIdVideo(rs.getInt("idVideo"));
                c.setExcelente(rs.getInt("excelente"));
                c.setBuena(rs.getInt("buena"));
                c.setRegular(rs.getInt("regular"));
                c.setMala(rs.getInt("mala"));
                rs.close(); ps.close();
                return c;
            }
            rs.close(); ps.close();
        } catch (Exception e) {
            System.out.println("Error obtener calificacion: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return null;
    }

    public void votar(int idVideo, String tipo) {
        // tipo: "excelente", "buena", "regular", "mala"
        inicializarCalificacion(idVideo);
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            String sql = "UPDATE calificacion SET " + tipo + " = " + tipo
                       + " + 1 WHERE idVideo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVideo);
            ps.executeUpdate();
            ps.close();
            System.out.println("Voto registrado: " + tipo);
        } catch (Exception e) {
            System.out.println("Error votar: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}