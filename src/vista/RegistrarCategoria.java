package vista;

import dao.Categoria_Dao;
import modelo.Categoria;

import javax.swing.*;

public class RegistrarCategoria extends JFrame {

    JLabel lblNombre;
    JTextField txtNombre;
    JButton btnGuardar;
    JButton btnVolver;

    public RegistrarCategoria() {
        setTitle("Registrar Categoría");
        setSize(360, 220);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblNombre = new JLabel("Nombre categoría:");
        lblNombre.setBounds(20, 30, 120, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 180, 25);
        add(txtNombre);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(60, 90, 100, 30);
        add(btnGuardar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(200, 90, 100, 30);
        add(btnVolver);

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un nombre de categoría válido.");
                return;
            }
            Categoria c = new Categoria();
            c.setNombre(nombre.trim());
            Categoria_Dao dao = new Categoria_Dao();
            dao.guardarCategoria(c);
            JOptionPane.showMessageDialog(null, "Categoría guardada correctamente");
            dispose();
        });

        btnVolver.addActionListener(e -> dispose());
    }
}
