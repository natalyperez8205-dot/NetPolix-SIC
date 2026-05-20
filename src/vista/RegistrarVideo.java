package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegistrarVideo extends JFrame {

    JLabel lblTitulo;
    JLabel lblCategoria;
    JLabel lblPrecio;
    JLabel lblDuracion;

    JTextField txtTitulo;
    JTextField txtCategoria;
    JTextField txtPrecio;
    JTextField txtDuracion;

    JButton btnGuardar;

    public RegistrarVideo(){

        setTitle("Registrar Video");
        setSize(500,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(40,40,100,25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(160,40,200,25);
        add(txtTitulo);

        lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(40,90,100,25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(160,90,200,25);
        add(txtCategoria);

        lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(40,140,100,25);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(160,140,200,25);
        add(txtPrecio);

        lblDuracion = new JLabel("Duración:");
        lblDuracion.setBounds(40,190,100,25);
        add(lblDuracion);

        txtDuracion = new JTextField();
        txtDuracion.setBounds(160,190,200,25);
        add(txtDuracion);

        btnGuardar = new JButton("Guardar");

        btnGuardar.setBounds(170,260,140,35);

        add(btnGuardar);

    }
}

