package vista;

import dao.Carrito_Dao;
import dao.Video_Dao;
import dao.Usuario_Dao;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Carrito extends JFrame {

    private Usuario usuario;
    private DefaultListModel<String> modeloCarrito;
    private JList<String> listaCarrito;
    private JLabel lblTotal;
    private JLabel lblSaldo;
    private final Carrito_Dao carritoDao = new Carrito_Dao();
    private final Video_Dao videoDao     = new Video_Dao();
    private final Usuario_Dao usuarioDao = new Usuario_Dao();

    public Carrito(Usuario usuario) {
        this.usuario = usuario;

        setTitle("NetPOLIx — Mi Carrito");
        setSize(660, 540);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 660, 65);
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

        JLabel lblSub = new JLabel("🛒  Mi carrito de compras");
        lblSub.setBounds(280, 22, 350, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // SALDO
        lblSaldo = new JLabel("Saldo disponible: $0.00");
        lblSaldo.setBounds(20, 78, 400, 22);
        lblSaldo.setFont(Estilos.FUENTE_SUBTIT);
        lblSaldo.setForeground(new Color(100, 200, 100));
        add(lblSaldo);

        // LISTA
        modeloCarrito = new DefaultListModel<>();
        listaCarrito  = new JList<>(modeloCarrito);
        listaCarrito.setBackground(Estilos.PANEL);
        listaCarrito.setForeground(Estilos.TEXTO);
        listaCarrito.setFont(Estilos.FUENTE_NORMAL);
        listaCarrito.setSelectionBackground(Estilos.ACENTO);
        listaCarrito.setFixedCellHeight(36);

        JScrollPane scroll = new JScrollPane(listaCarrito);
        scroll.setBounds(20, 108, 610, 260);
        Estilos.scroll(scroll);
        add(scroll);

        // TOTAL
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setBounds(20, 378, 300, 28);
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(Estilos.ACENTO);
        add(lblTotal);

        // BOTONES
        JButton btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBounds(20, 420, 170, 42);
        Estilos.botonPrincipal(btnEliminar);
        btnEliminar.setBackground(new Color(150, 20, 20));
        add(btnEliminar);

        JButton btnComprar = new JButton("✅ Confirmar compra");
        btnComprar.setBounds(205, 420, 200, 42);
        Estilos.botonPrincipal(btnComprar);
        add(btnComprar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(500, 420, 140, 42);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        btnEliminar.addActionListener(e -> {
            String sel = listaCarrito.getSelectedValue();
            if (sel == null || sel.contains("vacío")) {
                JOptionPane.showMessageDialog(null,
                        "Selecciona un elemento.");
                return;
            }
            int idVideo = extraerIdVideo(sel);
            if (idVideo > 0) {
                carritoDao.eliminarDelCarrito(usuario.getId(), idVideo);
                cargarCarrito();
            }
        });

        btnComprar.addActionListener(e -> {

            if (modeloCarrito.isEmpty()
                    || modeloCarrito.get(0).contains("vacío")) {
                JOptionPane.showMessageDialog(null,
                        "Tu carrito está vacío.");
                return;
            }

            // Calcular total y puntos
            Map<Integer, Integer> items =
                    carritoDao.obtenerCarrito(usuario.getId());
            double total = 0;
            int puntosGanados = 0;

            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                Video v = videoDao.buscarPorId(entry.getKey());
                if (v != null) {
                    total += v.getPrecio() * entry.getValue();
                    puntosGanados += 2 * entry.getValue();
                }
            }

            double saldoActual = usuarioDao.obtenerSaldo(usuario.getId());

            if (saldoActual < total) {
                JOptionPane.showMessageDialog(null,
                        "❌ Saldo insuficiente.\n"
                        + "Saldo disponible: $"
                        + String.format("%.2f", saldoActual)
                        + "\nTotal a pagar: $"
                        + String.format("%.2f", total),
                        "Saldo insuficiente",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Confirmar compra?\n"
                    + "Total: $" + String.format("%.2f", total)
                    + "\nSaldo actual: $"
                    + String.format("%.2f", saldoActual)
                    + "\nSaldo después: $"
                    + String.format("%.2f", saldoActual - total)
                    + "\nPuntos a ganar: +" + puntosGanados,
                    "Confirmar compra",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                boolean ok = usuarioDao.descontarSaldo(
                        usuario.getId(), total);

                if (ok) {
                    // Registrar compra en BD
                    int idCompra = usuarioDao.registrarCompra(
                            usuario.getId(), total, puntosGanados);

                    // Registrar detalle de cada video
                    if (idCompra > 0) {
                        for (Map.Entry<Integer, Integer> entry
                                : items.entrySet()) {
                            Video v = videoDao.buscarPorId(entry.getKey());
                            if (v != null) {
                                double sub = v.getPrecio()
                                           * entry.getValue();
                                usuarioDao.registrarDetalleCompra(
                                        idCompra,
                                        entry.getKey(),
                                        entry.getValue(),
                                        sub);
                            }
                        }
                    }

                    // Sumar puntos y vaciar carrito
                    usuarioDao.sumarPuntos(usuario.getId(), puntosGanados);
                    carritoDao.vaciarCarrito(usuario.getId());

                    int puntosActuales = usuarioDao.obtenerPuntos(
                            usuario.getId());

                    String msg = "🎉 ¡Compra realizada con éxito!\n"
                            + "Total pagado: $"
                            + String.format("%.2f", total)
                            + "\nPuntos ganados: +" + puntosGanados
                            + "\nPuntos totales: " + puntosActuales;

                    if (puntosActuales >= 20) {
                        msg += "\n\n🎁 ¡Felicidades! Tienes 20 puntos."
                             + "\n¡Tienes derecho a un video gratis!";
                    }

                    JOptionPane.showMessageDialog(null, msg,
                            "Compra exitosa",
                            JOptionPane.INFORMATION_MESSAGE);

                    cargarCarrito();

                } else {
                    JOptionPane.showMessageDialog(null,
                            "❌ Error al procesar el pago.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargarCarrito();
        setLocationRelativeTo(null);
    }

    private void cargarCarrito() {
        modeloCarrito.clear();

        double saldo = usuarioDao.obtenerSaldo(usuario.getId());
        lblSaldo.setText("Saldo disponible: $"
                + String.format("%.2f", saldo));

        Map<Integer, Integer> items =
                carritoDao.obtenerCarrito(usuario.getId());
        double total = 0;

        if (items.isEmpty()) {
            modeloCarrito.addElement("  El carrito está vacío.");
            lblTotal.setText("Total: $0.00");
            return;
        }

        for (Map.Entry<Integer, Integer> e : items.entrySet()) {
            Video v = videoDao.buscarPorId(e.getKey());
            if (v != null) {
                double sub = v.getPrecio() * e.getValue();
                total += sub;
                modeloCarrito.addElement(
                    "  " + v.getId() + " — "
                    + v.getTituloOriginal()
                    + "   |   x" + e.getValue()
                    + "   |   $" + String.format("%.2f", sub));
            }
        }
        lblTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private int extraerIdVideo(String texto) {
        try {
            return Integer.parseInt(
                    texto.trim().split("—")[0].trim());
        } catch (Exception ex) { return -1; }
    }
}