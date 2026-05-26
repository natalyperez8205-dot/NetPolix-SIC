package dao;

import conexion.ConexionBD;
import modelo.Video;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Carrito_Dao {

    public void agregarAlCarrito(int idUsuario, Video video) {
        if (video == null) return;
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;

            String sqlCheck = "SELECT id, cantidad FROM carrito WHERE id_usuario = ? AND id_video = ?";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, idUsuario);
            psCheck.setInt(2, video.getId());
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int nuevaCantidad = rs.getInt("cantidad") + 1;
                int idCarrito = rs.getInt("id");
                rs.close(); psCheck.close();
                PreparedStatement psUpdate = con.prepareStatement(
                    "UPDATE carrito SET cantidad = ? WHERE id = ?");
                psUpdate.setInt(1, nuevaCantidad);
                psUpdate.setInt(2, idCarrito);
                psUpdate.executeUpdate();
                psUpdate.close();
            } else {
                rs.close(); psCheck.close();
                PreparedStatement psInsert = con.prepareStatement(
                    "INSERT INTO carrito (id_usuario, id_video, cantidad) VALUES (?, ?, 1)");
                psInsert.setInt(1, idUsuario);
                psInsert.setInt(2, video.getId());
                psInsert.executeUpdate();
                psInsert.close();
            }
            System.out.println("Agregado al carrito: " + video.getTituloOriginal());
        } catch (SQLException e) {
            System.out.println("Error carrito: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public Map<Integer, Integer> obtenerCarrito(int idUsuario) {
        Map<Integer, Integer> carrito = new HashMap<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return carrito;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_video, cantidad FROM carrito WHERE id_usuario = ?");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carrito.put(rs.getInt("id_video"), rs.getInt("cantidad"));
            }
            rs.close(); ps.close();
        } catch (SQLException e) {
            System.out.println("Error obtener carrito: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return carrito;
    }

    public Map<Integer, Map<Integer, Integer>> obtenerTodosLosCarritos() {
        Map<Integer, Map<Integer, Integer>> datos = new HashMap<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return datos;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id_usuario, id_video, cantidad FROM carrito");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                int idVideo   = rs.getInt("id_video");
                int cantidad  = rs.getInt("cantidad");
                datos.computeIfAbsent(idUsuario, k -> new HashMap<>())
                     .put(idVideo, cantidad);
            }
            rs.close(); ps.close();
        } catch (SQLException e) {
            System.out.println("Error todos los carritos: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return datos;
    }

    public void eliminarDelCarrito(int idUsuario, int idVideo) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM carrito WHERE id_usuario = ? AND id_video = ?");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idVideo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error eliminar del carrito: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public void vaciarCarrito(int idUsuario) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM carrito WHERE id_usuario = ?");
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error vaciar carrito: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}