package arrays;

public class ArrayUsuarios {

    final static String[] usuarios = {

            "juan123", "maria456", "pedro789", "lucia321", "carlos654",
            "ana987", "diego111", "sofia222", "luis333", "laura444",
            "javier555", "isabel666", "fernando777", "marta888", "ricardo999"

    };

    public static void main(String[] args) {
        System.out.println("=== Lista de Usuarios ===");
        for (int i = 0; i < usuarios.length; i++) {
            System.out.println("Usuario " + (i + 1) + ": " + usuarios[i]);
        }
    }
}
