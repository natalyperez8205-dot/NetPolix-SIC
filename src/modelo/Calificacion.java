package modelo;

public class Calificacion {
    private int id;
    private int idVideo;
    private int excelente;
    private int buena;
    private int regular;
    private int mala;

    public Calificacion() {}

    public Calificacion(int id, int idVideo, int excelente, 
                        int buena, int regular, int mala) {
        this.id = id;
        this.idVideo = idVideo;
        this.excelente = excelente;
        this.buena = buena;
        this.regular = regular;
        this.mala = mala;
    }

    public double calcularPromedio() {
        int total = excelente + buena + regular + mala;
        if (total == 0) return 0;
        return (double)(excelente*4 + buena*3 + regular*2 + mala*1) / total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVideo() { return idVideo; }
    public void setIdVideo(int idVideo) { this.idVideo = idVideo; }
    public int getExcelente() { return excelente; }
    public void setExcelente(int excelente) { this.excelente = excelente; }
    public int getBuena() { return buena; }
    public void setBuena(int buena) { this.buena = buena; }
    public int getRegular() { return regular; }
    public void setRegular(int regular) { this.regular = regular; }
    public int getMala() { return mala; }
    public void setMala(int mala) { this.mala = mala; }
}