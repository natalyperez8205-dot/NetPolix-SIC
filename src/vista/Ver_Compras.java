package vista;

import dao.Carrito_Dao;
import dao.Usuario_Dao;
import dao.Video_Dao;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Ver_Compras extends JFrame {

    JTextArea areaCompras;

    public Ver_Compras() {

        setTitle("NetPOLIx — Ver Compras");
        setSize(600, 560);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 600, 65);
        header.setBackground(Estilos.PANEL);

        JLabel lblNet = new JLabel("NET");
        lblNet.setBounds(20, 10, 100, 45);
        lblNet.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblNet.setForeground(Estilos.ACENTO);
        header.add(lblNet);

        JLabel lblPolix = new JLabel("POLIx");
        lblPolix.setBounds(93, 10, 140, 45);
        lblPolix.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblPolix.setForeground(Estilos.TEXTO);
        header.add(lblPolix);

        JLabel lblSub = new JLabel("🛒  Reporte de compras");
        lblSub.setBounds(280, 22, 290, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // ÁREA DE TEXTO
        areaCompras = new JTextArea();
        areaCompras.setEditable(false);
        Estilos.area(areaCompras);

        JScrollPane scroll = new JScrollPane(areaCompras);
        scroll.setBounds(20, 80, 550, 400);
        Estilos.scroll(scroll);
        add(scroll);

        // BOTONES
        JButton btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setBounds(20, 495, 160, 38);
        Estilos.botonPrincipal(btnRefrescar);
        add(btnRefrescar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(410, 495, 160, 38);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        btnRefrescar.addActionListener(e -> mostrarCompras());
        btnVolver.addActionListener(e -> dispose());

        mostrarCompras();
        setLocationRelativeTo(null);
    }

    private void mostrarCompras() {
        Carrito_Dao carritoDao = new Carrito_Dao();
        Usuario_Dao usuarioDao = new Usuario_Dao();
        Video_Dao videoDao     = new Video_Dao();

        Map<Integer, Map<Integer, Integer>> datos =
                carritoDao.obtenerTodosLosCarritos();

        if (datos.isEmpty()) {
            areaCompras.setText("  No hay compras registradas aún.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Integer idUsuario : datos.keySet()) {
            Usuario usuario = usuarioDao.buscarUsuarioPorId(idUsuario);

            sb.append("┌─────────────────────────────────────────────┐\n");
            if (usuario != null) {
                sb.append("│  👤 ").append(usuario.getNombre()).append("\n");
                sb.append("│  ✉  ").append(usuario.getCorreo()).append("\n");
            } else {
                sb.append("│  👤 Usuario ID: ").append(idUsuario).append("\n");
            }
            sb.append("│\n");

            Map<Integer, Integer> carrito = datos.get(idUsuario);
            double total = 0;

            for (Integer idVideo : carrito.keySet()) {
                Video video = videoDao.buscarPorId(idVideo);
                int cantidad = carrito.get(idVideo);
                if (video != null) {
                    double sub = video.getPrecio() * cantidad;
                    total += sub;
                    sb.append("│  🎬 ").append(video.getTituloOriginal()).append("\n");
                    sb.append("│     Categoría: ").append(video.getCategoria()).append("\n");
                    sb.append("│     Precio: $").append(
                            String.format("%.2f", video.getPrecio()));
                    sb.append("  x").append(cantidad);
                    sb.append("  Subtotal: $").append(
                            String.format("%.2f", sub)).append("\n");
                    sb.append("│\n");
                }
            }

            sb.append("│  💰 TOTAL: $").append(
                    String.format("%.2f", total)).append("\n");
            sb.append("└─────────────────────────────────────────────┘\n\n");
        }

        areaCompras.setText(sb.toString());
        areaCompras.setCaretPosition(0);
    }
}