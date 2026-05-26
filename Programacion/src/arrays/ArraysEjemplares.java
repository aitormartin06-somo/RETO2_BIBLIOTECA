package arrays;

public class ArraysEjemplares {

    final static int[] id_ejemplar = {
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10
    };

    final static String[] estado_fisico = {
            "Nuevo", "Usado", "Dañado", "Nuevo", "Regular",
            "Usado", "Nuevo", "Dañado", "Regular", "Nuevo"
    };

    final static int[] id_libro = {
            101, 102, 103, 104, 105,
            106, 107, 108, 109, 110
    };

    public static void main(String[] args) {
        registro();
    }

    public static void registro() {
        System.out.println("[REGISTRO ArraysEjemplares] Lista de ejemplares:");
        for (int i = 0; i < id_ejemplar.length; i++) {
            System.out.println(
                    "Ejemplar " + (i + 1) +
                    " | ID: " + id_ejemplar[i] +
                    " | Estado físico: " + estado_fisico[i] +
                    " | ID Libro: " + id_libro[i]
            );
        }
    }
}

