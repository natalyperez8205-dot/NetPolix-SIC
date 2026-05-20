
	package dao;
    import java.sql.ResultSet;
	import conexion.ConexionBD;
	import modelo.Usuario;

	import java.sql.Connection;
	import java.sql.PreparedStatement;

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

	        } catch (Exception e) {

	            System.out.println("Error al guardar usuario");
	            System.out.println(e.getMessage());
	        }
	        
	    }
	    public Usuario iniciarSesion(String correo,
                String contrasena) {

             Usuario usuario = null;

              try {

                 Connection con = ConexionBD.getConexion();

                String sql = "SELECT * FROM usuario "
                         + "WHERE correo = ? "
                         + "AND contrasena = ?";

                 PreparedStatement ps =
                 con.prepareStatement(sql);
 
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

       } catch (Exception e) {

    System.out.println("Error login");
    System.out.println(e.getMessage()); }

   return usuario;
  } 
}
