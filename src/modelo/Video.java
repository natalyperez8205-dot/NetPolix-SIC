package modelo;

public class Video {
    private int id;
    private String tituloOriginal;
    private String categoria;
    private String actores;
    private int duracion;
    private int edadRestriccion;
    private String idioma;
    private double precio;

    public Video() {}

    public Video(int id, String tituloOriginal, String categoria,
                 String actores, int duracion, int edadRestriccion,
                 String idioma, double precio) {
        this.id = id;
        this.tituloOriginal = tituloOriginal;
        this.categoria = categoria;
        this.actores = actores;
        this.duracion = duracion;
        this.edadRestriccion = edadRestriccion;
        this.idioma = idioma;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTituloOriginal() { return tituloOriginal; }
    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getActores() { return actores; }
    public void setActores(String actores) { this.actores = actores; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public int getEdadRestriccion() { return edadRestriccion; }
    public void setEdadRestriccion(int edadRestriccion) {
        this.edadRestriccion = edadRestriccion;
    }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        String titulo = (tituloOriginal == null || tituloOriginal.isEmpty())
                ? "(Sin título)" : tituloOriginal;
        String cat = (categoria == null || categoria.isEmpty())
                ? "(Sin categoría)" : categoria;
        String dur = duracion == 0
                ? "(Sin duración)" : duracion + " min";
        String prec = precio == 0
                ? "(Sin precio)" : "$" + String.format("%.2f", precio);
        return titulo + "  |  " + cat + "  |  " + dur + "  |  " + prec;
    }
}