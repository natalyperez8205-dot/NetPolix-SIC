package vista;

import dao.Categoria_Dao;
import dao.Usuario_Dao;
import dao.Video_Dao;
import modelo.Categoria;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistrarVideo extends JFrame {

    JTextField txtTitulo;
    JTextField txtDuracion;
    JTextField txtPrecio;
    JTextField txtIdioma;
    JTextField txtEdad;
    JComboBox<String> cbCategoria;
    JComboBox<String> cbActores;

    public RegistrarVideo() {

        setTitle("NetPOLIx — Registrar Video");
        setSize(560, 640);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 560, 65);
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

        JLabel lblSub = new JLabel("🎬  Registrar nueva película");
        lblSub.setBounds(265, 22, 270, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // CAMPOS
        int xLabel = 25;
        int xField = 25;
        int w = 500;

        // TÍTULO
        agregarLabel("Título de la película:", xLabel, 82);
        txtTitulo = new JTextField();
        txtTitulo.setBounds(xField, 102, w, 36);
        Estilos.campo(txtTitulo);
        add(txtTitulo);

        // CATEGORÍA
        agregarLabel("Categoría:", xLabel, 152);
        cbCategoria = new JComboBox<>();
        cbCategoria.setBounds(xField, 172, 340, 36);
        Estilos.combo(cbCategoria);
        add(cbCategoria);

        JButton btnNuevaCat = new JButton("➕ Nueva");
        btnNuevaCat.setBounds(375, 172, 150, 36);
        Estilos.botonSecundario(btnNuevaCat);
        add(btnNuevaCat);

        // ACTORES
        agregarLabel("Actor principal:", xLabel, 222);
        cbActores = new JComboBox<>();
        cbActores.setBounds(xField, 242, 340, 36);
        Estilos.combo(cbActores);
        add(cbActores);

        JButton btnNuevoActor = new JButton("➕ Nuevo");
        btnNuevoActor.setBounds(375, 242, 150, 36);
        Estilos.botonSecundario(btnNuevoActor);
        add(btnNuevoActor);

        // DURACIÓN Y PRECIO
        agregarLabel("Duración (min):", xLabel, 292);
        txtDuracion = new JTextField();
        txtDuracion.setBounds(xField, 312, 235, 36);
        Estilos.campo(txtDuracion);
        add(txtDuracion);

        agregarLabel("Precio ($):", 290, 292);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(290, 312, 235, 36);
        Estilos.campo(txtPrecio);
        add(txtPrecio);

        // IDIOMA Y EDAD
        agregarLabel("Idioma:", xLabel, 362);
        txtIdioma = new JTextField();
        txtIdioma.setBounds(xField, 382, 235, 36);
        Estilos.campo(txtIdioma);
        add(txtIdioma);

        agregarLabel("Clasificación edad:", 290, 362);
        txtEdad = new JTextField();
        txtEdad.setBounds(290, 382, 235, 36);
        Estilos.campo(txtEdad);
        add(txtEdad);

        // BOTONES
        JButton btnGuardar = new JButton("💾  GUARDAR PELÍCULA");
        btnGuardar.setBounds(25, 445, 500, 46);
        Estilos.botonPrincipal(btnGuardar);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 15));
        add(btnGuardar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(25, 502, 500, 38);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        btnNuevaCat.addActionListener(e -> {
            new RegistrarCategoria().setVisible(true);
        });

        btnNuevoActor.addActionListener(e -> {
            new RegistrarActor().setVisible(true);
        });

        JButton btnRefCat = new JButton("🔄");
        btnRefCat.setBounds(375, 210, 150, 25);
        Estilos.botonSecundario(btnRefCat);
        btnRefCat.setFont(new Font("SansSerif", Font.PLAIN, 11));
        add(btnRefCat);

        JButton btnRefAct = new JButton("🔄");
        btnRefAct.setBounds(375, 280, 150, 25);
        Estilos.botonSecundario(btnRefAct);
        btnRefAct.setFont(new Font("SansSerif", Font.PLAIN, 11));
        add(btnRefAct);

        btnRefCat.addActionListener(e -> cargarCategorias());
        btnRefAct.addActionListener(e -> cargarActores());

        btnGuardar.addActionListener(e -> {
            if (txtTitulo.getText().trim().isEmpty()
                    || txtDuracion.getText().trim().isEmpty()
                    || txtPrecio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Título, duración y precio son obligatorios.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Video video = new Video();
                video.setTituloOriginal(txtTitulo.getText().trim());
                video.setCategoria(cbCategoria.getSelectedItem() != null
                        ? cbCategoria.getSelectedItem().toString() : "");
                video.setActores(cbActores.getSelectedItem() != null
                        ? cbActores.getSelectedItem().toString() : "");
                video.setDuracion(Integer.parseInt(txtDuracion.getText().trim()));
                video.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
                video.setIdioma(txtIdioma.getText().trim());
                video.setEdadRestriccion(
                        txtEdad.getText().trim().isEmpty() ? 0
                        : Integer.parseInt(txtEdad.getText().trim()));

                new Video_Dao().guardarVideo(video);

                JOptionPane.showMessageDialog(null,
                        "✅ Película registrada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                limpiar();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Duración y precio deben ser numéricos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargarCategorias();
        cargarActores();
        setLocationRelativeTo(null);
    }

    private void agregarLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 250, 18);
        lbl.setFont(Estilos.FUENTE_SUBTIT);
        lbl.setForeground(Estilos.TEXTO_GRIS);
        add(lbl);
    }

    private void cargarCategorias() {
        cbCategoria.removeAllItems();
        try {
            for (Categoria c : new Categoria_Dao().listarCategorias())
                cbCategoria.addItem(c.getNombre());
        } catch (Exception e) {
            System.out.println("Error categorias: " + e.getMessage());
        }
    }

    private void cargarActores() {
        cbActores.removeAllItems();
        try {
            for (Usuario u : new Usuario_Dao().listarActores())
                cbActores.addItem(u.getNombre());
        } catch (Exception e) {
            System.out.println("Error actores: " + e.getMessage());
        }
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtDuracion.setText("");
        txtPrecio.setText("");
        txtIdioma.setText("");
        txtEdad.setText("");
    }
}