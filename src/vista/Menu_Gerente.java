package vista;

import conexion.ConexionBD;
import dao.Calificacion_Dao;
import dao.Usuario_Dao;
import dao.Video_Dao;
import modelo.Calificacion;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
        JButton btnUsuarios       = crearBoton("👥  Reporte de Usuarios",      30, 148);
        JButton btnVideos         = crearBoton("🎬  Reporte de Videos",         30, 206);
        JButton btnCalificaciones = crearBoton("⭐  Reporte de Calificaciones", 30, 264);
        JButton btnVentas         = crearBoton("💰  Reporte de Ventas",         30, 322);
        JButton btnSalir          = crearBotonSec("⏻  Cerrar Sesión",           30, 450);

        add(btnUsuarios);
        add(btnVideos);
        add(btnCalificaciones);
        add(btnVentas);
        add(btnSalir);

        // REPORTE USUARIOS
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

        // REPORTE VIDEOS
        btnVideos.addActionListener(e -> {
            List<Video> lista = new Video_Dao().listarVideos();
            StringBuilder sb = new StringBuilder();
            for (Video v : lista) {
                sb.append("► ").append(v.getTituloOriginal())
                  .append("  |  ").append(
                        v.getCategoria() == null
                        || v.getCategoria().isEmpty()
                        ? "(Sin categoría)" : v.getCategoria())
                  .append("  |  $")
                  .append(String.format("%.2f", v.getPrecio()))
                  .append("\n");
            }
            sb.append("\nTotal: ").append(lista.size())
              .append(" videos en catálogo.");
            mostrarReporte("Reporte de Videos", sb.toString());
        });

        // REPORTE CALIFICACIONES
        btnCalificaciones.addActionListener(e -> {
            List<Video> lista = new Video_Dao().listarVideos();
            Calificacion_Dao calDao = new Calificacion_Dao();
            StringBuilder sb = new StringBuilder();
            for (Video v : lista) {
                Calificacion c = calDao.obtenerPorVideo(v.getId());
                sb.append("► ").append(v.getTituloOriginal()).append("\n");
                if (c != null) {
                    int total = c.getExcelente() + c.getBuena()
                              + c.getRegular() + c.getMala();
                    sb.append("   Promedio: ")
                      .append(String.format("%.2f", c.calcularPromedio()))
                      .append("/4")
                      .append("  |  Total votos: ").append(total)
                      .append("\n")
                      .append("   😍 Excelente: ").append(c.getExcelente())
                      .append("  🙂 Buena: ").append(c.getBuena())
                      .append("  😐 Regular: ").append(c.getRegular())
                      .append("  😤 Mala: ").append(c.getMala())
                      .append("\n");
                } else {
                    sb.append("   Sin calificaciones aún.\n");
                }
            }
            if (lista.isEmpty()) {
                mostrarReporte("Reporte de Calificaciones",
                        "No hay videos en el catálogo.");
            } else {
                mostrarReporte("Reporte de Calificaciones", sb.toString());
            }
        });

        // REPORTE VENTAS
        btnVentas.addActionListener(e -> {
            Connection con = null;
            try {
                con = ConexionBD.getConexion();
                if (con == null) {
                    JOptionPane.showMessageDialog(null, "Sin conexión.");
                    return;
                }

                PreparedStatement ps = con.prepareStatement(
                    "SELECT c.id, u.nombre, u.correo, " +
                    "c.fecha, c.total, c.puntos_ganados " +
                    "FROM compra c " +
                    "JOIN usuario u ON c.id_usuario = u.id " +
                    "ORDER BY c.fecha DESC");

                ResultSet rs = ps.executeQuery();
                StringBuilder sb = new StringBuilder();
                double totalGeneral = 0;
                int totalCompras = 0;

                while (rs.next()) {
                    totalCompras++;
                    double total = rs.getDouble("total");
                    totalGeneral += total;

                    sb.append("┌──────────────────────────────────────┐\n");
                    sb.append("│ 👤 ").append(
                            rs.getString("nombre")).append("\n");
                    sb.append("│ ✉  ").append(
                            rs.getString("correo")).append("\n");
                    sb.append("│ 📅 ").append(
                            rs.getString("fecha")).append("\n");
                    sb.append("│ 💰 Total: $")
                      .append(String.format("%.2f", total)).append("\n");
                    sb.append("│ ⭐ Puntos ganados: ")
                      .append(rs.getInt("puntos_ganados")).append("\n");

                    // Detalle de la compra
                    int idCompra = rs.getInt("id");
                    PreparedStatement psD = con.prepareStatement(
                        "SELECT v.tituloOriginal, d.cantidad, d.subtotal " +
                        "FROM detalle_compra d " +
                        "JOIN video v ON d.id_video = v.id " +
                        "WHERE d.id_compra = ?");
                    psD.setInt(1, idCompra);
                    ResultSet rsD = psD.executeQuery();
                    while (rsD.next()) {
                        sb.append("│   🎬 ")
                          .append(rsD.getString("tituloOriginal"))
                          .append(" x").append(rsD.getInt("cantidad"))
                          .append(" = $")
                          .append(String.format("%.2f",
                                  rsD.getDouble("subtotal")))
                          .append("\n");
                    }
                    rsD.close();
                    psD.close();

                    sb.append("└──────────────────────────────────────┘\n\n");
                }

                rs.close();
                ps.close();
                con.close();

                sb.append("══════════════════════════════════════\n");
                sb.append("📊 Total compras: ")
                  .append(totalCompras).append("\n");
                sb.append("💰 TOTAL GENERAL: $")
                  .append(String.format("%.2f", totalGeneral));

                if (totalCompras == 0) {
                    mostrarReporte("Reporte de Ventas",
                            "No hay ventas registradas aún.");
                } else {
                    mostrarReporte("Reporte de Ventas", sb.toString());
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al generar reporte: " + ex.getMessage());
            } finally {
                try {
                    if (con != null) con.close();
                } catch (Exception ex) {}
            }
        });

        // CERRAR SESIÓN
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