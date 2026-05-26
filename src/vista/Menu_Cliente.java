package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class Menu_Cliente extends JFrame {

    private Usuario usuario;

    public Menu_Cliente(Usuario usuario) {

        this.usuario = usuario;

        setTitle("NetPOLIx — Cliente");
        setSize(500, 520);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 500, 80);
        header.setBackground(Estilos.PANEL);
        header.setLayout(null);

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

        JLabel lblBienvenido = new JLabel("Hola, " + usuario.getNombre());
        lblBienvenido.setBounds(260, 15, 220, 20);
        lblBienvenido.setFont(Estilos.FUENTE_SUBTIT);
        lblBienvenido.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblBienvenido);

        JLabel lblRol = new JLabel("● CLIENTE");
        lblRol.setBounds(260, 38, 220, 20);
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRol.setForeground(Estilos.ACENTO);
        header.add(lblRol);

        add(header);

        // TÍTULO
        JLabel lblMenu = new JLabel("¿Qué quieres hacer hoy?");
        lblMenu.setBounds(30, 100, 400, 28);
        lblMenu.setFont(Estilos.FUENTE_TITULO);
        lblMenu.setForeground(Estilos.TEXTO);
        add(lblMenu);

        // BOTONES
        JButton btnCatalogo  = crearBoton("🎬  Ver Catálogo",        30, 150);
        JButton btnCarrito   = crearBoton("🛒  Mi Carrito",          30, 208);
        JButton btnCalificar = crearBoton("⭐  Calificar Película",  30, 266);
        JButton btnSaldo     = crearBotonSec("💰  Ver Saldo y Puntos", 30, 324);
        JButton btnSalir     = crearBotonSec("⏻  Cerrar Sesión",      30, 420);

        add(btnCatalogo);
        add(btnCarrito);
        add(btnCalificar);
        add(btnSaldo);
        add(btnSalir);

        // EVENTOS
        btnCatalogo.addActionListener(e ->
                new Catalogo_Video(usuario).setVisible(true));

        btnCarrito.addActionListener(e ->
                new Carrito(usuario).setVisible(true));

        btnCalificar.addActionListener(e ->
                new CalificarVideo(usuario).setVisible(true));

        btnSaldo.addActionListener(e -> {
            Usuario_Dao dao = new Usuario_Dao();
            double saldo  = dao.obtenerSaldo(usuario.getId());
            int puntos    = dao.obtenerPuntos(usuario.getId());

            String msg = "👤  " + usuario.getNombre() + "\n"
                    + "💰  Saldo disponible: $"
                    + String.format("%.2f", saldo) + "\n"
                    + "⭐  Puntos acumulados: " + puntos + "\n"
                    + (puntos >= 20
                        ? "\n🎁  ¡Tienes derecho a un video gratis!"
                        : "\n📊  Te faltan " + (20 - puntos)
                            + " puntos para un video gratis");

            JOptionPane.showMessageDialog(null, msg,
                    "Mi cuenta", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 430, 48);
        Estilos.botonPrincipal(btn);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        return btn;
    }

    private JButton crearBotonSec(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 430, 48);
        Estilos.botonSecundario(btn);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        return btn;
    }
}