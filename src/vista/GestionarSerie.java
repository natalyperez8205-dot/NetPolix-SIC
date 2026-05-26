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
    JTable tabla;
    DefaultTableModel modelo;

    public GestionarSerie() {

        setTitle("NetPOLIx — Series");
        setSize(620, 560);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        Serie_Dao dao = new Serie_Dao();

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 620, 65);
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

        JLabel lblSub = new JLabel("📺  Gestión de series");
        lblSub.setBounds(290, 22, 300, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // CAMPOS
        JLabel lblT = new JLabel("Título:");
        lblT.setBounds(20, 82, 80, 18);
        lblT.setFont(Estilos.FUENTE_SUBTIT);
        lblT.setForeground(Estilos.TEXTO_GRIS);
        add(lblT);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(20, 103, 360, 36);
        Estilos.campo(txtTitulo);
        add(txtTitulo);

        JLabel lblTmp = new JLabel("Temporada:");
        lblTmp.setBounds(395, 82, 100, 18);
        lblTmp.setFont(Estilos.FUENTE_SUBTIT);
        lblTmp.setForeground(Estilos.TEXTO_GRIS);
        add(lblTmp);

        txtTemporada = new JTextField();
        txtTemporada.setBounds(395, 103, 80, 36);
        Estilos.campo(txtTemporada);
        add(txtTemporada);

        JButton btnGuardar = new JButton("➕ Guardar");
        btnGuardar.setBounds(490, 103, 105, 36);
        Estilos.botonPrincipal(btnGuardar);
        add(btnGuardar);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Temporada");

        tabla = new JTable(modelo);
        Estilos.tabla(tabla);

        // al seleccionar fila carga en campos
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
                txtTemporada.setText(modelo.getValueAt(fila, 2).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 155, 565, 290);
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
        btnVolver.setBounds(430, 460, 155, 40);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        btnGuardar.addActionListener(e -> {
            if (txtTitulo.getText().trim().isEmpty()
                    || txtTemporada.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Completa todos los campos.");
                return;
            }
            try {
                Serie s = new Serie();
                s.setTitulo(txtTitulo.getText().trim());
                s.setTemporada(Integer.parseInt(txtTemporada.getText().trim()));
                dao.guardarSerie(s);
                JOptionPane.showMessageDialog(null, "✅ Serie guardada.");
                limpiar();
                cargar(dao);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La temporada debe ser un número.");
            }
        });

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una serie.");
                return;
            }
            try {
                int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                dao.editarSerie(id,
                        txtTitulo.getText().trim(),
                        Integer.parseInt(txtTemporada.getText().trim()));
                JOptionPane.showMessageDialog(null, "✅ Serie actualizada.");
                limpiar();
                cargar(dao);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La temporada debe ser un número.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una serie.");
                return;
            }
            String titulo = modelo.getValueAt(fila, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar \"" + titulo + "\"?");
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
                dao.eliminarSerie(id);
                JOptionPane.showMessageDialog(null, "Serie eliminada.");
                limpiar();
                cargar(dao);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar(dao);
        setLocationRelativeTo(null);
    }

    private void cargar(Serie_Dao dao) {
        modelo.setRowCount(0);
        for (Serie s : dao.listarSeries())
            modelo.addRow(new Object[]{s.getId(), s.getTitulo(), s.getTemporada()});
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtTemporada.setText("");
    }
}