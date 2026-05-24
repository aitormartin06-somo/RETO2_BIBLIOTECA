package model;

public class Prestamos {

    // === ATRIBUTOS ===
    private int id_prestamo;
    private String fecha_alquiler;
    private String fecha_devolucion;
    private int id_ejemplar;
    private int id_usuario;

    // === CONSTRUCTORES ===
    public Prestamos(int p_id_prestamo, String p_fecha_alquiler, String p_fecha_devolucion, int p_id_ejemplar,
            int p_id_usuario) {
        this.id_prestamo = p_id_prestamo;
        this.fecha_alquiler = p_fecha_alquiler;
        this.fecha_devolucion = p_fecha_devolucion;
        this.id_ejemplar = p_id_ejemplar;
        this.id_usuario = p_id_usuario;
    }

    // === GETTERS & SETTERS ===
    public int getId_prestamo() {
        return this.id_prestamo;
    }

    public void setId_prestamo(int p_id_prestamo) {
        this.id_prestamo = p_id_prestamo;
    }

    public String getFecha_alquiler() {
        return this.fecha_alquiler;
    }

    public void setFecha_alquiler(String p_fecha_alquiler) {
        this.fecha_alquiler = p_fecha_alquiler;
    }

    public String getFecha_devolucion() {
        return this.fecha_devolucion;
    }

    public void setFecha_devolucion(String p_fecha_devolucion) {
        this.fecha_devolucion = p_fecha_devolucion;
    }

    public int getId_ejemplar() {
        return this.id_ejemplar;
    }

    public void setId_ejemplar(int p_id_ejemplar) {
        this.id_ejemplar = p_id_ejemplar;
    }

    public int getid_user() {
        return this.id_usuario;
    }

    public void setId_usuario(int p_id_usuario) {
        this.id_usuario = p_id_usuario;
    }

    @Override
    public String toString() {
        return "ID: " + id_prestamo + " | Fecha alquiler: " + fecha_alquiler + " | Fecha devolución: "
                + fecha_devolucion + " | ID Ejemplar: " + id_ejemplar + " | ID Usuario: " + id_usuario;
    }
}
