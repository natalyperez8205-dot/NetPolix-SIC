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
    private final Usuario usuario;
    private List<Video> videos;

    private JComboBox<String> cbVideos;
    private JLabel lblPromedio;
    private JLabel lblExcelente;
    private JLabel lblBuena;
    private JLabel lblRegular;
    private JLabel lblMala;
    private JLabel lblVotoActual;
    private JProgressBar barExcelente;
    private JProgressBar barBuena;
    private JProgressBar barRegular;
    private JProgressBar barMala;

    public CalificarVideo(Usuario usuario) {

        this.usuario = usuario;

        setTitle("NetPOLIx — Calificar");
        setSize(520, 600);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Estilos.FONDO);

        // HEADER
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 520, 65);
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

        JLabel lblSub = new JLabel("⭐  Calificar película");
        lblSub.setBounds(270, 22, 230, 22);
        lblSub.setFont(Estilos.FUENTE_SUBTIT);
        lblSub.setForeground(Estilos.TEXTO_GRIS);
        header.add(lblSub);
        add(header);

        // COMBO
        JLabel lblSel = new JLabel("Selecciona una película:");
        lblSel.setBounds(25, 82, 250, 18);
        lblSel.setFont(Estilos.FUENTE_SUBTIT);
        lblSel.setForeground(Estilos.TEXTO_GRIS);
        add(lblSel);

        cbVideos = new JComboBox<>();
        cbVideos.setBounds(25, 103, 340, 34);
        Estilos.combo(cbVideos);
        add(cbVideos);

        JButton btnVer = new JButton("Ver");
        btnVer.setBounds(375, 103, 110, 34);
        Estilos.botonPrincipal(btnVer);
        add(btnVer);

        // VOTO ACTUAL DEL USUARIO
        lblVotoActual = new JLabel("Tu voto: ninguno");
        lblVotoActual.setBounds(25, 145, 460, 22);
        lblVotoActual.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblVotoActual.setForeground(new Color(100, 200, 100));
        add(lblVotoActual);

        // PROMEDIO
        lblPromedio = new JLabel("Promedio: —");
        lblPromedio.setBounds(25, 172, 460, 28);
        lblPromedio.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblPromedio.setForeground(Estilos.ACENTO);
        add(lblPromedio);

        // BARRAS
        lblExcelente = new JLabel("Excelente: 0");
        barExcelente = new JProgressBar(0, 100);
        agregarBarra(lblExcelente, barExcelente, 210,
                new Color(46, 204, 113));

        lblBuena = new JLabel("Buena: 0");
        barBuena = new JProgressBar(0, 100);
        agregarBarra(lblBuena, barBuena, 250,
                new Color(52, 152, 219));

        lblRegular = new JLabel("Regular: 0");
        barRegular = new JProgressBar(0, 100);
        agregarBarra(lblRegular, barRegular, 290,
                new Color(230, 126, 34));

        lblMala = new JLabel("Mala: 0");
        barMala = new JProgressBar(0, 100);
        agregarBarra(lblMala, barMala, 330,
                new Color(231, 76, 60));

        // SEPARADOR
        JSeparator sep = new JSeparator();
        sep.setBounds(25, 375, 460, 2);
        sep.setForeground(Estilos.BORDE);
        add(sep);

        JLabel lblVota = new JLabel("Tu calificación:");
        lblVota.setBounds(25, 385, 200, 20);
        lblVota.setFont(Estilos.FUENTE_SUBTIT);
        lblVota.setForeground(Estilos.TEXTO_GRIS);
        add(lblVota);

        // BOTONES VOTAR
        JButton btnExcelente = new JButton("😍 Excelente");
        btnExcelente.setBounds(25, 413, 105, 38);
        Estilos.botonPrincipal(btnExcelente);
        btnExcelente.setBackground(new Color(46, 204, 113));
        add(btnExcelente);

        JButton btnBuena = new JButton("🙂 Buena");
        btnBuena.setBounds(138, 413, 105, 38);
        Estilos.botonPrincipal(btnBuena);
        btnBuena.setBackground(new Color(52, 152, 219));
        add(btnBuena);

        JButton btnRegular = new JButton("😐 Regular");
        btnRegular.setBounds(251, 413, 105, 38);
        Estilos.botonPrincipal(btnRegular);
        btnRegular.setBackground(new Color(230, 126, 34));
        add(btnRegular);

        JButton btnMala = new JButton("😤 Mala");
        btnMala.setBounds(364, 413, 105, 38);
        Estilos.botonPrincipal(btnMala);
        btnMala.setBackground(new Color(231, 76, 60));
        add(btnMala);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setBounds(175, 468, 160, 36);
        Estilos.botonSecundario(btnVolver);
        add(btnVolver);

        // EVENTOS
        cargarVideos();
        btnVer.addActionListener(e -> mostrarCalificacion());
        btnExcelente.addActionListener(e -> votar("excelente"));
        btnBuena.addActionListener(e -> votar("buena"));
        btnRegular.addActionListener(e -> votar("regular"));
        btnMala.addActionListener(e -> votar("mala"));
        btnVolver.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
    }

    private void agregarBarra(JLabel lbl, JProgressBar bar,
                               int y, Color color) {
        lbl.setBounds(25, y, 120, 22);
        lbl.setFont(Estilos.FUENTE_NORMAL);
        lbl.setForeground(Estilos.TEXTO);
        add(lbl);

        bar.setBounds(150, y, 330, 22);
        bar.setBackground(Estilos.PANEL);
        bar.setForeground(color);
        bar.setBorderPainted(false);
        bar.setValue(0);
        add(bar);
    }

    private void cargarVideos() {
        cbVideos.removeAllItems();
        videos = videoDao.listarVideos();
        for (Video v : videos)
            cbVideos.addItem(v.getId() + " — " + v.getTituloOriginal());
    }

    private Video getSeleccionado() {
        int idx = cbVideos.getSelectedIndex();
        if (idx < 0 || videos == null || videos.isEmpty()) return null;
        return videos.get(idx);
    }

    private void mostrarCalificacion() {
        Video v = getSeleccionado();
        if (v == null) return;

        // Mostrar voto actual del usuario
        String votoActual = calDao.obtenerVotoAnterior(
                usuario.getId(), v.getId());
        if (votoActual != null) {
            lblVotoActual.setText("Tu voto actual: " + votoActual
                    + "  (puedes cambiarlo)");
            lblVotoActual.setForeground(new Color(100, 200, 100));
        } else {
            lblVotoActual.setText("Aún no has calificado esta película.");
            lblVotoActual.setForeground(Estilos.TEXTO_GRIS);
        }

        Calificacion c = calDao.obtenerPorVideo(v.getId());
        if (c == null) {
            lblPromedio.setText("Promedio: Sin votos aún");
            barExcelente.setValue(0); lblExcelente.setText("Excelente: 0");
            barBuena.setValue(0);     lblBuena.setText("Buena: 0");
            barRegular.setValue(0);   lblRegular.setText("Regular: 0");
            barMala.setValue(0);      lblMala.setText("Mala: 0");
            return;
        }

        int total = c.getExcelente() + c.getBuena()
                  + c.getRegular() + c.getMala();

        lblPromedio.setText(String.format(
                "Promedio: %.2f / 4.00  (%d votos totales)",
                c.calcularPromedio(), total));

        actualizarBarra(barExcelente, lblExcelente,
                "Excelente", c.getExcelente(), total);
        actualizarBarra(barBuena, lblBuena,
                "Buena", c.getBuena(), total);
        actualizarBarra(barRegular, lblRegular,
                "Regular", c.getRegular(), total);
        actualizarBarra(barMala, lblMala,
                "Mala", c.getMala(), total);
    }

    private void actualizarBarra(JProgressBar bar, JLabel lbl,
                                  String nombre, int votos, int total) {
        int pct = total > 0 ? (votos * 100 / total) : 0;
        bar.setValue(pct);
        lbl.setText(nombre + ": " + votos);
    }

    private void votar(String tipo) {
        Video v = getSeleccionado();
        if (v == null) {
            JOptionPane.showMessageDialog(null,
                    "Selecciona una película primero.");
            return;
        }

        String votoAnterior = calDao.obtenerVotoAnterior(
                usuario.getId(), v.getId());

        if (votoAnterior != null) {
            if (votoAnterior.equals(tipo)) {
                JOptionPane.showMessageDialog(null,
                        "Ya calificaste esta película como \""
                        + tipo + "\".");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Tu calificación actual es: \"" + votoAnterior + "\"\n"
                    + "¿Deseas cambiarla a: \"" + tipo + "\"?",
                    "Cambiar calificación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        calDao.votar(usuario.getId(), v.getId(), tipo);
        JOptionPane.showMessageDialog(null,
                votoAnterior != null
                ? "✅ Calificación cambiada a: " + tipo
                : "✅ Voto registrado: " + tipo);
        mostrarCalificacion();
    }
}