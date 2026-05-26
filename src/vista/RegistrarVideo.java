package vista;

import dao.Video_Dao;
import modelo.Video;
import javax.swing.*;

public class RegistrarVideo extends JFrame {

    JLabel lblTitulo;
    JLabel lblCategoria;
    JLabel lblActores;
    JLabel lblDuracion;
    JLabel lblEdadRestriccion;
    JLabel lblIdioma;
    JLabel lblPrecio;

    JTextField txtTitulo;
    JTextField txtCategoria;
    JTextField txtActores;
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

        txtCategoria = new JTextField();
        txtCategoria.setBounds(180,90,260,25);
        add(txtCategoria);

        lblActores = new JLabel("Actores:");
        lblActores.setBounds(40,140,120,25);
        add(lblActores);

        txtActores = new JTextField();
        txtActores.setBounds(180,140,260,25);
        add(txtActores);

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
                video.setCategoria(txtCategoria.getText());
                video.setActores(txtActores.getText());
                video.setDuracion(Integer.parseInt(txtDuracion.getText()));
                video.setEdadRestriccion(
                	    Integer.parseInt(
                	        txtEdadRestriccion.getText()));
                video.setIdioma(txtIdioma.getText());
                video.setPrecio(Double.parseDouble(txtPrecio.getText()));

                Video_Dao dao = new Video_Dao();
                dao.guardarVideo(video);

                JOptionPane.showMessageDialog(null,
                        "Película registrada correctamente");

                txtTitulo.setText("");
                txtCategoria.setText("");
                txtActores.setText("");
                txtDuracion.setText("");
                txtEdadRestriccion.setText("");
                txtIdioma.setText("");
                txtPrecio.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Duración y precio deben ser numéricos.");
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }
}
