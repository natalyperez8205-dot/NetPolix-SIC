package vista;

import dao.Carrito_Dao;
import dao.Video_Dao;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.Font;
import java.util.List;
import java.util.Map;

public class Catalogo_Video extends JFrame {

    private Usuario usuario;
    private JList<Video> listaVideos;
    private DefaultListModel<Video> modeloVideos;
    private JButton btnAgregarCarrito;
    private JButton btnVerCarrito;
    private JButton btnRefrescar;
    private JButton btnVolver;

    private final Video_Dao videoDao = new Video_Dao();
    private final Carrito_Dao carritoDao = new Carrito_Dao();

    public Catalogo_Video(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Catálogo de películas");
        setSize(700,450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Catálogo disponible");
        lblTitulo.setBounds(20, 10, 300, 30);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(lblTitulo);

        modeloVideos = new DefaultListModel<>();
        listaVideos = new JList<>(modeloVideos);
        listaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaVideos);
        scroll.setBounds(20, 50, 640, 280);
        add(scroll);

        btnAgregarCarrito = new JButton("Agregar al carrito");
        btnAgregarCarrito.setBounds(20, 350, 190, 35);
        add(btnAgregarCarrito);

        btnVerCarrito = new JButton("Ver carrito");
        btnVerCarrito.setBounds(230, 350, 140, 35);
        add(btnVerCarrito);

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBounds(390, 350, 120, 35);
        add(btnRefrescar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(520, 350, 140, 35);
        add(btnVolver);

        btnAgregarCarrito.addActionListener(e -> {
            Video seleccionado = listaVideos.getSelectedValue();
            if (seleccionado == null || seleccionado.getId() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Seleccione una película válida para agregar al carrito.");
                return;
            }
            carritoDao.agregarAlCarrito(usuario.getId(), seleccionado);
            JOptionPane.showMessageDialog(null,
                    "Película agregada al carrito.");
        });

        btnVerCarrito.addActionListener(e -> {
            Carrito carrito = new Carrito(usuario);
            carrito.setVisible(true);
        });

        btnRefrescar.addActionListener(e -> cargarVideos());

        btnVolver.addActionListener(e -> dispose());

        cargarVideos();
    }

    private void cargarVideos() {
        modeloVideos.clear();
        List<Video> videos = videoDao.listarVideos();
        if (videos.isEmpty()) {
            modeloVideos.addElement(new Video(0, "No hay películas", "", "", 0, "", "", 0.0));
            return;
        }
        for (Video video : videos) {
            modeloVideos.addElement(video);
        }
    }
}