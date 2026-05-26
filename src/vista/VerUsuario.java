package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerUsuario extends JFrame {

    JTable tablaUsuarios;
    DefaultTableModel modelo;

    public VerUsuario() {

        setTitle("NetPOLIx — Usuarios");
        setSize(720, 580);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 720, 65);
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

        JLabel lblSub = new JLabel("👥  Gestión de usuarios");
        lblSub.setBounds(310, 22, 380, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // TABLA
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Rol");

        tablaUsuarios = new JTable(modelo);
        Estilos.tabla(tablaUsuarios);

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBounds(20, 80, 670, 340);
        Estilos.scroll(scroll);
        add(scroll);

        // BOTONES
        JButton btnEditar   = new JButton("✏ Editar usuario");
        btnEditar.setBounds(20, 440, 190, 40);
        Estilos.botonPrincipal(btnEditar);
        add(btnEditar);

        JButton btnEliminar = new JButton("🗑 Eliminar usuario");
        btnEliminar.setBounds(225, 440, 190, 40);
        Estilos.botonPrincipal(btnEliminar);
        btnEliminar.setBackground(new Color(150, 20, 20));
        add(btnEliminar);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(560, 440, 130, 40);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EDITAR — abre ventana con nombre, correo y rol
        btnEditar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null,
                        "Selecciona un usuario primero.");
                return;
            }

            int id = Integer.parseInt(
                    modelo.getValueAt(fila, 0).toString());
            String nombreActual = modelo.getValueAt(fila, 1).toString();
            String correoActual = modelo.getValueAt(fila, 2).toString();
            String rolActual    = modelo.getValueAt(fila, 3).toString();

            // Panel de edición
            JPanel panel = new JPanel(null);
            panel.setPreferredSize(new Dimension(380, 200));
            panel.setBackground(Estilos.FONDO);

            JLabel lN = new JLabel("Nombre:");
            lN.setBounds(10, 10, 100, 20);
            lN.setForeground(Estilos.TEXTO);
            panel.add(lN);

            JTextField txtNombre = new JTextField(nombreActual);
            txtNombre.setBounds(10, 32, 350, 32);
            Estilos.campo(txtNombre);
            panel.add(txtNombre);

            JLabel lC = new JLabel("Correo:");
            lC.setBounds(10, 75, 100, 20);
            lC.setForeground(Estilos.TEXTO);
            panel.add(lC);

            JTextField txtCorreo = new JTextField(correoActual);
            txtCorreo.setBounds(10, 97, 350, 32);
            Estilos.campo(txtCorreo);
            panel.add(txtCorreo);

            JLabel lR = new JLabel("Rol:");
            lR.setBounds(10, 140, 100, 20);
            lR.setForeground(Estilos.TEXTO);
            panel.add(lR);

            String[] roles = {"CLIENTE", "ADMINISTRADOR", "GERENTE"};
            JComboBox<String> cbRol = new JComboBox<>(roles);
            cbRol.setSelectedItem(rolActual);
            cbRol.setBounds(10, 162, 200, 32);
            Estilos.combo(cbRol);
            panel.add(cbRol);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Editar usuario", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtNombre.getText().trim();
                String nuevoCorreo = txtCorreo.getText().trim();
                String nuevoRol    = cbRol.getSelectedItem().toString();

                if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Nombre y correo no pueden estar vacíos.");
                    return;
                }

                new Usuario_Dao().editarUsuarioCompleto(
                        id, nuevoNombre, nuevoCorreo, nuevoRol);
                JOptionPane.showMessageDialog(null,
                        "✅ Usuario actualizado correctamente.");
                mostrarUsuarios();
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null,
                        "Selecciona un usuario primero.");
                return;
            }
            String nombre = modelo.getValueAt(fila, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar a \"" + nombre + "\"?\nEsta acción no se puede deshacer.",
                    "Confirmar", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(
                        modelo.getValueAt(fila, 0).toString());
                new Usuario_Dao().eliminarUsuario(id);
                JOptionPane.showMessageDialog(null, "Usuario eliminado.");
                mostrarUsuarios();
            }
        });

        btnVolver.addActionListener(e -> dispose());

        mostrarUsuarios();
        setLocationRelativeTo(null);
    }

    private void mostrarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = new Usuario_Dao().listarUsuarios();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                u.getId(),
                u.getNombre().isEmpty() ? "(vacío)" : u.getNombre(),
                u.getCorreo().isEmpty() ? "(vacío)" : u.getCorreo(),
                u.getRol()
            });
        }
    }
}