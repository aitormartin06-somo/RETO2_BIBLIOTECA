package arrays;

import model.Prestamos;

public class ArrayPrestamos {

    public static void main(String[] args) {
        registro();
    }

    public static void registro() {
        System.out.println("[REGISTRO ArrayPrestamos] Lista de préstamos:");
        Prestamos[] prestamos = new Prestamos[3];

        prestamos[0] = new Prestamos(1, "2025-05-01", "2025-05-15", 101, 1);
        prestamos[1] = new Prestamos(2, "2025-05-03", "2025-05-17", 102, 2);
        prestamos[2] = new Prestamos(3, "2025-05-05", "2025-05-20", 103, 3);

        for (Prestamos p : prestamos) {
            System.out.println(p.registro());
        }
    }

}