package RETO2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    private List<Autor> autores;
    private List<Libro> libros;
    private Scanner sc;
    public Biblioteca() {
        autores = new ArrayList<>();
        libros = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    // ===================== MENÚ =====================
    public void menu() {
        int opcion;
        do {
            System.out.println("\n===== BIBLIOTECA =====");
            System.out.println("1. Añadir autor");
            System.out.println("2. Añadir libro");
            System.out.println("3. Listar autores");
            System.out.println("4. Listar libros");
            System.out.println("5. Eliminar libro");
            System.out.println("6. Estadísticas libros");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> añadirAutor();
                case 2 -> añadirLibro();
                case 3 -> listarAutores();
                case 4 -> listarLibros();
                case 5 -> eliminarLibro();
                case 6 -> estadisticas();
            }
        } while (opcion != 0);
    }

    // ===================== AUTORES =====================
    private void añadirAutor() {
        System.out.print("ID autor: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nacionalidad: ");
        String nacionalidad = sc.nextLine();
        System.out.print("Año nacimiento: ");
        int año = sc.nextInt();
        Autor autor = new Autor(id, nombre, nacionalidad, LocalDate.of(año, 1, 1));
        autores.add(autor);
        System.out.println("Autor añadido.");
    }
    private Autor buscarAutorPorId(int id) {
        for (Autor a : autores) {
            if (a.getIdAutor() == id)
                return a;
        }
        return null;
    }
    private void listarAutores() {
        for (Autor a : autores)
            System.out.println(a);
    }

    // ===================== LIBROS =====================
    private void añadirLibro() {
        System.out.print("ID libro: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        System.out.print("Año publicación: ");
        int año = sc.nextInt();
        sc.nextLine();
        System.out.print("Género: ");
        String genero = sc.nextLine();
        System.out.print("ID del autor: ");
        int idAutor = sc.nextInt();
        Autor autor = buscarAutorPorId(idAutor);
        if (autor == null) {
            System.out.println("Autor no encontrado.");
            return;
        }
        Libro libro = new Libro(id, titulo, isbn, año, genero, autor);
        libros.add(libro);
        autor.addLibro(libro);
        System.out.println("Libro añadido correctamente.");
    }
    private void listarLibros() {
        for (Libro l : libros)
            System.out.println(l);
    }
    private void eliminarLibro() {
        System.out.print("ID libro a eliminar: ");
        int id = sc.nextInt();
        Libro encontrado = null;
        for (Libro l : libros) {
            if (l.getIdLibro() == id) {
                encontrado = l;
                break;
            }
        }
        if (encontrado != null) {
            encontrado.getAutor().removeLibro(encontrado);
            libros.remove(encontrado);
            System.out.println("Libro eliminado.");
        } else {
            System.out.println("No existe ese libro.");
        }
    }

    // ===================== ESTADÍSTICAS =====================
    private void estadisticas() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros.");
            return;
        }
        int max = libros.get(0).getAnioPublicacion();
        int min = libros.get(0).getAnioPublicacion();
        int suma = 0;
        for (Libro l : libros) {
            int año = l.getAnioPublicacion();
            suma += año;
            if (año > max) max = año;
            if (año < min) min = año;
        }
        double media = (double) suma / libros.size();
        System.out.println("Año más reciente: " + max);
        System.out.println("Año más antiguo: " + min);
        System.out.println("Media años: " + media);
        System.out.println("Total libros: " + libros.size());
    }
}
