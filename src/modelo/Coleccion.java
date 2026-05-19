package modelo;

public class Coleccion {
    private int id;
    private String isan;
    private String titulo;
    private int volumen;

    public Coleccion() {}

    public Coleccion(int id, String isan, String titulo, int volumen) {
        this.id = id;
        this.isan = isan;
        this.titulo = titulo;
        this.volumen = volumen;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIsan() { return isan; }
    public void setIsan(String isan) { this.isan = isan; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getVolumen() { return volumen; }
    public void setVolumen(int volumen) { this.volumen = volumen; }
}
