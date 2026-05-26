package dao;

import modelo.Video;
import java.util.HashMap;
import java.util.Map;

public class Carrito_Dao {

    private static final Map<Integer, Map<Integer, Integer>> carritoPorUsuario = new HashMap<>();

    public void agregarAlCarrito(int idUsuario, Video video) {
        if (video == null) {
            return;
        }
        Map<Integer, Integer> carrito = carritoPorUsuario.computeIfAbsent(idUsuario, k -> new HashMap<>());
        carrito.merge(video.getId(), 1, Integer::sum);
        System.out.println("Agregado al carrito: " + video.getTituloOriginal());
    }

    public Map<Integer, Integer> obtenerCarrito(int idUsuario) {
        return carritoPorUsuario.getOrDefault(idUsuario, new HashMap<>());
    }

    public void eliminarDelCarrito(int idUsuario, int idVideo) {
        Map<Integer, Integer> carrito = carritoPorUsuario.get(idUsuario);
        if (carrito != null) {
            carrito.remove(idVideo);
        }
    }

    public void vaciarCarrito(int idUsuario) {
        carritoPorUsuario.remove(idUsuario);
    }
    public Map<Integer, Map<Integer, Integer>>
    obtenerTodosLosCarritos(){

        return carritoPorUsuario;

    } 
}