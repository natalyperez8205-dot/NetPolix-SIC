package vista;

import dao.Calificacion_Dao;
import dao.Carrito_Dao;
import dao.Usuario_Dao;
import dao.Video_Dao;
import modelo.Calificacion;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Menu_Gerente extends JFrame {

    public Menu_Gerente() {

        setTitle("NetPOLIx — Gerente");
        setSize(500, 560);
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

        JLabel lblRol = new JLabel("● GERENTE");
        lblRol.setBounds(280, 30, 200, 20);
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblRol.setForeground(new Color(100, 200, 100));
        header.add(lblRol);

        add(header);

        // TÍTULO
        JLabel lblMenu = new JLabel("Reportes ejecutivos");
        lblMenu.setBounds(30, 100, 400, 28);
        lblMenu.setFont(Estilos.FUENTE_TITULO);
        lblMenu.setForeground(Estilos.TEXTO);
        add(lblMenu);

        // BOTONES
        JButton btnUsuarios       = crearBoton("👥  Reporte de Usuarios",       30, 148);
        JButton btnVideos         = crearBoton("🎬  Reporte de Videos",          30, 206);
        JButton btnCalificaciones = crearBoton("⭐  Reporte de Calificaciones",  30, 264);
        JButton btnVentas         = crearBoton("💰  Reporte de Ventas",          30, 322);
        JButton btnSalir          = crearBotonSec("⏻  Cerrar Sesión",            30, 450);

        add(btnUsuarios);
        add(btnVideos);
        add(btnCalificaciones);
        add(btnVentas);
        add(btnSalir);

        // EVENTO USUARIOS
        btnUsuarios.addActionListener(e -> {
            List<Usuario> lista = new Usuario_Dao().listarUsuarios();
            StringBuilder sb = new StringBuilder();
            for (Usuario u : lista) {
                sb.append("► ").append(u.getNombre())
                  .append("  |  ").append(u.getCorreo())
                  .append("  |  ").append(u.getRol()).append("\n");
            }
            sb.append("\nTotal: ").append(lista.size())
              .append(" usuarios registrados.");
            mostrarReporte("Reporte de Usuarios", sb.toString());
        });

        // EVENTO VIDEOS
        btnVideos.addActionListener(e -> {
            List<Video> lista = new Video_Dao().listarVideos();
            StringBuilder sb = new StringBuilder();
            for (Video v : lista) {
                sb.append("► ").append(v.getTituloOriginal())
                  .append("  |  ").append(v.getCategoria())
                  .append("  |  $").append(
                          String.format("%.2f", v.getPrecio())).append("\n");
            }
            sb.append("\nTotal: ").append(lista.size())
              .append(" videos en catálogo.");
            mostrarReporte("Reporte de Videos", sb.toString());
        });

        // EVENTO CALIFICACIONES
        btnCalificaciones.addActionListener(e -> {
            List<Video> lista = new Video_Dao().listarVideos();
            Calificacion_Dao calDao = new Calificacion_Dao();
            StringBuilder sb = new StringBuilder();
            for (Video v : lista) {
                Calificacion c = calDao.obtenerPorVideo(v.getId());
                sb.append("► ").append(v.getTituloOriginal()).append("\n");
                if (c != null) {
                    sb.append("   Promedio: ")
                      .append(String.format("%.2f", c.calcularPromedio()))
                      .append("/4")
                      .append("  |  Excelente: ").append(c.getExcelente())
                      .append("  Buena: ").append(c.getBuena())
                      .append("  Regular: ").append(c.getRegular())
                      .append("  Mala: ").append(c.getMala())
                      .append("\n");
                } else {
                    sb.append("   Sin calificaciones aún.\n");
                }
            }
            mostrarReporte("Reporte de Calificaciones", sb.toString());
        });

        // EVENTO VENTAS
        btnVentas.addActionListener(e -> {
            Usuario_Dao uDao = new Usuario_Dao();
            Video_Dao vDao   = new Video_Dao();
            Carrito_Dao cDao = new Carrito_Dao();

            Map<Integer, Map<Integer, Integer>> carritos =
                    cDao.obtenerTodosLosCarritos();

            if (carritos.isEmpty()) {
                mostrarReporte("Reporte de Ventas",
                        "No hay ventas registradas aún.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            double totalGeneral = 0;

            for (Integer idUsuario : carritos.keySet()) {
                Usuario u = uDao.buscarUsuarioPorId(idUsuario);
                sb.append("┌────────────────────────────────────────┐\n");
                sb.append("│ 👤 ").append(
                        u != null ? u.getNombre() : "Usuario " + idUsuario)
                  .append("\n│\n");

                Map<Integer, Integer> items = carritos.get(idUsuario);
                double totalUsuario = 0;

                for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                    Video v = vDao.buscarPorId(entry.getKey());
                    if (v != null) {
                        double sub = v.getPrecio() * entry.getValue();
                        totalUsuario += sub;
                        sb.append("│ 🎬 ").append(v.getTituloOriginal())
                          .append("\n│     x").append(entry.getValue())
                          .append("  →  $")
                          .append(String.format("%.2f", sub)).append("\n");
                    }
                }

                sb.append("│\n│ Subtotal: $")
                  .append(String.format("%.2f", totalUsuario)).append("\n");
                sb.append("└────────────────────────────────────────┘\n\n");
                totalGeneral += totalUsuario;
            }

            sb.append("══════════════════════════════════════\n");
            sb.append("💰 TOTAL GENERAL: $")
              .append(String.format("%.2f", totalGeneral));

            mostrarReporte("Reporte de Ventas", sb.toString());
        });

        // EVENTO CERRAR SESIÓN
        btnSalir.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private void mostrarReporte(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBackground(Estilos.PANEL);
        area.setForeground(Estilos.TEXTO);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(620, 420));
        scroll.getViewport().setBackground(Estilos.PANEL);
        JOptionPane.showMessageDialog(null, scroll, titulo,
                JOptionPane.PLAIN_MESSAGE);
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