package model;

public class Penalizaciones {
    
    // === ATRIBUTOS ===
    private int id_penalizacion;
    private String descripcion;
    private int num_dias_penalizacion;

    // === CONSTRUCTOR ===
    public Penalizaciones(int p_id_penalizacion, String p_descripcion, int p_num_dias_penalizacion) {
        this.id_penalizacion = p_id_penalizacion;
        this.descripcion = p_descripcion;
        this.num_dias_penalizacion = p_num_dias_penalizacion;
    }

    // === GETTERS Y SETTERS ===
    public int getId_penalizacion() {
        return this.id_penalizacion;
    }

    public void setId_penalizacion(int p_id_penalizacion) {
        this.id_penalizacion = p_id_penalizacion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String p_descripcion) {
        this.descripcion = p_descripcion;
    }

    public int getNum_dias_penalizacion() {
        return this.num_dias_penalizacion;
    }

    public void setNum_dias_penalizacion(int p_num_dias_penalizacion) {
        this.num_dias_penalizacion = p_num_dias_penalizacion;
    }

    @Override
    public String toString() {
        return "ID: " + id_penalizacion + " | Descripción: " + descripcion + " | Días de penalización: " + num_dias_penalizacion;
    }

}
