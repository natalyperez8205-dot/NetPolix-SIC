package vista;

import modelo.Usuario;
import javax.swing.*;

public class Menu_Cliente extends JFrame {

    private Usuario usuario;
    private JLabel lblTitulo;
    private JButton btnVerCatalogo;
    private JButton btnVerCarrito;
    private JButton btnCalificar;
    private JButton btnCerrarSesion;

    public Menu_Cliente(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Menu Cliente");
        setSize(500, 420);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("Bienvenido " + usuario.getNombre());
        lblTitulo.setBounds(120, 50, 300, 30);
        add(lblTitulo);

        btnVerCatalogo = new JButton("Ver catálogo");
        btnVerCatalogo.setBounds(140, 110, 200, 40);
        add(btnVerCatalogo);

        btnVerCarrito = new JButton("Ver carrito");
        btnVerCarrito.setBounds(140, 170, 200, 40);
        add(btnVerCarrito);

        btnCalificar = new JButton("Calificar película");
        btnCalificar.setBounds(140, 230, 200, 40);
        add(btnCalificar);

        btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBounds(140, 310, 200, 40);
        add(btnCerrarSesion);

        btnVerCatalogo.addActionListener(e -> {
            Catalogo_Video catalogo = new Catalogo_Video(usuario);
            catalogo.setVisible(true);
        });

        btnVerCarrito.addActionListener(e -> {
            Carrito carrito = new Carrito(usuario);
            carrito.setVisible(true);
        });

        btnCalificar.addActionListener(e -> {
            CalificarVideo ventana = new CalificarVideo(usuario);
            ventana.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}