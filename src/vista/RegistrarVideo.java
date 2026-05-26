package vista;

import dao.Video_Dao;
import dao.Categoria_Dao;
import dao.Usuario_Dao;
import modelo.Video;
import modelo.Categoria;
import modelo.Usuario;
import javax.swing.*;
import java.util.List;

public class RegistrarVideo extends JFrame {

    JLabel lblTitulo;
    JLabel lblCategoria;
    JLabel lblActores;
    JLabel lblDuracion;
    JLabel lblEdadRestriccion;
    JLabel lblIdioma;
    JLabel lblPrecio;

    JTextField txtTitulo;
    JComboBox<String> cbCategoria;
    JComboBox<String> cbActores;
    JTextField txtDuracion;
    JTextField txtEdadRestriccion;
    JTextField txtIdioma;
    JTextField txtPrecio;

    JButton btnGuardar;
    JButton btnVolver;

    public RegistrarVideo(){

        setTitle("Registrar Video");
        setSize(520,540);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(40,40,120,25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(180,40,260,25);
        add(txtTitulo);

        lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(40,90,120,25);
        add(lblCategoria);

        cbCategoria = new JComboBox<>();
        cbCategoria.setBounds(180,90,200,25);
        add(cbCategoria);

        JButton btnNuevaCategoria = new JButton("Nueva categoría");
        btnNuevaCategoria.setBounds(390,90,140,25);
        add(btnNuevaCategoria);

        lblActores = new JLabel("Actores:");
        lblActores.setBounds(40,140,120,25);
        add(lblActores);

        cbActores = new JComboBox<>();
        cbActores.setBounds(180,140,200,25);
        add(cbActores);

        JButton btnNuevoActor = new JButton("Registrar actor");
        btnNuevoActor.setBounds(390,140,140,25);
        add(btnNuevoActor);

        lblDuracion = new JLabel("Duración (min):");
        lblDuracion.setBounds(40,190,120,25);
        add(lblDuracion);

        txtDuracion = new JTextField();
        txtDuracion.setBounds(180,190,260,25);
        add(txtDuracion);

        lblEdadRestriccion = new JLabel("Edad restricción:");
        lblEdadRestriccion.setBounds(40,240,120,25);
        add(lblEdadRestriccion);

        txtEdadRestriccion = new JTextField();
        txtEdadRestriccion.setBounds(180,240,260,25);
        add(txtEdadRestriccion);

        lblIdioma = new JLabel("Idioma:");
        lblIdioma.setBounds(40,290,120,25);
        add(lblIdioma);

        txtIdioma = new JTextField();
        txtIdioma.setBounds(180,290,260,25);
        add(txtIdioma);

        lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(40,340,120,25);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(180,340,260,25);
        add(txtPrecio);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(190,400,140,35);
        add(btnGuardar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(190,445,140,35);
        add(btnVolver);

        btnGuardar.addActionListener(e -> {
            try {
                Video video = new Video();
                video.setTituloOriginal(txtTitulo.getText());
                String categoria = (String) cbCategoria.getSelectedItem();
                String actor = (String) cbActores.getSelectedItem();
                video.setCategoria(categoria != null ? categoria : "");
                video.setActores(actor != null ? actor : "");
                video.setDuracion(Integer.parseInt(txtDuracion.getText()));
                video.setEdadRestriccion(txtEdadRestriccion.getText());
                video.setIdioma(txtIdioma.getText());
                video.setPrecio(Double.parseDouble(txtPrecio.getText()));

                Video_Dao dao = new Video_Dao();
                dao.guardarVideo(video);

                JOptionPane.showMessageDialog(null,
                        "Película registrada correctamente");

                txtTitulo.setText("");
                txtDuracion.setText("");
                txtEdadRestriccion.setText("");
                txtIdioma.setText("");
                txtPrecio.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Duración y precio deben ser numéricos.");
            }
        });

        // populate combo boxes
        cargarCategorias();
        cargarActores();

        btnNuevoActor.addActionListener(a -> {
            RegistrarActor ra = new RegistrarActor();
            ra.setVisible(true);
        });

        btnNuevaCategoria.addActionListener(a -> {
            RegistrarCategoria rc = new RegistrarCategoria();
            rc.setVisible(true);
        });

        // buttons to refresh lists
        JButton btnRefCats = new JButton("Refrescar cat.");
        btnRefCats.setBounds(390,120,140,20);
        add(btnRefCats);
        btnRefCats.addActionListener(a -> cargarCategorias());

        JButton btnRefAct = new JButton("Refrescar act.");
        btnRefAct.setBounds(390,160,140,20);
        add(btnRefAct);
        btnRefAct.addActionListener(a -> cargarActores());

        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarCategorias() {
        cbCategoria.removeAllItems();
        try {
            Categoria_Dao dao = new Categoria_Dao();
            List<Categoria> cats = dao.listarCategorias();
            for (Categoria c : cats) {
                cbCategoria.addItem(c.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error cargando categorias: " + e.getMessage());
        }
    }

    private void cargarActores() {
        cbActores.removeAllItems();
        try {
            Usuario_Dao dao = new Usuario_Dao();
            List<Usuario> actores = dao.listarActores();
            for (Usuario u : actores) {
                cbActores.addItem(u.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error cargando actores: " + e.getMessage());
        }
    }
}
