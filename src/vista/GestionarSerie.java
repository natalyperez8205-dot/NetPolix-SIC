package vista;

import dao.Serie_Dao;
import modelo.Serie;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionarSerie extends JFrame {

    JTextField txtTitulo;
    JTextField txtTemporada;
    JButton btnGuardar;
    JButton btnEditar;
    JButton btnEliminar;
    JButton btnVolver;
    JTable tabla;
    DefaultTableModel modelo;

    private final Serie_Dao dao = new Serie_Dao();

    public GestionarSerie() {

        setTitle("Gestionar Series");
        setSize(580, 480);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("GESTIÓN DE SERIES");
        lblTitulo.setBounds(190, 15, 250, 25);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(lblTitulo);

        JLabel lblTit = new JLabel("Título:");
        lblTit.setBounds(20, 55, 100, 25);
        add(lblTit);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(130, 55, 380, 25);
        add(txtTitulo);

        JLabel lblTemp = new JLabel("Temporada:");
        lblTemp.setBounds(20, 90, 100, 25);
        add(lblTemp);

        txtTemporada = new JTextField();
        txtTemporada.setBounds(130, 90, 150, 25);
        add(txtTemporada);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 130, 110, 30);
        add(btnGuardar);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(145, 130, 110, 30);
        add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(270, 130, 110, 30);
        add(btnEliminar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(395, 130, 110, 30);
        add(btnVolver);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Temporada");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 175, 520, 255);
        add(scroll);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
                txtTemporada.setText(modelo.getValueAt(fila, 2).toString());
            }
        });

        btnGuardar.addActionListener(e -> {
            if (txtTitulo.getText().trim().isEmpty()
                    || txtTemporada.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }
            try {
                Serie s = new Serie();
                s.setTitulo(txtTitulo.getText().trim());
                s.setTemporada(Integer.parseInt(txtTemporada.getText().trim()));
                dao.guardarSerie(s);
                JOptionPane.showMessageDialog(null, "Serie guardada.");
                txtTitulo.setText("");
                txtTemporada.setText("");
                cargar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La temporada debe ser un número.");
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una serie.");
                return;
            }
            try {
                int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                dao.editarSerie(id,
                        txtTitulo.getText().trim(),
                        Integer.parseInt(txtTemporada.getText().trim()));
                JOptionPane.showMessageDialog(null, "Serie actualizada.");
                txtTitulo.setText("");
                txtTemporada.setText("");
                cargar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La temporada debe ser un número.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una serie.");
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar esta serie?");
            if (confirm == JOptionPane.YES_OPTION) {
                dao.eliminarSerie(id);
                JOptionPane.showMessageDialog(null, "Serie eliminada.");
                cargar();
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar();
    }

    private void cargar() {
        modelo.setRowCount(0);
        List<Serie> lista = dao.listarSeries();
        for (Serie s : lista) {
            modelo.addRow(new Object[]{s.getId(), s.getTitulo(), s.getTemporada()});
        }
    }
}