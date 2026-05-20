package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu_Administrador extends JFrame {

    JLabel lblTitulo;

    JButton btnRegistrarVideo;

    public Menu_Administrador(){

        setTitle("Menu Administrador");
        setSize(500,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo =
                new JLabel("BIENVENIDO ADMINISTRADOR");

        lblTitulo.setBounds(120,100,250,30);

        add(lblTitulo);

        btnRegistrarVideo =
                new JButton("Registrar Video");

        btnRegistrarVideo.setBounds(
                140,180,180,40);

        add(btnRegistrarVideo);

        btnRegistrarVideo.addActionListener(e -> {

            RegistrarVideo ventana = new RegistrarVideo();

            ventana.setVisible(true);

        });

    }
}