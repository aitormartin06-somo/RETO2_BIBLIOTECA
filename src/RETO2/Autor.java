package RETO2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Autor {

    // Atributos
    private int idAutor;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private List<Libro> libros;

    // Constructor vacío
    public Autor() {
        this.libros = new ArrayList<>();
    }

    // Constructor completo
    public Autor(int idAutor, String nombre, String nacionalidad, LocalDate fechaNacimiento) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.libros = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    // Métodos para gestionar libros
    public void addLibro(Libro libro) {
        this.libros.add(libro);
    }

    public void removeLibro(Libro libro) {
        this.libros.remove(libro);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "idAutor=" + idAutor +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
