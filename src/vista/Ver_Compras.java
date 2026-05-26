package vista;

import dao.Carrito_Dao;
import dao.Usuario_Dao;
import dao.Video_Dao;

import modelo.Usuario;
import modelo.Video;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Ver_Compras extends JFrame {

    JTextArea areaCompras;

    public Ver_Compras(){

        setTitle("Compras");

        setSize(500,500);

        setLayout(new BorderLayout());

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        areaCompras =
                new JTextArea();

        areaCompras.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(areaCompras);

        add(scroll, BorderLayout.CENTER);

        JButton btnVolver =
                new JButton("Volver");

        add(btnVolver,
                BorderLayout.SOUTH);

        btnVolver.addActionListener(
                e -> dispose());

        mostrarCompras();

    }

    public void mostrarCompras(){

        Carrito_Dao carritoDao =
                new Carrito_Dao();

        Usuario_Dao usuarioDao =
                new Usuario_Dao();

        Video_Dao videoDao =
                new Video_Dao();

        Map<Integer,
                Map<Integer,Integer>>
                datos =
                carritoDao.obtenerTodosLosCarritos();

        String texto = "";

        for(Integer idUsuario
                : datos.keySet()){

            Usuario usuario =
                    usuarioDao.buscarUsuarioPorId(
                            idUsuario);

            if(usuario != null){

                texto +=
                        "USUARIO: "
                        + usuario.getNombre()
                        + "\n"

                        + "CORREO: "
                        + usuario.getCorreo()
                        + "\n\n";
            }

            Map<Integer,Integer>
                    carrito =
                    datos.get(idUsuario);

            for(Integer idVideo
                    : carrito.keySet()){

                Video video =
                        videoDao.buscarPorId(idVideo);

                if(video != null){

                    texto +=
                            "PELÍCULA: "
                            + video.getTituloOriginal()
                            + "\n"

                            + "CATEGORÍA: "
                            + video.getCategoria()
                            + "\n"

                            + "PRECIO: "
                            + video.getPrecio()
                            + "\n"

                            + "CANTIDAD: "
                            + carrito.get(idVideo)
                            + "\n\n";
                }
            }

            texto +=
                    "----------------------\n";
        }

        areaCompras.setText(texto);
    }
}
