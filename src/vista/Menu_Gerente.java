package vista;

import dao.Usuario_Dao;
import dao.Video_Dao;
import dao.Calificacion_Dao;
import modelo.Usuario;
import modelo.Video;
import modelo.Calificacion;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Menu_Gerente extends JFrame {

    private JButton btnReporteUsuarios;
    private JButton btnReporteVideos;
    private JButton btnReporteCalificaciones;
    private JButton btnCerrarSesion;

    public Menu_Gerente() {

        setTitle("Panel Gerente");
        setSize(420, 380);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblTitulo = new JLabel("PANEL GERENTE");
        lblTitulo.setBounds(130, 30, 200, 30);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(lblTitulo);

        btnReporteUsuarios = new JButton("Reporte de Usuarios");
        btnReporteUsuarios.setBounds(110, 90, 200, 40);
        add(btnReporteUsuarios);

        btnReporteVideos = new JButton("Reporte de Videos");
        btnReporteVideos.setBounds(110, 150, 200, 40);
        add(btnReporteVideos);

        btnReporteCalificaciones = new JButton("Reporte de Calificaciones");
        btnReporteCalificaciones.setBounds(110, 210, 200, 40);
        add(btnReporteCalificaciones);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(110, 290, 200, 40);
        add(btnCerrarSesion);

        btnReporteUsuarios.addActionListener(e -> {
            Usuario_Dao dao = new Usuario_Dao();
            List<Usuario> lista = dao.listarUsuarios();
            StringBuilder sb = new StringBuilder("=== REPORTE DE USUARIOS ===\n\n");
            for (Usuario u : lista) {
                sb.append("ID: ").append(u.getId())
                  .append(" | Nombre: ").append(u.getNombre())
                  .append(" | Correo: ").append(u.getCorreo())
                  .append(" | Rol: ").append(u.getRol())
                  .append("\n");
            }
            sb.append("\nTotal: ").append(lista.size()).append(" usuarios.");
            mostrarReporte("Reporte Usuarios", sb.toString());
        });

        btnReporteVideos.addActionListener(e -> {
            Video_Dao dao = new Video_Dao();
            List<Video> lista = dao.listarVideos();
            StringBuilder sb = new StringBuilder("=== REPORTE DE VIDEOS ===\n\n");
            for (Video v : lista) {
                sb.append("ID: ").append(v.getId())
                  .append(" | Título: ").append(v.getTituloOriginal())
                  .append(" | Categoría: ").append(v.getCategoria())
                  .append(" | Precio: $").append(v.getPrecio())
                  .append("\n");
            }
            sb.append("\nTotal: ").append(lista.size()).append(" videos.");
            mostrarReporte("Reporte Videos", sb.toString());
        });

        btnReporteCalificaciones.addActionListener(e -> {
            Video_Dao videoDao = new Video_Dao();
            Calificacion_Dao calDao = new Calificacion_Dao();
            List<Video> lista = videoDao.listarVideos();
            StringBuilder sb = new StringBuilder("=== REPORTE DE CALIFICACIONES ===\n\n");
            for (Video v : lista) {
                Calificacion c = calDao.obtenerPorVideo(v.getId());
                sb.append("Película: ").append(v.getTituloOriginal()).append("\n");
                if (c != null) {
                    sb.append("  Promedio: ").append(
                            String.format("%.2f", c.calcularPromedio()))
                      .append(" | Excelente: ").append(c.getExcelente())
                      .append(" | Buena: ").append(c.getBuena())
                      .append(" | Regular: ").append(c.getRegular())
                      .append(" | Mala: ").append(c.getMala())
                      .append("\n");
                } else {
                    sb.append("  Sin calificaciones\n");
                }
            }
            mostrarReporte("Reporte Calificaciones", sb.toString());
        });

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }

    private void mostrarReporte(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scroll, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }
}