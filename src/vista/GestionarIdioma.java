package vista;

import dao.Idioma_Dao;
import modelo.Idioma;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GestionarIdioma extends JFrame {

    JTextField txtIdioma;

    JButton btnGuardar;
    JButton btnEliminar;
    JButton btnVolver;

    JTextArea areaIdiomas;

    private final Idioma_Dao dao =
            new Idioma_Dao();

    public GestionarIdioma(){

        setTitle("Gestionar Idiomas");

        setSize(500,500);

        setLayout(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo =
                new JLabel(
                        "GESTIÓN IDIOMAS");

        lblTitulo.setBounds(
                150,20,200,30);

        add(lblTitulo);

        JLabel lblIdioma =
                new JLabel(
                        "Idioma:");

        lblIdioma.setBounds(
                40,70,100,25);

        add(lblIdioma);

        txtIdioma =
                new JTextField();

        txtIdioma.setBounds(
                120,70,220,25);

        add(txtIdioma);

        btnGuardar =
                new JButton(
                        "Guardar");

        btnGuardar.setBounds(
                350,70,100,25);

        add(btnGuardar);

        areaIdiomas =
                new JTextArea();

        areaIdiomas.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(areaIdiomas);

        scroll.setBounds(
                40,120,400,220);

        add(scroll);

        btnEliminar =
                new JButton(
                        "Eliminar");

        btnEliminar.setBounds(
                90,380,120,35);

        add(btnEliminar);

        btnVolver =
                new JButton(
                        "Volver");

        btnVolver.setBounds(
                250,380,120,35);

        add(btnVolver);

        btnGuardar.addActionListener(e -> {

            if(txtIdioma.getText()
                    .trim()
                    .isEmpty()){

                JOptionPane.showMessageDialog(
                        null,
                        "Ingrese un idioma");

                return;
            }

            Idioma idioma =
                    new Idioma();

            idioma.setNombre(
                    txtIdioma.getText());

            dao.guardarIdioma(idioma);

            JOptionPane.showMessageDialog(
                    null,
                    "Idioma guardado");

            txtIdioma.setText("");

            mostrarIdiomas();

        });

        btnEliminar.addActionListener(e -> {

            String idTexto =
                    JOptionPane.showInputDialog(
                            "Ingrese ID idioma");

            try{

                int id =
                        Integer.parseInt(idTexto);

                dao.eliminarIdioma(id);

                mostrarIdiomas();

            } catch(Exception ex){

                JOptionPane.showMessageDialog(
                        null,
                        "ID inválido");
            }

        });

        btnVolver.addActionListener(
                e -> dispose());

        mostrarIdiomas();
    }

    public void mostrarIdiomas(){

        List<Idioma> lista =
                dao.listarIdiomas();

        String texto = "";

        for(Idioma idioma : lista){

            texto +=
                    idioma.getId()
                    + " - "
                    + idioma.getNombre()
                    + "\n";
        }

        areaIdiomas.setText(texto);
    }
}