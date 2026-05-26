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
            System.out.println("Error init calificacion: " + e.getMessage());
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

    // Verifica si el usuario ya votó este video
    public boolean yaVoto(int idUsuario, int idVideo) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return false;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id FROM calificacion_usuario " +
                "WHERE idUsuario = ? AND idVideo = ?");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idVideo);
            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            rs.close(); ps.close();
            return existe;
        } catch (Exception e) {
            // tabla no existe aun
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    // Obtiene el voto anterior del usuario
    public String obtenerVotoAnterior(int idUsuario, int idVideo) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return null;
            PreparedStatement ps = con.prepareStatement(
                "SELECT tipo FROM calificacion_usuario " +
                "WHERE idUsuario = ? AND idVideo = ?");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idVideo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                rs.close(); ps.close();
                return tipo;
            }
            rs.close(); ps.close();
        } catch (Exception e) {
            System.out.println("Error voto anterior: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return null;
    }

    public void votar(int idUsuario, int idVideo, String tipo) {
        inicializarCalificacion(idVideo);
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;

            String votoAnterior = obtenerVotoAnterior(idUsuario, idVideo);

            if (votoAnterior != null) {
                // Quitar voto anterior
                PreparedStatement psQuitar = con.prepareStatement(
                    "UPDATE calificacion SET " + votoAnterior + " = "
                    + votoAnterior + " - 1 WHERE idVideo = ?");
                psQuitar.setInt(1, idVideo);
                psQuitar.executeUpdate();
                psQuitar.close();

                // Actualizar registro de voto
                PreparedStatement psUpdate = con.prepareStatement(
                    "UPDATE calificacion_usuario SET tipo = ? " +
                    "WHERE idUsuario = ? AND idVideo = ?");
                psUpdate.setString(1, tipo);
                psUpdate.setInt(2, idUsuario);
                psUpdate.setInt(3, idVideo);
                psUpdate.executeUpdate();
                psUpdate.close();

            } else {
                // Insertar nuevo registro de voto
                PreparedStatement psInsert = con.prepareStatement(
                    "INSERT INTO calificacion_usuario " +
                    "(idUsuario, idVideo, tipo) VALUES (?, ?, ?)");
                psInsert.setInt(1, idUsuario);
                psInsert.setInt(2, idVideo);
                psInsert.setString(3, tipo);
                psInsert.executeUpdate();
                psInsert.close();
            }

            // Sumar nuevo voto
            PreparedStatement psSumar = con.prepareStatement(
                "UPDATE calificacion SET " + tipo + " = "
                + tipo + " + 1 WHERE idVideo = ?");
            psSumar.setInt(1, idVideo);
            psSumar.executeUpdate();
            psSumar.close();

            System.out.println("Voto registrado: " + tipo);

        } catch (Exception e) {
            System.out.println("Error votar: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}