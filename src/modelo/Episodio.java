package modelo;

public class Episodio {
    private int id;
    private String titulo;
    private int duracion;
    private int numero;
    private int idSerie;

    public Episodio() {}

    public Episodio(int id, String titulo, int duracion, 
                    int numero, int idSerie) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.numero = numero;
        this.idSerie = idSerie;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getIdSerie() { return idSerie; }
    public void setIdSerie(int idSerie) { this.idSerie = idSerie; }
}