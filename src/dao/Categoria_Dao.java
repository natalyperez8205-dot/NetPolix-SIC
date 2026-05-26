package dao;

import conexion.ConexionBD;
import modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Categoria_Dao {

	public void guardarCategoria(Categoria categoria) {
		try {
			Connection con = ConexionBD.getConexion();
			if (con == null) return;
			String sql = "INSERT INTO categoria (nombre) VALUES (?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, categoria.getNombre());
			ps.executeUpdate();
			ps.close();
			con.close();
			System.out.println("Categoría guardada: " + categoria.getNombre());
		} catch (Exception e) {
			System.out.println("Error al guardar categoría: " + e.getMessage());
		}
	}

	public List<Categoria> listarCategorias() {
		List<Categoria> lista = new ArrayList<>();
		try {
			Connection con = ConexionBD.getConexion();
			if (con == null) return lista;
			String sql = "SELECT id, nombre FROM categoria ORDER BY nombre";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Categoria c = new Categoria();
				c.setId(rs.getInt("id"));
				c.setNombre(rs.getString("nombre"));
				lista.add(c);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error al listar categorias: " + e.getMessage());
		}
		return lista;
	}

}

