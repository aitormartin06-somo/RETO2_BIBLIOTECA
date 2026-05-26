package arrays;

public class ArrayLibros {

    final static String[] titulos = {
            "El nombre del viento", "Eliza y la bestia", "El principito", "El resplandor", "El psicoanalista",
            "El código Da Vinci", "El laberinto de los espíritus", "Elaleph", "El poder del ahora",
            "El sabueso de los Baskerville",
            "El alquimista", "El exorcista", "El código secreto", "El laberinto de la soledad", "El contrato del diablo"
    };

    public static void main(String[] args) {
        registro();
    }

    public static void registro() {
        System.out.println("[REGISTRO ArrayLibros] Lista de libros:");
        for (int i = 0; i < titulos.length; i++) {
            System.out.println("Libro " + (i + 1) + ": " + titulos[i]);
        }
    }

}
