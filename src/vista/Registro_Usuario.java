package vista;
import javax.swing.*;
import java.time.LocalDate;
import dao.Usuario_Dao;
import modelo.Usuario;
import vista.Login;

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
    JButton btnVolver;

    public Registro_Usuario() {

        setTitle("Registro Usuario");
        setSize(420, 380);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 220, 25);
        add(txtNombre);

        lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(30, 70, 100, 25);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(140, 70, 220, 25);
        add(txtCedula);

        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 110, 100, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(140, 110, 220, 25);
        add(txtCorreo);

        lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30, 150, 100, 25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(140, 150, 220, 25);
        add(txtContrasena);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(140, 210, 120, 30);
        add(btnRegistrar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(280, 210, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(e -> {

            Usuario usuario = new Usuario();

            usuario.setNombre(txtNombre.getText());
            usuario.setCedula(txtCedula.getText());
            usuario.setCorreo(txtCorreo.getText());
            usuario.setContrasena(new String(txtContrasena.getPassword()));

            usuario.setRol("CLIENTE");
            usuario.setFechaIngreso(LocalDate.now().toString());
            usuario.setPuntos(0);
            usuario.setSaldo(0);
            usuario.setIdReferido(0);

            Usuario_Dao dao = new Usuario_Dao();
            dao.guardarUsuario(usuario);

            JOptionPane.showMessageDialog(null,
                    "Usuario registrado correctamente");

            Login login = new Login();
            login.setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}
