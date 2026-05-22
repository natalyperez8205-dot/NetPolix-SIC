package modelo;

public class Video {
    private int id;
    private String isan;
    private String tituloOriginal;
    private int anio;
    private int duracion;
    private int idClasificacion;

    public Video() {}

    public Video(int id, String isan, String tituloOriginal, 
                 int anio, int duracion, int idClasificacion) {
        this.id = id;
        this.isan = isan;
        this.tituloOriginal = tituloOriginal;
        this.anio = anio;
        this.duracion = duracion;
        this.idClasificacion = idClasificacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIsan() { return isan; }
    public void setIsan(String isan) { this.isan = isan; }
    public String getTituloOriginal() { return tituloOriginal; }
    public void setTituloOriginal(String tituloOriginal) { 
        this.tituloOriginal = tituloOriginal; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public int getIdClasificacion() { return idClasificacion; }
    public void setIdClasificacion(int idClasificacion) { 
        this.idClasificacion = idClasificacion; }
}


//Canibrouston

//aaaa