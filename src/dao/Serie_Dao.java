package dao;

import conexion.ConexionBD;
import modelo.Serie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Serie_Dao {

    public void guardarSerie(Serie serie) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            // idVideo = 1 como placeholder; ajustar si manejan video relacionado
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO serie (titulo, temporada, idVideo) VALUES (?, ?, 1)");
            ps.setString(1, serie.getTitulo());
            ps.setInt(2, serie.getTemporada());
            ps.executeUpdate();
            ps.close();
            System.out.println("Serie guardada: " + serie.getTitulo());
        } catch (Exception e) {
            System.out.println("Error guardar serie: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public List<Serie> listarSeries() {
        List<Serie> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return lista;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, titulo, temporada FROM serie ORDER BY titulo");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serie s = new Serie();
                s.setId(rs.getInt("id"));
                s.setTitulo(rs.getString("titulo"));
                s.setTemporada(rs.getInt("temporada"));
                lista.add(s);
            }
            rs.close(); ps.close();
        } catch (Exception e) {
            System.out.println("Error listar series: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return lista;
    }

    public void editarSerie(int id, String titulo, int temporada) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "UPDATE serie SET titulo = ?, temporada = ? WHERE id = ?");
            ps.setString(1, titulo);
            ps.setInt(2, temporada);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Serie editada");
        } catch (Exception e) {
            System.out.println("Error editar serie: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public void eliminarSerie(int id) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM serie WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Serie eliminada");
        } catch (Exception e) {
            System.out.println("Error eliminar serie: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}