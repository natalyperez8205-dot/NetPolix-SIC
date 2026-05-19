package modelo;

public class Idioma {
    private int id;
    private String lenguaje;

    public Idioma() {}

    public Idioma(int id, String lenguaje) {
        this.id = id;
        this.lenguaje = lenguaje;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { 
        this.lenguaje = lenguaje; }
}