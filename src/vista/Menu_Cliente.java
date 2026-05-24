package vista;

import modelo.Usuario;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import vista.Login;

public class Menu_Cliente extends JFrame {

    private Usuario usuario;
    private JLabel lblTitulo;
    private JButton btnVerCatalogo;
    private JButton btnVerCarrito;
    private JButton btnCerrarSesion;

    public Menu_Cliente(Usuario usuario){

        this.usuario = usuario;

        setTitle("Menu Cliente");
        setSize(500,350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("Bienvenido " + usuario.getNombre());
        lblTitulo.setBounds(120,60,300,30);
        add(lblTitulo);

        btnVerCatalogo = new JButton("Ver catálogo");
        btnVerCatalogo.setBounds(140,130,200,40);
        add(btnVerCatalogo);

        btnVerCarrito = new JButton("Ver carrito");
        btnVerCarrito.setBounds(140,190,200,40);
        add(btnVerCarrito);

        btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBounds(140,250,200,40);
        add(btnCerrarSesion);

        btnVerCatalogo.addActionListener(e -> {
            Catalogo_Video catalogo = new Catalogo_Video(usuario);
            catalogo.setVisible(true);
        });

        btnVerCarrito.addActionListener(e -> {
            Carrito carrito = new Carrito(usuario);
            carrito.setVisible(true);
        });

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}