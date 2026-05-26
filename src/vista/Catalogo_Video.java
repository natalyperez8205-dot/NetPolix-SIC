package vista;

import dao.Carrito_Dao;
import dao.Video_Dao;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Catalogo_Video extends JFrame {

    private Usuario usuario;
    private DefaultTableModel modelo;
    private JTable tabla;
    private final Video_Dao videoDao = new Video_Dao();
    private final Carrito_Dao carritoDao = new Carrito_Dao();

    // CONSTRUCTOR CLIENTE
    public Catalogo_Video(Usuario usuario) {
        this.usuario = usuario;
        setTitle("NetPOLIx — Catálogo");
        setSize(800, 560);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);
        construirUI(true);
        setLocationRelativeTo(null);
    }

    // CONSTRUCTOR ADMINISTRADOR
    public Catalogo_Video() {
        setTitle("NetPOLIx — Gestión Catálogo");
        setSize(800, 560);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);
        construirUI(false);
        setLocationRelativeTo(null);
    }

    private void construirUI(boolean esCliente) {

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 800, 65);
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

        JLabel lblSub = new JLabel(esCliente
                ? "🎬  Catálogo de películas"
                : "📋  Gestión de catálogo");
        lblSub.setBounds(310, 22, 450, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Categoría");
        modelo.addColumn("Actores");
        modelo.addColumn("Duración");
        modelo.addColumn("Precio");

        tabla = new JTable(modelo);
        Estilos.tabla(tabla);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
        tabla.getColumnModel().getColumn(4).setMaxWidth(80);
        tabla.getColumnModel().getColumn(5).setMaxWidth(80);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 80, 750, 350);
        Estilos.scroll(scroll);
        add(scroll);

        if (esCliente) {

            JButton btnAgregar   = new JButton("🛒 Agregar al carrito");
            btnAgregar.setBounds(20, 448, 210, 42);
            Estilos.botonPrincipal(btnAgregar);
            add(btnAgregar);

            JButton btnCarrito   = new JButton("👁 Ver carrito");
            btnCarrito.setBounds(245, 448, 180, 42);
            Estilos.botonPrincipal(btnCarrito);
            add(btnCarrito);

            JButton btnRefrescar = new JButton("🔄 Refrescar");
            btnRefrescar.setBounds(440, 448, 150, 42);
            Estilos.botonSecundario(btnRefrescar);
            add(btnRefrescar);

            JButton btnVolver    = new JButton("← Volver");
            btnVolver.setBounds(605, 448, 165, 42);
            Estilos.botonSecundario(btnVolver);
            add(btnVolver);

            btnAgregar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Selecciona una película.");
                    return;
                }
                int idVideo = Integer.parseInt(
                        modelo.getValueAt(fila, 0).toString());
                Video v = videoDao.buscarPorId(idVideo);
                if (v != null) {
                    carritoDao.agregarAlCarrito(usuario.getId(), v);
                    JOptionPane.showMessageDialog(null,
                            "✅ \"" + v.getTituloOriginal()
                            + "\" agregada al carrito.");
                }
            });

            btnCarrito.addActionListener(e ->
                    new Carrito(usuario).setVisible(true));

            btnRefrescar.addActionListener(e -> cargarVideos());

            btnVolver.addActionListener(e -> dispose());

        } else {

            JButton btnEliminar  = new JButton("🗑 Eliminar");
            btnEliminar.setBounds(20, 448, 180, 42);
            Estilos.botonPrincipal(btnEliminar);
            btnEliminar.setBackground(new Color(150, 20, 20));
            add(btnEliminar);

            JButton btnEditar    = new JButton("✏ Editar");
            btnEditar.setBounds(215, 448, 180, 42);
            Estilos.botonPrincipal(btnEditar);
            add(btnEditar);

            JButton btnRefrescar = new JButton("🔄 Refrescar");
            btnRefrescar.setBounds(410, 448, 150, 42);
            Estilos.botonSecundario(btnRefrescar);
            add(btnRefrescar);

            JButton btnVolver    = new JButton("← Volver");
            btnVolver.setBounds(605, 448, 165, 42);
            Estilos.botonSecundario(btnVolver);
            add(btnVolver);

            btnEliminar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Selecciona una película.");
                    return;
                }
                String titulo = modelo.getValueAt(fila, 1).toString();
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Eliminar \"" + titulo + "\"?");
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(
                            modelo.getValueAt(fila, 0).toString());
                    videoDao.eliminarVideo(id);
                    JOptionPane.showMessageDialog(null,
                            "Película eliminada.");
                    cargarVideos();
                }
            });

            btnEditar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Selecciona una película.");
                    return;
                }

                int id = Integer.parseInt(
                        modelo.getValueAt(fila, 0).toString());
                String titulo    = modelo.getValueAt(fila, 1).toString();
                String catActual = modelo.getValueAt(fila, 2).toString();
                String actores   = modelo.getValueAt(fila, 3).toString()
                        .equals("(Sin actores)") ? "" :
                         modelo.getValueAt(fila, 3).toString();

                // Limpiar duración y precio correctamente
                String durStr = modelo.getValueAt(fila, 4).toString()
                        .replace(" min", "").trim();
                String precStr = modelo.getValueAt(fila, 5).toString()
                        .replace("$", "").replace(",", ".").trim();

                // Panel edición
                JPanel panel = new JPanel(null);
                panel.setPreferredSize(new Dimension(420, 280));
                panel.setBackground(Estilos.FONDO);

                agregarLabelPanel(panel, "Título:", 10, 5);
                JTextField txtTit = new JTextField(titulo);
                txtTit.setBounds(10, 25, 390, 32);
                Estilos.campo(txtTit);
                panel.add(txtTit);

                agregarLabelPanel(panel, "Categoría:", 10, 68);
                JTextField txtCat = new JTextField(
                        catActual.equals("(Sin categoría)") ? "" : catActual);
                txtCat.setBounds(10, 88, 190, 32);
                Estilos.campo(txtCat);
                panel.add(txtCat);

                agregarLabelPanel(panel, "Actores:", 215, 68);
                JTextField txtAct = new JTextField(actores);
                txtAct.setBounds(215, 88, 185, 32);
                Estilos.campo(txtAct);
                panel.add(txtAct);

                agregarLabelPanel(panel, "Duración (min):", 10, 132);
                JTextField txtDur = new JTextField(durStr);
                txtDur.setBounds(10, 152, 150, 32);
                Estilos.campo(txtDur);
                panel.add(txtDur);

                agregarLabelPanel(panel, "Precio ($):", 215, 132);
                JTextField txtPrec = new JTextField(precStr);
                txtPrec.setBounds(215, 152, 185, 32);
                Estilos.campo(txtPrec);
                panel.add(txtPrec);

                int result = JOptionPane.showConfirmDialog(null,
                        panel, "Editar película",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String nuevoTitulo = txtTit.getText().trim();
                        String nuevaCat    = txtCat.getText().trim();
                        String nuevosAct   = txtAct.getText().trim();

                        int nuevaDur = Integer.parseInt(
                                txtDur.getText().trim()
                                      .replace(" min", ""));

                        double nuevoPrecio = Double.parseDouble(
                                txtPrec.getText().trim()
                                       .replace("$", "")
                                       .replace(",", "."));

                        if (nuevoTitulo.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "El título no puede estar vacío.");
                            return;
                        }

                        videoDao.editarVideo(id, nuevoTitulo,
                                nuevaCat, nuevosAct,
                                nuevaDur, nuevoPrecio);

                        JOptionPane.showMessageDialog(null,
                                "✅ Película actualizada correctamente.");
                        cargarVideos();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Duración y precio deben ser numéricos.\n"
                                + "Duración: solo números (ej: 120)\n"
                                + "Precio: solo números (ej: 9.99)");
                    }
                }
            });

            btnRefrescar.addActionListener(e -> cargarVideos());
            btnVolver.addActionListener(e -> dispose());
        }

        cargarVideos();
    }

    private void agregarLabelPanel(JPanel panel, String texto,
                                    int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 200, 18);
        lbl.setFont(Estilos.FUENTE_SUBTIT);
        lbl.setForeground(Estilos.TEXTO);
        panel.add(lbl);
    }

    private void cargarVideos() {
        modelo.setRowCount(0);
        List<Video> lista = videoDao.listarVideos();
        if (lista.isEmpty()) {
            modelo.addRow(new Object[]{
                "-", "No hay películas", "-", "-", "-", "-"
            });
            return;
        }
        for (Video v : lista) {
            modelo.addRow(new Object[]{
                v.getId(),
                v.getTituloOriginal() == null
                    || v.getTituloOriginal().isEmpty()
                    ? "(Sin título)" : v.getTituloOriginal(),
                v.getCategoria() == null
                    || v.getCategoria().isEmpty()
                    ? "(Sin categoría)" : v.getCategoria(),
                v.getActores() == null
                    || v.getActores().isEmpty()
                    ? "(Sin actores)" : v.getActores(),
                v.getDuracion() + " min",
                "$" + String.format("%.2f", v.getPrecio())
            });
        }
    }
}