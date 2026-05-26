package vista;

import javax.swing.*;

public class Menu_Administrador extends JFrame {

    JLabel lblTitulo;
    JButton btnRegistrarVideo;
    JButton btnVerCatalogo;
    JButton btnVerUsuarios;
    JButton btnVerCompras;
    JButton btnGestionarIdiomas;
    JButton btnGestionarSeries;
    JButton btnGestionarClasificaciones;
    JButton btnCerrarSesion;

    public Menu_Administrador() {

        setTitle("Panel Administrador");
        setSize(500, 650);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("PANEL ADMINISTRADOR");
        lblTitulo.setBounds(130, 30, 250, 30);
        add(lblTitulo);

        btnRegistrarVideo = new JButton("Registrar Video");
        btnRegistrarVideo.setBounds(140, 80, 200, 40);
        add(btnRegistrarVideo);

        btnVerCatalogo = new JButton("Gestionar Catálogo");
        btnVerCatalogo.setBounds(140, 135, 200, 40);
        add(btnVerCatalogo);

        btnVerUsuarios = new JButton("Ver Usuarios");
        btnVerUsuarios.setBounds(140, 190, 200, 40);
        add(btnVerUsuarios);

        btnVerCompras = new JButton("Ver Compras");
        btnVerCompras.setBounds(140, 245, 200, 40);
        add(btnVerCompras);

        btnGestionarIdiomas = new JButton("Gestionar Idiomas");
        btnGestionarIdiomas.setBounds(140, 300, 200, 40);
        add(btnGestionarIdiomas);

        btnGestionarSeries = new JButton("Gestionar Series");
        btnGestionarSeries.setBounds(140, 355, 200, 40);
        add(btnGestionarSeries);

        btnGestionarClasificaciones = new JButton("Clasificaciones");
        btnGestionarClasificaciones.setBounds(140, 410, 200, 40);
        add(btnGestionarClasificaciones);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(140, 490, 200, 40);
        add(btnCerrarSesion);

        btnRegistrarVideo.addActionListener(e -> {
            RegistrarVideo ventana = new RegistrarVideo();
            ventana.setVisible(true);
        });

        btnVerCatalogo.addActionListener(e -> {
            Catalogo_Video ventana = new Catalogo_Video();
            ventana.setVisible(true);
        });

        btnVerUsuarios.addActionListener(e -> {
            VerUsuario ventana = new VerUsuario();
            ventana.setVisible(true);
        });

        btnVerCompras.addActionListener(e -> {
            Ver_Compras ventana = new Ver_Compras();
            ventana.setVisible(true);
        });

        btnGestionarIdiomas.addActionListener(e -> {
            GestionarIdioma ventana = new GestionarIdioma();
            ventana.setVisible(true);
        });

        btnGestionarSeries.addActionListener(e -> {
            GestionarSerie ventana = new GestionarSerie();
            ventana.setVisible(true);
        });

        btnGestionarClasificaciones.addActionListener(e -> {
            GestionarClasificacion ventana = new GestionarClasificacion();
            ventana.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}