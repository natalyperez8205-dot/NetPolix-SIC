package vista;

import dao.Clasificacion_Dao;
import modelo.Clasificacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionarClasificacion extends JFrame {

    JTextField txtTipo;
    JTextField txtDescripcion;
    JButton btnGuardar;
    JButton btnEditar;
    JButton btnEliminar;
    JButton btnVolver;
    JTable tabla;
    DefaultTableModel modelo;

    private final Clasificacion_Dao dao = new Clasificacion_Dao();

    public GestionarClasificacion() {

        setTitle("Gestionar Clasificaciones");
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("GESTIÓN DE CLASIFICACIONES");
        lblTitulo.setBounds(150, 15, 300, 25);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(lblTitulo);

        JLabel lblTipo = new JLabel("Tipo (ej: PG-13):");
        lblTipo.setBounds(20, 55, 130, 25);
        add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(155, 55, 150, 25);
        add(txtTipo);

        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setBounds(20, 90, 130, 25);
        add(lblDesc);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(155, 90, 380, 25);
        add(txtDescripcion);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(20, 130, 120, 30);
        add(btnGuardar);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(155, 130, 120, 30);
        add(btnEditar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(290, 130, 120, 30);
        add(btnEliminar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(425, 130, 110, 30);
        add(btnVolver);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Tipo");
        modelo.addColumn("Descripción");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 175, 540, 270);
        add(scroll);

        // al seleccionar fila, carga los datos en los campos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtTipo.setText(modelo.getValueAt(fila, 1).toString());
                txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
            }
        });

        btnGuardar.addActionListener(e -> {
            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El tipo es obligatorio.");
                return;
            }
            Clasificacion c = new Clasificacion();
            c.setTipo(txtTipo.getText().trim());
            c.setDescripcion(txtDescripcion.getText().trim());
            dao.guardarClasificacion(c);
            JOptionPane.showMessageDialog(null, "Clasificación guardada.");
            txtTipo.setText("");
            txtDescripcion.setText("");
            cargar();
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una clasificación.");
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            dao.editarClasificacion(id,
                    txtTipo.getText().trim(),
                    txtDescripcion.getText().trim());
            JOptionPane.showMessageDialog(null, "Clasificación actualizada.");
            txtTipo.setText("");
            txtDescripcion.setText("");
            cargar();
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione una clasificación.");
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar esta clasificación?");
            if (confirm == JOptionPane.YES_OPTION) {
                dao.eliminarClasificacion(id);
                JOptionPane.showMessageDialog(null, "Clasificación eliminada.");
                cargar();
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar();
    }

    private void cargar() {
        modelo.setRowCount(0);
        List<Clasificacion> lista = dao.listarClasificaciones();
        for (Clasificacion c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getTipo(), c.getDescripcion()});
        }
    }
}