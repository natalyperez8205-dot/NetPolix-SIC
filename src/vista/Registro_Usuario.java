package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Registro_Usuario extends JFrame {

    JTextField txtNombre;
    JTextField txtCedula;
    JTextField txtCorreo;
    JPasswordField txtContrasena;
    JButton btnRegistrar;
    JButton btnVolver;

    public Registro_Usuario() {

        setTitle("NetPOLIx — Registro");
        setSize(480, 580);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        JLabel lblLogo = new JLabel("NET");
        lblLogo.setBounds(90, 30, 200, 50);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblLogo.setForeground(Estilos.ACENTO);
        add(lblLogo);

        JLabel lblLogo2 = new JLabel("POLIx");
        lblLogo2.setBounds(200, 30, 200, 50);
        lblLogo2.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblLogo2.setForeground(Estilos.TEXTO);
        add(lblLogo2);

        JLabel lblTitulo = new JLabel("Crear cuenta");
        lblTitulo.setBounds(60, 88, 300, 28);
        lblTitulo.setFont(Estilos.FUENTE_TITULO);
        lblTitulo.setForeground(Estilos.TEXTO);
        add(lblTitulo);

        // NOMBRE
        JLabel lblNombre = new JLabel("Nombre completo");
        lblNombre.setBounds(60, 130, 200, 18);
        lblNombre.setFont(Estilos.FUENTE_SUBTIT);
        lblNombre.setForeground(Estilos.TEXTO_GRIS);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(60, 150, 350, 36);
        Estilos.campo(txtNombre);
        add(txtNombre);

        // CEDULA
        JLabel lblCedula = new JLabel("Cédula");
        lblCedula.setBounds(60, 198, 200, 18);
        lblCedula.setFont(Estilos.FUENTE_SUBTIT);
        lblCedula.setForeground(Estilos.TEXTO_GRIS);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(60, 218, 350, 36);
        Estilos.campo(txtCedula);
        add(txtCedula);

        // CORREO
        JLabel lblCorreo = new JLabel("Correo electrónico");
        lblCorreo.setBounds(60, 266, 200, 18);
        lblCorreo.setFont(Estilos.FUENTE_SUBTIT);
        lblCorreo.setForeground(Estilos.TEXTO_GRIS);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(60, 286, 350, 36);
        Estilos.campo(txtCorreo);
        add(txtCorreo);

        // CONTRASEÑA
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(60, 334, 200, 18);
        lblPass.setFont(Estilos.FUENTE_SUBTIT);
        lblPass.setForeground(Estilos.TEXTO_GRIS);
        add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(60, 354, 350, 36);
        Estilos.campo(txtContrasena);
        add(txtContrasena);

        // BOTONES
        btnRegistrar = new JButton("CREAR CUENTA");
        btnRegistrar.setBounds(60, 415, 350, 42);
        Estilos.botonPrincipal(btnRegistrar);
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 15));
        add(btnRegistrar);

        btnVolver = new JButton("¿Ya tienes cuenta? Inicia sesión");
        btnVolver.setBounds(60, 470, 350, 36);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        btnRegistrar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()
                    || txtCedula.getText().trim().isEmpty()
                    || txtCorreo.getText().trim().isEmpty()
                    || txtContrasena.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null,
                        "Completa todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(txtNombre.getText().trim());
            usuario.setCedula(txtCedula.getText().trim());
            usuario.setCorreo(txtCorreo.getText().trim());
            usuario.setContrasena(new String(txtContrasena.getPassword()));
            usuario.setRol("CLIENTE");
            usuario.setFechaIngreso(LocalDate.now().toString());
            usuario.setPuntos(0);
            usuario.setSaldo(0);
            usuario.setIdReferido(0);

            new Usuario_Dao().guardarUsuario(usuario);

            JOptionPane.showMessageDialog(null,
                    "¡Cuenta creada exitosamente!",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            new Login().setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }
}