package vista;

import dao.Carrito_Dao;
import dao.Video_Dao;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Carrito extends JFrame {

    private Usuario usuario;
    private JList<String> listaCarrito;
    private DefaultListModel<String> modeloCarrito;
    private JButton btnEliminar;
    private JButton btnComprar;
    private JButton btnVolver;

    private final Carrito_Dao carritoDao = new Carrito_Dao();
    private final Video_Dao videoDao = new Video_Dao();

    public Carrito(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Carrito de compras");
        setSize(640,420);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Tu carrito");
        lblTitulo.setBounds(20, 10, 300, 30);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(lblTitulo);

        modeloCarrito = new DefaultListModel<>();
        listaCarrito = new JList<>(modeloCarrito);
        JScrollPane scroll = new JScrollPane(listaCarrito);
        scroll.setBounds(20, 50, 580, 260);
        add(scroll);

        btnEliminar = new JButton("Eliminar selección");
        btnEliminar.setBounds(20, 330, 170, 35);
        add(btnEliminar);

        btnComprar = new JButton("Terminar compra");
        btnComprar.setBounds(210, 330, 170, 35);
        add(btnComprar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(400, 330, 120, 35);
        add(btnVolver);

        btnEliminar.addActionListener(e -> {
            String seleccionado = listaCarrito.getSelectedValue();
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(null,
                        "Seleccione un elemento para eliminar.");
                return;
            }
            int idVideo = obtenerIdSeleccionado(seleccionado);
            if (idVideo > 0) {
                carritoDao.eliminarDelCarrito(usuario.getId(), idVideo);
                cargarCarrito();
            }
        });

        btnComprar.addActionListener(e -> {
            if (modeloCarrito.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El carrito está vacío.");
                return;
            }
         // carritoDao.vaciarCarrito(usuario.getId());
            cargarCarrito();
            JOptionPane.showMessageDialog(null,
                    "Compra realizada. Gracias por su compra.");
        });

        btnVolver.addActionListener(e -> dispose());

        cargarCarrito();
    }

    private void cargarCarrito() {
        modeloCarrito.clear();
        Map<Integer, Integer> items = carritoDao.obtenerCarrito(usuario.getId());
        double total = 0.0;
        if (items.isEmpty()) {
            modeloCarrito.addElement("El carrito está vacío.");
            return;
        }

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Video video = videoDao.buscarPorId(entry.getKey());
            if (video != null) {
                int cantidad = entry.getValue();
                double subtotal = cantidad * video.getPrecio();
                total += subtotal;
                modeloCarrito.addElement(video.getId() + " - " + video.getTituloOriginal()
                        + " | Cantidad: " + cantidad
                        + " | Precio: " + video.getPrecio()
                        + " | Subtotal: " + subtotal);
            }
        }
        modeloCarrito.addElement("--- Total: " + total + " ---");
    }

    private int obtenerIdSeleccionado(String texto) {
        String[] partes = texto.split(" - ");
        if (partes.length > 0) {
            try {
                return Integer.parseInt(partes[0].trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }
}