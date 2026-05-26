package dao;

import modelo.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Video_Dao {

    private static final List<Video> videos = new ArrayList<>();
    private static int nextId = 1;

    public void guardarVideo(Video video) {
        try {
            Connection con = ConexionBD.getConexion();
            if (con != null) {
                // Try inserting including optional columns if they exist
                String sqlWithExtras = "INSERT INTO video (isan, tituloOriginal, anio, duracion, categoria, precio, actores, edadRestriccion, idioma) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = null;
                try {
                    ps = con.prepareStatement(sqlWithExtras, PreparedStatement.RETURN_GENERATED_KEYS);
                    String isan = "ISAN" + System.currentTimeMillis();
                    ps.setString(1, isan);
                    ps.setString(2, video.getTituloOriginal());
                    int anio = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                    ps.setInt(3, anio);
                    ps.setInt(4, video.getDuracion());
                    ps.setString(5, video.getCategoria());
                    ps.setDouble(6, video.getPrecio());
                    ps.setString(7, video.getActores());
                    ps.setString(8, video.getEdadRestriccion());
                    ps.setString(9, video.getIdioma());

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
                } catch (SQLException ex) {
                    // If the DB schema doesn't have the extra columns, fallback to simple insert
                    if (ps != null) {
                        try { ps.close(); } catch (Exception ignore) {}
                    }
                }

                // Fallback simple insert
                String sql = "INSERT INTO video (isan, tituloOriginal, anio, duracion, categoria, precio) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps2 = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                String isan2 = "ISAN" + System.currentTimeMillis();
                ps2.setString(1, isan2);
                ps2.setString(2, video.getTituloOriginal());
                int anio2 = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                ps2.setInt(3, anio2);
                ps2.setInt(4, video.getDuracion());
                ps2.setString(5, video.getCategoria());
                ps2.setDouble(6, video.getPrecio());

                ps2.executeUpdate();
                ResultSet rs2 = ps2.getGeneratedKeys();
                if (rs2.next()) {
                    video.setId(rs2.getInt(1));
                }
                rs2.close();
                ps2.close();
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
        List<Video> resultado = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) {
                return Collections.unmodifiableList(videos);
            }

            // Try to select possible columns if the schema contains them
            String sqlWithActores = "SELECT id, tituloOriginal, duracion, categoria, precio, actores, edadRestriccion, idioma FROM video";
            try (PreparedStatement ps = con.prepareStatement(sqlWithActores);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Video v = new Video();
                    v.setId(rs.getInt("id"));
                    v.setTituloOriginal(rs.getString("tituloOriginal"));
                    v.setDuracion(rs.getInt("duracion"));
                    v.setCategoria(rs.getString("categoria"));
                    v.setPrecio(rs.getDouble("precio"));
                    try { v.setActores(rs.getString("actores")); } catch (Exception ex) { }
                    try { v.setEdadRestriccion(rs.getString("edadRestriccion")); } catch (Exception ex) { }
                    try { v.setIdioma(rs.getString("idioma")); } catch (Exception ex) { }
                    resultado.add(v);
                }
                return resultado;
            } catch (SQLException e) {
                // Fallback to a minimal select if the extra columns do not exist
            }

            String sql = "SELECT id, tituloOriginal, duracion, categoria, precio FROM video";
            try (PreparedStatement ps2 = con.prepareStatement(sql);
                 ResultSet rs2 = ps2.executeQuery()) {
                while (rs2.next()) {
                    Video v = new Video();
                    v.setId(rs2.getInt("id"));
                    v.setTituloOriginal(rs2.getString("tituloOriginal"));
                    v.setDuracion(rs2.getInt("duracion"));
                    v.setCategoria(rs2.getString("categoria"));
                    v.setPrecio(rs2.getDouble("precio"));
                    resultado.add(v);
                }
                return resultado;
            }
        } catch (Exception e) {
            System.out.println("Error al listar videos desde BD: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) { }
        }

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
