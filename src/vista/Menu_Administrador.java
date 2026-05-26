package vista;

import javax.swing.*;

public class Menu_Administrador extends JFrame {

    JLabel lblTitulo;

    JButton btnRegistrarVideo;
    JButton btnVerCatalogo;
    JButton btnVerUsuarios;
    JButton btnVerCompras;
    JButton btnGestionarIdiomas;
    JButton btnCerrarSesion;

    public Menu_Administrador(){

        setTitle("Panel Administrador");

        setSize(500,550);

        setLayout(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel(
                "PANEL ADMINISTRADOR");

        lblTitulo.setBounds(
                130,40,250,30);

        add(lblTitulo);

        // BOTON REGISTRAR VIDEO

        btnRegistrarVideo =
                new JButton(
                        "Registrar Video");

        btnRegistrarVideo.setBounds(
                140,100,180,40);

        add(btnRegistrarVideo);

        // BOTON GESTIONAR CATALOGO

        btnVerCatalogo =
                new JButton(
                        "Gestionar Catalogo");

        btnVerCatalogo.setBounds(
                140,160,180,40);

        add(btnVerCatalogo);

        // BOTON VER USUARIOS

        btnVerUsuarios =
                new JButton(
                        "Ver Usuarios");

        btnVerUsuarios.setBounds(
                140,220,180,40);

        add(btnVerUsuarios);

        // BOTON VER COMPRAS

        btnVerCompras =
                new JButton(
                        "Ver Compras");

        btnVerCompras.setBounds(
                140,280,180,40);

        add(btnVerCompras);

        // BOTON GESTIONAR IDIOMAS

        btnGestionarIdiomas =
                new JButton(
                        "Gestionar Idiomas");

        btnGestionarIdiomas.setBounds(
                140,340,180,40);

        add(btnGestionarIdiomas);

        // BOTON CERRAR SESION

        btnCerrarSesion =
                new JButton(
                        "Cerrar Sesión");

        btnCerrarSesion.setBounds(
                140,420,180,40);

        add(btnCerrarSesion);

        // EVENTO REGISTRAR VIDEO

        btnRegistrarVideo
                .addActionListener(e -> {

            RegistrarVideo ventana =
                    new RegistrarVideo();

            ventana.setVisible(true);

        });

        // EVENTO GESTIONAR CATALOGO

        btnVerCatalogo
                .addActionListener(e -> {

            Catalogo_Video ventana =
                    new Catalogo_Video();

            ventana.setVisible(true);

        });

        // EVENTO VER USUARIOS

        btnVerUsuarios
                .addActionListener(e -> {

            VerUsuario ventana =
                    new VerUsuario();

            ventana.setVisible(true);

        });

        // EVENTO VER COMPRAS

        btnVerCompras
                .addActionListener(e -> {

            Ver_Compras ventana =
                    new Ver_Compras();

            ventana.setVisible(true);

        });

        // EVENTO GESTIONAR IDIOMAS

        btnGestionarIdiomas
                .addActionListener(e -> {

            GestionarIdioma ventana =
                    new GestionarIdioma();

            ventana.setVisible(true);

        });

        // EVENTO CERRAR SESION

        btnCerrarSesion
                .addActionListener(e -> {

            Login login =
                    new Login();

            login.setVisible(true);

            dispose();

        });

    }
}