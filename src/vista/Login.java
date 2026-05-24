package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import vista.Menu_Cliente;
import vista.Menu_Administrador;
import vista.Menu_Gerente;
import vista.Registro_Usuario;

public class Login extends JFrame {

    JLabel lblCorreo;
    JLabel lblContrasena;

    JTextField txtCorreo;
    JPasswordField txtContrasena;

    JButton btnIngresar;
    JButton btnRegistrar;

    public Login() {

        setTitle("Login");
        setSize(420,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30,40,100,25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(140,40,180,25);
        add(txtCorreo);

        lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(30,90,100,25);
        add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(140,90,180,25);
        add(txtContrasena);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(140,150,120,30);
        add(btnIngresar);

        btnRegistrar = new JButton("Registrar cuenta");
        btnRegistrar.setBounds(140,200,160,30);
        add(btnRegistrar);

        btnIngresar.addActionListener(e -> {

            Usuario_Dao dao = new Usuario_Dao();

            Usuario usuario = dao.iniciarSesion(
                    txtCorreo.getText(),
                    new String(txtContrasena.getPassword())
            );

            if(usuario != null){

                JOptionPane.showMessageDialog(null,
                        "Bienvenido "
                                + usuario.getNombre());

if(usuario.getRol().trim().equalsIgnoreCase("CLIENTE")){
                    Menu_Cliente menu = new Menu_Cliente(usuario);
                    menu.setVisible(true);
                } else if(usuario.getRol().trim().equalsIgnoreCase("ADMINISTRADOR")){
                    Menu_Administrador menu = new Menu_Administrador();
                    menu.setVisible(true);
                } else if(usuario.getRol().trim().equalsIgnoreCase("GERENTE")){
                    Menu_Gerente menu = new Menu_Gerente();
                    menu.setVisible(true);
                }

                dispose();

            } else {
                JOptionPane.showMessageDialog(null,
                        "Datos incorrectos");
            }

        });

        btnRegistrar.addActionListener(e -> {
            Registro_Usuario registro = new Registro_Usuario();
            registro.setVisible(true);
            dispose();
        });
    }
}