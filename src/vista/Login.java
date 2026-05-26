package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    JTextField txtCorreo;
    JPasswordField txtContrasena;
    JButton btnIngresar;
    JButton btnRegistrar;
    JButton btnSalir;

    public Login() {

        setTitle("NetPOLIx");
        setSize(480, 560);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Estilos.FONDO);

        // LOGO
        JLabel lblLogo = new JLabel("NET");
        lblLogo.setBounds(90, 45, 200, 60);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 52));
        lblLogo.setForeground(Estilos.ACENTO);
        add(lblLogo);

        JLabel lblLogo2 = new JLabel("POLIx");
        lblLogo2.setBounds(210, 45, 220, 60);
        lblLogo2.setFont(new Font("SansSerif", Font.BOLD, 52));
        lblLogo2.setForeground(Estilos.TEXTO);
        add(lblLogo2);

        JLabel lblSub = new JLabel("Tu plataforma de streaming");
        lblSub.setBounds(100, 108, 300, 20);
        lblSub.setFont(Estilos.FUENTE_NORMAL);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        add(lblSub);

        JSeparator sep = new JSeparator();
        sep.setBounds(60, 138, 350, 2);
        sep.setForeground(Estilos.BORDE);
        add(sep);

        // CORREO
        JLabel lblCorreo = new JLabel("Correo electrónico");
        lblCorreo.setBounds(60, 155, 200, 20);
        lblCorreo.setFont(Estilos.FUENTE_SUBTIT);
        lblCorreo.setForeground(Estilos.TEXTO_GRIS);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(60, 178, 350, 38);
        Estilos.campo(txtCorreo);
        add(txtCorreo);

        // CONTRASEÑA
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(60, 228, 200, 20);
        lblPass.setFont(Estilos.FUENTE_SUBTIT);
        lblPass.setForeground(Estilos.TEXTO_GRIS);
        add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(60, 251, 350, 38);
        Estilos.campo(txtContrasena);
        add(txtContrasena);

        // BOTÓN INGRESAR
        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setBounds(60, 315, 350, 42);
        Estilos.botonPrincipal(btnIngresar);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 15));
        add(btnIngresar);

        // BOTÓN REGISTRAR
        btnRegistrar = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrar.setBounds(60, 370, 350, 38);
        Estilos.botonSecundario(btnRegistrar);
        add(btnRegistrar);

        // BOTÓN SALIR
        btnSalir = new JButton("✕  Salir del programa");
        btnSalir.setBounds(60, 420, 350, 38);
        btnSalir.setBackground(new Color(30, 30, 30));
        btnSalir.setForeground(Estilos.TEXTO_GRIS);
        btnSalir.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSalir);

        JLabel lblVersion = new JLabel(
                "NetPOLIx © 2026 - Politécnico Gran Colombiano");
        lblVersion.setBounds(60, 490, 380, 18);
        lblVersion.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblVersion.setForeground(Estilos.BORDE);
        add(lblVersion);

        // EVENTOS
        btnIngresar.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtContrasena.getPassword()).trim();

            if (correo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Completa todos los campos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario_Dao dao = new Usuario_Dao();
            Usuario usuario = dao.iniciarSesion(correo, pass);

            if (usuario != null) {
                JOptionPane.showMessageDialog(null,
                        "¡Bienvenido, " + usuario.getNombre() + "!",
                        "Acceso concedido",
                        JOptionPane.INFORMATION_MESSAGE);

                String rol = usuario.getRol().trim().toUpperCase();
                if (rol.equals("CLIENTE")) {
                    new Menu_Cliente(usuario).setVisible(true);
                } else if (rol.equals("ADMINISTRADOR")) {
                    new Menu_Administrador().setVisible(true);
                } else if (rol.equals("GERENTE")) {
                    new Menu_Gerente().setVisible(true);
                }
                dispose();

            } else {
                JOptionPane.showMessageDialog(null,
                        "Correo o contraseña incorrectos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegistrar.addActionListener(e -> {
            new Registro_Usuario().setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Deseas salir de NetPOLIx?",
                    "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
    }
}