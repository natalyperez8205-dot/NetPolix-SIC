package vista;

import javax.swing.*;
import java.awt.*;

public class Menu_Administrador extends JFrame {

    public Menu_Administrador() {

        setTitle("NetPOLIx — Administrador");
        setSize(500, 730);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 500, 80);
        header.setBackground(Estilos.PANEL);

        JLabel lblNet = new JLabel("NET");
        lblNet.setBounds(20, 18, 120, 45);
        lblNet.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblNet.setForeground(Estilos.ACENTO);
        header.add(lblNet);

        JLabel lblPolix = new JLabel("POLIx");
        lblPolix.setBounds(98, 18, 150, 45);
        lblPolix.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblPolix.setForeground(Estilos.TEXTO);
        header.add(lblPolix);

        JLabel lblRol = new JLabel("● ADMINISTRADOR");
        lblRol.setBounds(240, 30, 230, 20);
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRol.setForeground(new Color(255, 165, 0));
        header.add(lblRol);

        add(header);

        JLabel lblMenu = new JLabel("Panel de administración");
        lblMenu.setBounds(30, 95, 400, 28);
        lblMenu.setFont(Estilos.FUENTE_TITULO);
        lblMenu.setForeground(Estilos.TEXTO);
        add(lblMenu);

        // BOTONES
        JButton btnRegistrarVideo   = crearBoton("🎬  Registrar Video",          30, 140);
        JButton btnVerCatalogo      = crearBoton("📋  Gestionar Catálogo",        30, 198);
        JButton btnVerUsuarios      = crearBoton("👥  Ver Usuarios",              30, 256);
        JButton btnRecargarSaldo    = crearBoton("💰  Recargar Saldo Cliente",    30, 314);
        JButton btnVerCompras       = crearBoton("🛒  Ver Compras",               30, 372);
        JButton btnGestionarIdiomas = crearBoton("🌐  Gestionar Idiomas",         30, 430);
        JButton btnGestionarSeries  = crearBoton("📺  Gestionar Series",          30, 488);
        JButton btnGestionarClasif  = crearBoton("🔞  Gestionar Clasificaciones", 30, 546);
        JButton btnCerrarSesion     = crearBotonSec("⏻  Cerrar Sesión",           30, 630);

        add(btnRegistrarVideo);
        add(btnVerCatalogo);
        add(btnVerUsuarios);
        add(btnRecargarSaldo);
        add(btnVerCompras);
        add(btnGestionarIdiomas);
        add(btnGestionarSeries);
        add(btnGestionarClasif);
        add(btnCerrarSesion);

        btnRegistrarVideo.addActionListener(e ->
                new RegistrarVideo().setVisible(true));

        btnVerCatalogo.addActionListener(e ->
                new Catalogo_Video().setVisible(true));

        btnVerUsuarios.addActionListener(e ->
                new VerUsuario().setVisible(true));

        btnRecargarSaldo.addActionListener(e ->
                new RecargaSaldo().setVisible(true));

        btnVerCompras.addActionListener(e ->
                new Ver_Compras().setVisible(true));

        btnGestionarIdiomas.addActionListener(e ->
                new GestionarIdioma().setVisible(true));

        btnGestionarSeries.addActionListener(e ->
                new GestionarSerie().setVisible(true));

        btnGestionarClasif.addActionListener(e ->
                new GestionarClasificacion().setVisible(true));

        btnCerrarSesion.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 430, 46);
        Estilos.botonPrincipal(btn);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        return btn;
    }

    private JButton crearBotonSec(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 430, 46);
        Estilos.botonSecundario(btn);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        return btn;
    }
}