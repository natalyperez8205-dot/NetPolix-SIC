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

    JButton btnEditar;
    JButton btnEliminar;
    JButton btnVolver;

    public VerUsuario(){

        setTitle("Usuarios registrados");

        setSize(700,500);

        setLayout(new BorderLayout());

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Rol");

        tablaUsuarios =
                new JTable(modelo);

        JScrollPane scroll =
                new JScrollPane(tablaUsuarios);

        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones =
                new JPanel();

        btnEditar =
                new JButton("Editar");

        btnEliminar =
                new JButton("Eliminar");

        btnVolver =
                new JButton("Volver");

        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        add(panelBotones,
                BorderLayout.SOUTH);

        btnVolver.addActionListener(
                e -> dispose());

        btnEliminar.addActionListener(e -> {

            int fila =
                    tablaUsuarios.getSelectedRow();

            if(fila == -1){

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione un usuario");

                return;
            }

            int id =
                    Integer.parseInt(
                            modelo.getValueAt(
                                    fila,
                                    0).toString());

            Usuario_Dao dao =
                    new Usuario_Dao();

            dao.eliminarUsuario(id);

            mostrarUsuarios();

        });

        btnEditar.addActionListener(e -> {

            int fila =
                    tablaUsuarios.getSelectedRow();

            if(fila == -1){

                JOptionPane.showMessageDialog(
                        null,
                        "Seleccione un usuario");

                return;
            }

            String nuevoNombre =
                    JOptionPane.showInputDialog(
                            "Nuevo nombre:");

            if(nuevoNombre != null){

                int id =
                        Integer.parseInt(
                                modelo.getValueAt(
                                        fila,
                                        0).toString());

                Usuario_Dao dao =
                        new Usuario_Dao();

                dao.editarUsuario(
                        id,
                        nuevoNombre);

                mostrarUsuarios();
            }

        });

        mostrarUsuarios();
    }

    public void mostrarUsuarios(){

        modelo.setRowCount(0);

        Usuario_Dao dao =
                new Usuario_Dao();

        List<Usuario> lista =
                dao.listarUsuarios();

        for(Usuario usuario : lista){

            modelo.addRow(new Object[]{

                    usuario.getId(),

                    usuario.getNombre(),

                    usuario.getCorreo(),

                    usuario.getRol()

            });
        }
    }
}