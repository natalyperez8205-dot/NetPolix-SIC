package vista;

import dao.Idioma_Dao;
import modelo.Idioma;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionarIdioma extends JFrame {

    JTextField txtIdioma;
    JTable tabla;
    DefaultTableModel modelo;

    public GestionarIdioma() {

        setTitle("NetPOLIx — Idiomas");
        setSize(580, 520);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        Idioma_Dao dao = new Idioma_Dao();

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 580, 65);
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

        JLabel lblSub = new JLabel("🌐  Gestión de idiomas");
        lblSub.setBounds(270, 22, 280, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // CAMPO
        JLabel lblNombre = new JLabel("Nombre del idioma:");
        lblNombre.setBounds(20, 82, 180, 18);
        lblNombre.setFont(Estilos.FUENTE_SUBTIT);
        lblNombre.setForeground(Estilos.TEXTO_GRIS);
        add(lblNombre);

        txtIdioma = new JTextField();
        txtIdioma.setBounds(20, 103, 320, 36);
        Estilos.campo(txtIdioma);
        add(txtIdioma);

        JButton btnGuardar = new JButton("➕ Agregar");
        btnGuardar.setBounds(355, 103, 190, 36);
        Estilos.botonPrincipal(btnGuardar);
        add(btnGuardar);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Idioma");

        tabla = new JTable(modelo);
        Estilos.tabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 155, 525, 270);
        Estilos.scroll(scroll);
        add(scroll);

        // BOTONES
        JButton btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBounds(20, 440, 160, 40);
        Estilos.botonPrincipal(btnEliminar);
        btnEliminar.setBackground(new Color(150, 20, 20));
        add(btnEliminar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(390, 440, 155, 40);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        btnGuardar.addActionListener(e -> {
            String nombre = txtIdioma.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresa un idioma.");
                return;
            }
            Idioma idioma = new Idioma();
            idioma.setNombre(nombre);
            dao.guardarIdioma(idioma);
            JOptionPane.showMessageDialog(null, "✅ Idioma guardado.");
            txtIdioma.setText("");
            cargar(dao);
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona un idioma.");
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            String nombre = modelo.getValueAt(fila, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar el idioma \"" + nombre + "\"?");
            if (confirm == JOptionPane.YES_OPTION) {
                dao.eliminarIdioma(id);
                JOptionPane.showMessageDialog(null, "Idioma eliminado.");
                cargar(dao);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar(dao);
        setLocationRelativeTo(null);
    }

    private void cargar(Idioma_Dao dao) {
        modelo.setRowCount(0);
        for (Idioma i : dao.listarIdiomas())
            modelo.addRow(new Object[]{i.getId(), i.getNombre()});
    }
}