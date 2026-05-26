package model;

public class Libro {

    // === Atributos ===
    private int id_libro;
    private String titulo;
    private long isbn;
    private String editorial;
    private String genero;
    private int num_copias;

    // === Constructor ===
    public Libro(int p_id_libro, String p_titulo, long p_isbn, String p_editorial, String p_genero, int p_num_copias) {
        this.id_libro = p_id_libro;
        this.titulo = p_titulo;
        this.isbn = p_isbn;
        this.editorial = p_editorial;
        this.genero = p_genero;
        this.num_copias = p_num_copias;
    }

    // === Getters y Setters ===
    public int getId_libro() {
        return this.id_libro;
    }

    public void setId_libro(int p_id_libro) {
        this.id_libro = p_id_libro;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String p_titulo) {
        this.titulo = p_titulo;
    }

    public long getIsbn() {
        return this.isbn;
    }

    public void setIsbn(long p_isbn) {
        this.isbn = p_isbn;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public void setEditorial(String p_editorial) {
        this.editorial = p_editorial;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String p_genero) {
        this.genero = p_genero;
    }

    public int getNum_copias() {
        return this.num_copias;
    }

    public void setNum_copias(int p_num_copias) {
        this.num_copias = p_num_copias;
    }

    @Override
    public String toString() {
        return "ID: " + id_libro + " | Título: " + titulo + " | ISBN: " + isbn + " | Editorial: " + editorial + " | Género: " + genero + " | Copias: " + num_copias;
    }

    public String registro() {
        return "[REGISTRO Libro] " + toString();
    }
}