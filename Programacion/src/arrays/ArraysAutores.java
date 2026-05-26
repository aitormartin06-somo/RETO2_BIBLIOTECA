package arrays;

public class ArraysAutores {

    final static String[] nombres = {
            "Gabriel", "Isabela", "Rafa", "Emanuel", "Carlos",
            "Andres", "Antonio", "Unai", "Pancracio", "Andrea", "Miguel"
    };

    final static String[] apellidos = {
            "García", "Martínez", "López", "González", "Rodríguez",
            "Fernández", "Pérez", "Gómez", "Sánchez", "Díaz", "Romero"
    };

    public static void main(String[] args) {
        registro();
    }

    public static void registro() {
        System.out.println("[REGISTRO ArraysAutores] Lista de autores:");
        for (int i = 0; i < nombres.length; i++) {
            System.out.println("Autor " + (i + 1) + ": " + nombres[i] + " " + apellidos[i]);
        }
    }
}
