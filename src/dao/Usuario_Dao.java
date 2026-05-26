package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Usuario;

public class Usuario_Dao {

	public void guardarUsuario(Usuario usuario) {
		try {
			Connection con = ConexionBD.getConexion();
			String sql = "INSERT INTO usuario(nombre, cedula, correo, contrasena, rol, fechaIngreso, puntos, saldo, idReferido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getCedula());
			ps.setString(3, usuario.getCorreo());
			ps.setString(4, usuario.getContrasena());
			ps.setString(5, usuario.getRol());
			ps.setString(6, usuario.getFechaIngreso());
			ps.setInt(7, usuario.getPuntos());
			ps.setDouble(8, usuario.getSaldo());
			ps.setInt(9, usuario.getIdReferido());
			ps.executeUpdate();
			System.out.println("Usuario guardado correctamente");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error al guardar usuario");
			System.out.println(e.getMessage());
		}
	}

	public List<Usuario> listarActores() {
		List<Usuario> lista = new ArrayList<>();
		try {
			Connection con = ConexionBD.getConexion();
			if (con == null) return lista;

			String sql = "SELECT * FROM usuario WHERE UPPER(rol) = 'ACTOR'";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNombre(rs.getString("nombre"));
				u.setCedula(rs.getString("cedula"));
				u.setCorreo(rs.getString("correo"));
				u.setRol(rs.getString("rol"));
				u.setFechaIngreso(rs.getString("fechaIngreso"));
				u.setPuntos(rs.getInt("puntos"));
				u.setSaldo(rs.getDouble("saldo"));
				lista.add(u);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error al listar actores: " + e.getMessage());
		}
		return lista;
	}

	public Usuario iniciarSesion(String correo, String contrasena) {
		Usuario usuario = null;
		try {
			Connection con = ConexionBD.getConexion();
			String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, correo);
			ps.setString(2, contrasena);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNombre(rs.getString("nombre"));
				usuario.setCorreo(rs.getString("correo"));
				usuario.setRol(rs.getString("rol"));
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("Error login");
			System.out.println(e.getMessage());
		}
		return usuario;
	}
}
