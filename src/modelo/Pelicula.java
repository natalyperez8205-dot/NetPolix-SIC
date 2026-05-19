package modelo;

public class Pelicula {
    private int id;
    private int idVideo;

    public Pelicula() {}

    public Pelicula(int id, int idVideo) {
        this.id = id;
        this.idVideo = idVideo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVideo() { return idVideo; }
    public void setIdVideo(int idVideo) { this.idVideo = idVideo; }
}