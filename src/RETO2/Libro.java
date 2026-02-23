package RETO2;

import java.util.ArrayList;
import java.util.List;

public class Libro {

    // Atributos
    private int idLibro;
    private String titulo;
    private String isbn;
    private int anioPublicacion;
    private String genero;
    private Autor autor;
    private List<Prestamo> prestamos;  // NUEVO ATRIBUTO

    // Constructor vacío
    public Libro() {
        this.prestamos = new ArrayList<>();  // Inicializar lista
    }

    // Constructor completo
    public Libro(int idLibro, String titulo, String isbn, int anioPublicacion, String genero, Autor autor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
        this.autor = autor;
        this.prestamos = new ArrayList<>();  // Inicializar lista
    }

    // Getters y Setters
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    // NUEVOS MÉTODOS PARA PRÉSTAMOS
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void addPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", genero='" + genero + '\'' +
                ", autor=" + (autor != null ? autor.getNombre() : "null") +
                ", prestamos=" + prestamos.size() +
                '}';
    }
}