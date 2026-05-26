package vista;

import dao.Usuario_Dao;
import modelo.Usuario;

import javax.swing.*;
import java.time.LocalDate;

public class RegistrarActor extends JFrame {

    JLabel lblNombre;
    JLabel lblCedula;

    JTextField txtNombre;
    JTextField txtCedula;

    JButton btnRegistrar;
    JButton btnVolver;

    public RegistrarActor() {
        setTitle("Registrar Actor");
        setSize(420, 260);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 220, 25);
        add(txtNombre);

        lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(30, 70, 100, 25);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(140, 70, 220, 25);
        add(txtCedula);

        btnRegistrar = new JButton("Registrar Actor");
        btnRegistrar.setBounds(140, 130, 150, 30);
        add(btnRegistrar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(300, 130, 100, 30);
        add(btnVolver);

        btnRegistrar.addActionListener(e -> {
            Usuario usuario = new Usuario();
            usuario.setNombre(txtNombre.getText());
            usuario.setCedula(txtCedula.getText());
            usuario.setCorreo(txtCedula.getText() + "@actor.netpolix");
            usuario.setContrasena("actor");
            usuario.setRol("ACTOR");
            usuario.setFechaIngreso(LocalDate.now().toString());
            usuario.setPuntos(0);
            usuario.setSaldo(0);
            usuario.setIdReferido(0);

            Usuario_Dao dao = new Usuario_Dao();
            dao.guardarUsuario(usuario);

            JOptionPane.showMessageDialog(null, "Actor registrado correctamente");
            dispose();
        });

        btnVolver.addActionListener(e -> dispose());
    }
}
