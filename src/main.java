import dao.Usuario_Dao;
import modelo.Usuario;

public class main {

    public static void main(String[] args) {

        Usuario usuario = new Usuario();

        usuario.setNombre("Karen");
        usuario.setCedula("123456");
        usuario.setCorreo("karen@gmail.com");
        usuario.setContrasena("1234");
        usuario.setRol("cliente");
        usuario.setFechaIngreso("2026-05-20");
        usuario.setPuntos(0);
        usuario.setSaldo(10000);
        usuario.setIdReferido(0);

        Usuario_Dao dao = new Usuario_Dao();

        dao.guardarUsuario(usuario);

    }
}
