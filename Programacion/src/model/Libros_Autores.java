package model;

public class Libros_Autores {

    private int id_libro;
    private int id_autor;

    public Libros_Autores(int p_id_libro, int p_id_autor) {
        this.id_libro = p_id_libro;
        this.id_autor = p_id_autor;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int p_id_libro) {
        this.id_libro = p_id_libro;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int p_id_autor) {
        this.id_autor = p_id_autor;
    }
    

}
