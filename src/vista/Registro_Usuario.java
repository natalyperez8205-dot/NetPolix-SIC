package vista;
import javax.swing.*;
import dao.Usuario_Dao;
import modelo.Usuario;
public class Registro_Usuario extends JFrame {

	JLabel lblNombre;
    JLabel lblCedula;
    JLabel lblCorreo;
    JLabel lblContrasena;

    JTextField txtNombre;
    JTextField txtCedula;
    JTextField txtCorreo;

    JPasswordField txtContrasena;

    JButton btnRegistrar;

    public Registro_Usuario() {

        setTitle("Registro Usuario");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 180, 25);
        add(txtNombre);

        lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(30, 70, 100, 25);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(140, 70, 180, 25);
        add(txtCedula);

        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 110, 100, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(140, 110, 180, 25);
        add(txtCorreo);

        lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30, 150, 100, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(140, 150, 180, 25);
        add(txtContrasena);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140, 200, 120, 30);
        add(btnRegistrar);
        
        btnRegistrar.addActionListener(e -> {

            Usuario usuario = new Usuario();

            usuario.setNombre(txtNombre.getText());
            usuario.setCedula(txtCedula.getText());
            usuario.setCorreo(txtCorreo.getText());
            usuario.setContrasena(txtContrasena.getText());

            usuario.setRol("cliente");
            usuario.setFechaIngreso("2026-05-20");
            usuario.setPuntos(0);
            usuario.setSaldo(0);
            usuario.setIdReferido(0);

            Usuario_Dao dao = new Usuario_Dao();

            dao.guardarUsuario(usuario);

            JOptionPane.showMessageDialog(null,
                    "Usuario registrado correctamente");

        });
    }
}
