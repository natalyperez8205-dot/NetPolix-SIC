package dao;

import modelo.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Video_Dao {

    private static final List<Video> videos = new ArrayList<>();
    private static int nextId = 1;

    public void guardarVideo(Video video) {
        try {
            Connection con = ConexionBD.getConexion();
            if (con != null) {
                String sql = "INSERT INTO video (isan, tituloOriginal, anio, duracion, categoria, precio) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                String isan = "ISAN" + System.currentTimeMillis();
                ps.setString(1, isan);
                ps.setString(2, video.getTituloOriginal());
                int anio = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
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

                videos.add(video);
                System.out.println("Video insertado en BD: " + video.getTituloOriginal());
                return;
            } else {
                System.out.println("No hay conexión a la BD, guardando en memoria.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar video en BD: " + e.getMessage());
        }

        // Fallback a almacenamiento en memoria
        video.setId(nextId++);
        videos.add(video);
        System.out.println("Video guardado en memoria: " + video.getTituloOriginal());
    }

    public List<Video> listarVideos() {
        return Collections.unmodifiableList(videos);
    }

    public Video buscarPorId(int id) {
        for (Video video : videos) {
            if (video.getId() == id) {
                return video;
            }
        }
        return null;
    }
}
