package dao;

import conexion.ConexionBD;
import modelo.Clasificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Clasificacion_Dao {

    public void guardarClasificacion(Clasificacion c) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO clasificacion (tipo, descripcion) VALUES (?, ?)");
            ps.setString(1, c.getTipo());
            ps.setString(2, c.getDescripcion());
            ps.executeUpdate();
            ps.close();
            System.out.println("Clasificación guardada: " + c.getTipo());
        } catch (Exception e) {
            System.out.println("Error guardar clasificacion: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public List<Clasificacion> listarClasificaciones() {
        List<Clasificacion> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return lista;
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, tipo, descripcion FROM clasificacion ORDER BY tipo");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Clasificacion c = new Clasificacion();
                c.setId(rs.getInt("id"));
                c.setTipo(rs.getString("tipo"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }
            rs.close(); ps.close();
        } catch (Exception e) {
            System.out.println("Error listar clasificaciones: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return lista;
    }

    public void editarClasificacion(int id, String tipo, String descripcion) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "UPDATE clasificacion SET tipo = ?, descripcion = ? WHERE id = ?");
            ps.setString(1, tipo);
            ps.setString(2, descripcion);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Clasificación editada");
        } catch (Exception e) {
            System.out.println("Error editar clasificacion: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public void eliminarClasificacion(int id) {
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            if (con == null) return;
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM clasificacion WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            System.out.println("Clasificación eliminada");
        } catch (Exception e) {
            System.out.println("Error eliminar clasificacion: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}