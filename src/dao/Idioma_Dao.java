package dao;

import conexion.ConexionBD;
import modelo.Idioma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Idioma_Dao {

    public void guardarIdioma(Idioma idioma) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO idioma (lenguaje) VALUES (?)");
            ps.setString(1, idioma.getNombre());
            ps.executeUpdate();
            ps.close();
            System.out.println("Idioma guardado: " + idioma.getNombre());

        } catch (Exception e) {
            System.out.println("Error guardar idioma: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public List<Idioma> listarIdiomas() {
        List<Idioma> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return lista;

            PreparedStatement ps = con.prepareStatement(
                "SELECT id, lenguaje FROM idioma ORDER BY lenguaje");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Idioma idioma = new Idioma();
                idioma.setId(rs.getInt("id"));
                idioma.setNombre(rs.getString("lenguaje"));
                lista.add(idioma);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error listar idiomas: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return lista;
    }

    public void eliminarIdioma(int id) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM idioma WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Idioma eliminado");

        } catch (Exception e) {
            System.out.println("Error eliminar idioma: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}