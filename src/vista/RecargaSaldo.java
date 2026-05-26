package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RecargaSaldo extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;
    JTextField txtMonto;

    public RecargaSaldo() {

        setTitle("NetPOLIx — Recargar Saldo");
        setSize(660, 520);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 660, 65);
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

        JLabel lblSub = new JLabel("💰  Recargar saldo a cliente");
        lblSub.setBounds(280, 22, 350, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // INSTRUCCIÓN
        JLabel lblInst = new JLabel(
                "Selecciona un cliente y escribe el monto a recargar:");
        lblInst.setBounds(20, 78, 500, 20);
        lblInst.setFont(Estilos.FUENTE_SUBTIT);
        lblInst.setForeground(Estilos.TEXTO_GRIS);
        add(lblInst);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Saldo actual");

        tabla = new JTable(modelo);
        Estilos.tabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 105, 610, 270);
        Estilos.scroll(scroll);
        add(scroll);

        // MONTO
        JLabel lblMonto = new JLabel("Monto a recargar ($):");
        lblMonto.setBounds(20, 390, 200, 20);
        lblMonto.setFont(Estilos.FUENTE_SUBTIT);
        lblMonto.setForeground(Estilos.TEXTO_GRIS);
        add(lblMonto);

        txtMonto = new JTextField();
        txtMonto.setBounds(220, 388, 200, 34);
        Estilos.campo(txtMonto);
        add(txtMonto);

        // BOTONES
        JButton btnRecargar = new JButton("💰 Recargar");
        btnRecargar.setBounds(20, 438, 200, 42);
        Estilos.botonPrincipal(btnRecargar);
        add(btnRecargar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(450, 438, 180, 42);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        btnRecargar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null,
                        "Selecciona un cliente primero.");
                return;
            }
            if (txtMonto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Ingresa un monto.");
                return;
            }
            try {
                double monto = Double.parseDouble(
                        txtMonto.getText().trim());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(null,
                            "El monto debe ser mayor a 0.");
                    return;
                }
                int id = Integer.parseInt(
                        modelo.getValueAt(fila, 0).toString());
                String nombre = modelo.getValueAt(fila, 1).toString();

                new Usuario_Dao().recargarSaldo(id, monto);

                JOptionPane.showMessageDialog(null,
                        "✅ Se recargaron $"
                        + String.format("%.2f", monto)
                        + " a " + nombre + " correctamente.",
                        "Recarga exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                txtMonto.setText("");
                cargar();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "El monto debe ser un número válido.");
            }
        });

        btnVolver.addActionListener(e -> dispose());

        cargar();
        setLocationRelativeTo(null);
    }

    private void cargar() {
        modelo.setRowCount(0);
        Usuario_Dao dao = new Usuario_Dao();
        List<Usuario> lista = dao.listarUsuarios();
        for (Usuario u : lista) {
            if (u.getRol().equalsIgnoreCase("CLIENTE")) {
                double saldo = dao.obtenerSaldo(u.getId());
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    "$" + String.format("%.2f", saldo)
                });
            }
        }
    }
}