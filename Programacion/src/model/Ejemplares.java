package model;

public class Ejemplares {

    // === ATRIBUTOS ===
    private int id_ejemplar;
    private String estado_fisico;
    private int id_libro;

    // === CONSTRUCTORES ===
    public Ejemplares(int p_id_ejemplar, String p_estado_fisico, int p_id_libro) {
        this.id_ejemplar = p_id_ejemplar;
        this.estado_fisico = p_estado_fisico;
        this.id_libro = p_id_libro;
    }

    // === GETTERS & SETTERS ===
    public int getId_ejemplar() {
        return this.id_ejemplar;
    }

    public void setId_ejemplar(int p_id_ejemplar) {
        this.id_ejemplar = p_id_ejemplar;
    }

    public String getEstado_fisico() {
        return this.estado_fisico;
    }

    public void setEstado_fisico(String p_estado_fisico) {
        this.estado_fisico = p_estado_fisico;
    }

    public int getId_libro() {
        return this.id_libro;
    }

    public void setId_libro(int p_id_libro) {
        this.id_libro = p_id_libro;
    }

    @Override
    public String toString() {
        return "ID: " + id_ejemplar + " | Estado físico: " + estado_fisico + " | ID Libro: " + id_libro;
    }

}