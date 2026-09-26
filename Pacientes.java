public class Pacientes {
    private int id;
    private String nombre;
    private int edad;
    private String servicio;
    private String estado;
    public Pacientes() {
    }
    public Pacientes(int id, String nombre, int edad, String servicio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.servicio = servicio;
        this.estado = estado;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getServicio() {
        return servicio;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Edad: " + edad
                + " | Servicio: " + servicio + " | Estado: " + estado;
                
    }

}
