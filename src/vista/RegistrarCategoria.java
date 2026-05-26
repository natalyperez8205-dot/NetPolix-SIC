package vista;

import dao.Categoria_Dao;
import modelo.Categoria;
import javax.swing.*;
import java.awt.*;

public class RegistrarCategoria extends JFrame {

    JTextField txtNombre;

    public RegistrarCategoria() {

        setTitle("NetPOLIx — Nueva Categoría");
        setSize(440, 320);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 440, 65);
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

        JLabel lblSub = new JLabel("Nueva categoría");
        lblSub.setBounds(255, 25, 170, 20);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        JLabel lblNombre = new JLabel("Nombre de la categoría:");
        lblNombre.setBounds(30, 90, 220, 18);
        lblNombre.setFont(Estilos.FUENTE_SUBTIT);
        lblNombre.setForeground(Estilos.TEXTO_GRIS);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(30, 112, 370, 38);
        Estilos.campo(txtNombre);
        add(txtNombre);

        JButton btnGuardar = new JButton("✅  GUARDAR CATEGORÍA");
        btnGuardar.setBounds(30, 175, 370, 44);
        Estilos.botonPrincipal(btnGuardar);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnGuardar);

        JButton btnVolver = new JButton("← Cancelar");
        btnVolver.setBounds(30, 230, 370, 38);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Ingresa un nombre de categoría.",
                        "Campo vacío",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Categoria c = new Categoria();
            c.setNombre(nombre);
            new Categoria_Dao().guardarCategoria(c);
            JOptionPane.showMessageDialog(null,
                    "✅ Categoría \"" + nombre + "\" guardada.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnVolver.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }
}