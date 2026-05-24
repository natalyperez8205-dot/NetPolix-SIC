package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import vista.Login;

public class Menu_Gerente extends JFrame {

    private JButton btnCerrarSesion;

    public Menu_Gerente(){

        setTitle("Menu Gerente");
        setSize(400,330);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Bienvenido Gerente");
        lblTitulo.setBounds(110,90,200,30);
        add(lblTitulo);

        btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setBounds(110,160,160,40);
        add(btnCerrarSesion);

        btnCerrarSesion.addActionListener(e -> {
            Login login = new Login();
            login.setVisible(true);
            dispose();
        });
    }
}