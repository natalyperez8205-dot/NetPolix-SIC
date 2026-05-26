package vista;

import java.awt.*;

public class Estilos {

    // COLORES
    public static final Color FONDO         = new Color(15, 15, 15);
    public static final Color PANEL         = new Color(25, 25, 25);
    public static final Color ACENTO        = new Color(229, 9, 20);   // rojo Netflix
    public static final Color ACENTO_HOVER  = new Color(180, 0, 10);
    public static final Color TEXTO         = new Color(255, 255, 255);
    public static final Color TEXTO_GRIS    = new Color(180, 180, 180);
    public static final Color CAMPO         = new Color(40, 40, 40);
    public static final Color BORDE         = new Color(60, 60, 60);
    public static final Color BOTON_SEC     = new Color(50, 50, 50);

    // FUENTES
    public static final Font FUENTE_TITULO  = new Font("SansSerif", Font.BOLD, 26);
    public static final Font FUENTE_SUBTIT  = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL  = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON   = new Font("SansSerif", Font.BOLD, 13);

    // Aplica estilo a un botón principal (rojo)
    public static void botonPrincipal(javax.swing.JButton btn) {
        btn.setBackground(ACENTO);
        btn.setForeground(TEXTO);
        btn.setFont(FUENTE_BOTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Aplica estilo a un botón secundario (gris oscuro)
    public static void botonSecundario(javax.swing.JButton btn) {
        btn.setBackground(BOTON_SEC);
        btn.setForeground(TEXTO);
        btn.setFont(FUENTE_BOTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Aplica estilo a un campo de texto
    public static void campo(javax.swing.JTextField campo) {
        campo.setBackground(CAMPO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDE),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    // Aplica estilo a un campo de contraseña
    public static void campo(javax.swing.JPasswordField campo) {
        campo.setBackground(CAMPO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(TEXTO);
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDE),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    // Aplica estilo a una tabla
    public static void tabla(javax.swing.JTable tabla) {
        tabla.setBackground(PANEL);
        tabla.setForeground(TEXTO);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(28);
        tabla.setGridColor(BORDE);
        tabla.getTableHeader().setBackground(ACENTO);
        tabla.getTableHeader().setForeground(TEXTO);
        tabla.getTableHeader().setFont(FUENTE_SUBTIT);
        tabla.setSelectionBackground(ACENTO);
        tabla.setSelectionForeground(TEXTO);
    }

    // Aplica estilo a un JTextArea
    public static void area(javax.swing.JTextArea area) {
        area.setBackground(PANEL);
        area.setForeground(TEXTO);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setCaretColor(TEXTO);
        area.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
    }

    // Aplica estilo a un JScrollPane
    public static void scroll(javax.swing.JScrollPane scroll) {
        scroll.getViewport().setBackground(PANEL);
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(BORDE));
    }

    // Aplica estilo a un JComboBox
    @SuppressWarnings("unchecked")
    public static void combo(javax.swing.JComboBox combo) {
        combo.setBackground(CAMPO);
        combo.setForeground(TEXTO);
        combo.setFont(FUENTE_NORMAL);
    }
}