package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String cedula;
    private String correo;
    private String contrasena;
    private String rol;
    private String fechaIngreso;
    private int puntos;
    private double saldo;
    private int idReferido;

    public Usuario() {}

    public Usuario(int id, String nombre, String cedula, 
                   String correo, String contrasena, String rol,
                   String fechaIngreso, int puntos, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.puntos = puntos;
        this.saldo = saldo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(String fechaIngreso) { 
        this.fechaIngreso = fechaIngreso; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public int getIdReferido() { return idReferido; }
    public void setIdReferido(int idReferido) { 
        this.idReferido = idReferido; }
}