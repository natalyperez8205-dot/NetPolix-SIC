package vista;

import dao.Calificacion_Dao;
import dao.Video_Dao;
import modelo.Calificacion;
import modelo.Usuario;
import modelo.Video;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalificarVideo extends JFrame {

    private final Calificacion_Dao calDao = new Calificacion_Dao();
    private final Video_Dao videoDao = new Video_Dao();

    JComboBox<String> cbVideos;
    JList<Integer> listaIds; // para guardar ids paralelos
    java.util.List<Video> videos;

    JLabel lblPromedio;
    JLabel lblExcelente;
    JLabel lblBuena;
    JLabel lblRegular;
    JLabel lblMala;

    JButton btnExcelente;
    JButton btnBuena;
    JButton btnRegular;
    JButton btnMala;
    JButton btnVolver;

    public CalificarVideo(Usuario usuario) {

        setTitle("Calificar Video");
        setSize(500, 420);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("CALIFICAR PELÍCULA");
        lblTitulo.setBounds(150, 15, 250, 25);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        add(lblTitulo);

        JLabel lblSel = new JLabel("Película:");
        lblSel.setBounds(20, 55, 80, 25);
        add(lblSel);

        cbVideos = new JComboBox<>();
        cbVideos.setBounds(110, 55, 330, 25);
        add(cbVideos);

        JButton btnCargar = new JButton("Ver calificación");
        btnCargar.setBounds(110, 90, 160, 30);
        add(btnCargar);

        lblPromedio = new JLabel("Promedio: -");
        lblPromedio.setBounds(20, 135, 400, 25);
        lblPromedio.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        add(lblPromedio);

        lblExcelente = new JLabel("Excelente: 0 votos");
        lblExcelente.setBounds(20, 165, 200, 20);
        add(lblExcelente);

        lblBuena = new JLabel("Buena: 0 votos");
        lblBuena.setBounds(20, 188, 200, 20);
        add(lblBuena);

        lblRegular = new JLabel("Regular: 0 votos");
        lblRegular.setBounds(20, 211, 200, 20);
        add(lblRegular);

        lblMala = new JLabel("Mala: 0 votos");
        lblMala.setBounds(20, 234, 200, 20);
        add(lblMala);

        JLabel lblVotar = new JLabel("Tu calificación:");
        lblVotar.setBounds(20, 270, 150, 25);
        lblVotar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        add(lblVotar);

        btnExcelente = new JButton("Excelente");
        btnExcelente.setBounds(20, 300, 100, 30);
        add(btnExcelente);

        btnBuena = new JButton("Buena");
        btnBuena.setBounds(130, 300, 100, 30);
        add(btnBuena);

        btnRegular = new JButton("Regular");
        btnRegular.setBounds(240, 300, 100, 30);
        add(btnRegular);

        btnMala = new JButton("Mala");
        btnMala.setBounds(350, 300, 100, 30);
        add(btnMala);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 350, 120, 30);
        add(btnVolver);

        cargarVideos();

        btnCargar.addActionListener(e -> mostrarCalificacion());

        btnExcelente.addActionListener(e -> votar("excelente"));
        btnBuena.addActionListener(e -> votar("buena"));
        btnRegular.addActionListener(e -> votar("regular"));
        btnMala.addActionListener(e -> votar("mala"));

        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarVideos() {
        cbVideos.removeAllItems();
        videos = videoDao.listarVideos();
        for (Video v : videos) {
            cbVideos.addItem(v.getId() + " - " + v.getTituloOriginal());
        }
    }

    private Video getVideoSeleccionado() {
        int idx = cbVideos.getSelectedIndex();
        if (idx < 0 || videos.isEmpty()) return null;
        return videos.get(idx);
    }

    private void mostrarCalificacion() {
        Video v = getVideoSeleccionado();
        if (v == null) return;

        Calificacion c = calDao.obtenerPorVideo(v.getId());
        if (c == null) {
            lblPromedio.setText("Promedio: Sin calificaciones");
            lblExcelente.setText("Excelente: 0 votos");
            lblBuena.setText("Buena: 0 votos");
            lblRegular.setText("Regular: 0 votos");
            lblMala.setText("Mala: 0 votos");
            return;
        }

        lblPromedio.setText(String.format(
                "Promedio: %.2f / 4.00", c.calcularPromedio()));
        lblExcelente.setText("Excelente: " + c.getExcelente() + " votos");
        lblBuena.setText("Buena: " + c.getBuena() + " votos");
        lblRegular.setText("Regular: " + c.getRegular() + " votos");
        lblMala.setText("Mala: " + c.getMala() + " votos");
    }

    private void votar(String tipo) {
        Video v = getVideoSeleccionado();
        if (v == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una película.");
            return;
        }
        calDao.votar(v.getId(), tipo);
        JOptionPane.showMessageDialog(null, "Voto registrado: " + tipo);
        mostrarCalificacion();
    }
}