package vista;

import dao.Usuario_Dao;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RegistrarActor extends JFrame {

    JTextField txtNombre;
    JTextField txtCedula;

    public RegistrarActor() {

        setTitle("NetPOLIx — Registrar Actor");
        setSize(440, 380);
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

        JLabel lblSub = new JLabel("🎭  Nuevo actor");
        lblSub.setBounds(255, 25, 170, 20);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // NOMBRE
        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setBounds(30, 90, 200, 18);
        lblNombre.setFont(Estilos.FUENTE_SUBTIT);
        lblNombre.setForeground(Estilos.TEXTO_GRIS);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(30, 112, 370, 38);
        Estilos.campo(txtNombre);
        add(txtNombre);

        // CÉDULA
        JLabel lblCedula = new JLabel("Cédula / Identificación:");
        lblCedula.setBounds(30, 165, 220, 18);
        lblCedula.setFont(Estilos.FUENTE_SUBTIT);
        lblCedula.setForeground(Estilos.TEXTO_GRIS);
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(30, 187, 370, 38);
        Estilos.campo(txtCedula);
        add(txtCedula);

        // BOTONES
        JButton btnGuardar = new JButton("✅  REGISTRAR ACTOR");
        btnGuardar.setBounds(30, 255, 370, 44);
        Estilos.botonPrincipal(btnGuardar);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(btnGuardar);

        JButton btnVolver = new JButton("← Cancelar");
        btnVolver.setBounds(30, 310, 370, 38);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()
                    || txtCedula.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Completa todos los campos.",
                        "Campos incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Usuario actor = new Usuario();
            actor.setNombre(txtNombre.getText().trim());
            actor.setCedula(txtCedula.getText().trim());
            actor.setCorreo(txtCedula.getText().trim() + "@actor.netpolix");
            actor.setContrasena("actor");
            actor.setRol("ACTOR");
            actor.setFechaIngreso(LocalDate.now().toString());
            actor.setPuntos(0);
            actor.setSaldo(0);
            actor.setIdReferido(0);

            new Usuario_Dao().guardarUsuario(actor);

            JOptionPane.showMessageDialog(null,
                    "✅ Actor \"" + actor.getNombre() + "\" registrado.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnVolver.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }
}