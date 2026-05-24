package model;

public class Autor {

    // ==== ATRIBUTOS ====
    private int id_autor;
    private String nombre;
    private String apellidos;
    private String nacionalidad;

    // ==== CONSTRUCTORES ====
    public Autor(int p_id_autor, String p_nombre, String p_apellido, String p_nacionalidad) {
        this.id_autor = p_id_autor;
        this.nombre = p_nombre;
        this.apellidos = p_apellido;
        this.nacionalidad = p_nacionalidad;
    }

    // ==== GETTERS & SETTERS ====
    public int getId_autor() {
        return this.id_autor;
    }

    public void setId_autor(int p_id_autor) {
        this.id_autor = p_id_autor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String p_apellido) {
        this.apellidos = p_apellido;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(String p_nacionalidad) {
        this.nacionalidad = p_nacionalidad;
    }

    @Override
    public String toString() {
        return "ID: " + id_autor + " | Nombre: " + nombre + " | Apellidos: " + apellidos + " | Nacionalidad: "
                + nacionalidad;
    }
}
