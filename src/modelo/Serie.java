package modelo;

public class Serie {
    private int id;
    private String titulo;
    private int temporada;
    private int idVideo;

    public Serie() {}

    public Serie(int id, String titulo, int temporada, int idVideo) {
        this.id = id;
        this.titulo = titulo;
        this.temporada = temporada;
        this.idVideo = idVideo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getTemporada() { return temporada; }
    public void setTemporada(int temporada) { 
        this.temporada = temporada; }
    public int getIdVideo() { return idVideo; }
    public void setIdVideo(int idVideo) { this.idVideo = idVideo; }
}