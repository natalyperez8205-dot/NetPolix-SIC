package dao;

import modelo.Video;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Video_Dao {

    // -------------------------------------------------------
    // GUARDAR VIDEO
    // -------------------------------------------------------
    public void guardarVideo(Video video) {
        try {
            Connection con = ConexionBD.getConexion();
            if (con == null) {
                System.out.println("Sin conexión a BD");
                return;
            }

            String sql = "INSERT INTO video (isan, tituloOriginal, anio, duracion, categoria, precio) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // ISAN único basado en timestamp
            String isan = "ISAN" + System.currentTimeMillis();
            ps.setString(1, isan);
            ps.setString(2, video.getTituloOriginal());

            int anio = java.util.Calendar.getInstance()
                           .get(java.util.Calendar.YEAR);
            ps.setInt(3, anio);
            ps.setInt(4, video.getDuracion());
            ps.setString(5, video.getCategoria());
            ps.setDouble(6, video.getPrecio());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                video.setId(rs.getInt(1));
            }

            rs.close();
            ps.close();
            con.close();

            System.out.println("Video guardado en BD: "
                    + video.getTituloOriginal());

        } catch (Exception e) {
            System.out.println("Error al guardar video: "
                    + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // LISTAR TODOS LOS VIDEOS
    // -------------------------------------------------------
    public List<Video> listarVideos() {
        List<Video> resultado = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return resultado;

            String sql = "SELECT id, tituloOriginal, duracion, "
                       + "categoria, precio FROM video";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id"));
                v.setTituloOriginal(rs.getString("tituloOriginal"));
                v.setDuracion(rs.getInt("duracion"));
                v.setCategoria(rs.getString("categoria"));
                v.setPrecio(rs.getDouble("precio"));
                resultado.add(v);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al listar videos: "
                    + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) { }
        }
        return resultado;
    }

    // -------------------------------------------------------
    // BUSCAR POR ID — ahora consulta la BD correctamente
    // -------------------------------------------------------
    public Video buscarPorId(int id) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return null;

            String sql = "SELECT id, tituloOriginal, duracion, "
                       + "categoria, precio FROM video WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id"));
                v.setTituloOriginal(rs.getString("tituloOriginal"));
                v.setDuracion(rs.getInt("duracion"));
                v.setCategoria(rs.getString("categoria"));
                v.setPrecio(rs.getDouble("precio"));
                rs.close();
                ps.close();
                return v;
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al buscar video: "
                    + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) { }
        }
        return null;
    }

    // -------------------------------------------------------
    // ELIMINAR VIDEO
    // -------------------------------------------------------
    public void eliminarVideo(int id) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;

            String sql = "DELETE FROM video WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Video eliminado id=" + id);

        } catch (SQLException e) {
            System.out.println("Error al eliminar video: "
                    + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) { }
        }
    }
}