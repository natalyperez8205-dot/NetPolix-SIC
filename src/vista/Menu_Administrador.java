package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import vista.RegistrarVideo;
import vista.Login;

public class Menu_Administrador extends JFrame {

    JLabel lblTitulo;

    JButton btnRegistrarVideo;
    JButton btnCerrarSesion;

    public Menu_Administrador(){

        setTitle("Menu Administrador");
        setSize(500,450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("BIENVENIDO ADMINISTRADOR");
        lblTitulo.setBounds(120,100,250,30);
        add(lblTitulo);

        btnRegistrarVideo = new JButton("Registrar Video");
        btnRegistrarVideo.setBounds(140,180,180,40);
        add(btnRegistrarVideo);

        btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBounds(140,250,180,40);
        add(btnCerrarSesion);

        btnRegistrarVideo.addActionListener(e -> {
            RegistrarVideo ventana = new RegistrarVideo();
            ventana.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}