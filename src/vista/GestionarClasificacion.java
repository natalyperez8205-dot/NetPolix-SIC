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
    JTable tabla;
    DefaultTableModel modelo;

    public GestionarClasificacion() {

        setTitle("NetPOLIx — Clasificaciones");
        setSize(650, 560);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        Clasificacion_Dao dao = new Clasificacion_Dao();

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 650, 65);
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

        JLabel lblSub = new JLabel("🔞  Gestión de clasificaciones");
        lblSub.setBounds(280, 22, 340, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // CAMPOS
        JLabel lblTipo = new JLabel("Tipo (ej: PG-13):");
        lblTipo.setBounds(20, 82, 150, 18);
        lblTipo.setFont(Estilos.FUENTE_SUBTIT);
        lblTipo.setForeground(Estilos.TEXTO_GRIS);
        add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(20, 103, 150, 36);
        Estilos.campo(txtTipo);
        add(txtTipo);

        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setBounds(185, 82, 120, 18);
        lblDesc.setFont(Estilos.FUENTE_SUBTIT);
        lblDesc.setForeground(Estilos.TEXTO_GRIS);
        add(lblDesc);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(185, 103, 330, 36);
        Estilos.campo(txtDescripcion);
        add(txtDescripcion);

        JButton btnGuardar = new JButton("➕");
        btnGuardar.setBounds(530, 103, 90, 36);
        Estilos.botonPrincipal(btnGuardar);
        add(btnGuardar);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Tipo");
        modelo.addColumn("Descripción");

        tabla = new JTable(modelo);
        Estilos.tabla(tabla);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtTipo.setText(modelo.getValueAt(fila, 1).toString());
                txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 155, 600, 290);
        Estilos.scroll(scroll);
        add(scroll);

        // BOTONES
        JButton btnEditar   = new JButton("✏ Editar");
        btnEditar.setBounds(20, 460, 160, 40);
        Estilos.botonPrincipal(btnEditar);
        add(btnEditar);

        JButton btnEliminar = new JButton("🗑 Eliminar");
        btnEliminar.setBounds(195, 460, 160, 40);
        Estilos.botonPrincipal(btnEliminar);
        btnEliminar.setBackground(new Color(150, 20, 20));
        add(btnEliminar);

        JButton btnVolver   = new JButton("← Volver");
        btnVolver.setBounds(470, 460, 150, 40);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        btnGuardar.addActionListener(e -> {
            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El tipo es obligatorio.");
                return;
            }
            Clasificacion c = new Clasificacion();
            c.setTipo(txtTipo.getText().trim());
            c.setDescripcion(txtDescripcion.getText().trim());
            dao.guardarClasificacion(c);
            JOptionPane.showMessageDialog(null, "✅ Clasificación guardada.");
            limpiar();
            cargar(dao);
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación.");
                return;
            }
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            dao.editarClasificacion(id,
                    txtTipo.getText().trim(),
                    txtDescripcion.getText().trim());
            JOptionPane.showMessageDialog(null, "✅ Clasificación actualizada.");
            limpiar();
            cargar(dao);
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una clasificación.");
                return;
            }
            String tipo = modelo.getValueAt(fila, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar la clasificación \"" + tipo + "\"?");
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                dao.eliminarClasificacion(id);
                JOptionPane.showMessageDialog(null, "Clasificación eliminada.");
                limpiar();
                cargar(dao);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar(dao);
        setLocationRelativeTo(null);
    }

    private void cargar(Clasificacion_Dao dao) {
        modelo.setRowCount(0);
        for (Clasificacion c : dao.listarClasificaciones())
            modelo.addRow(new Object[]{c.getId(), c.getTipo(), c.getDescripcion()});
    }

    private void limpiar() {
        txtTipo.setText("");
        txtDescripcion.setText("");
    }
}