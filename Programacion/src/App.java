import arrays.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import model.*;

public class App {

    public static final String HOSTNAME = "localhost";
    public static final String DATABASE = "biblioteca";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:mysql://" + HOSTNAME + ":3306/" + DATABASE + "?useSSL=false";

    public static Connection getConexion() {
        return InputOutput.getConexion(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Validar conexion al iniciar; salir si no hay conexion
            if (!validarConexion()) {
                System.out.println("Saliendo por falta de conexión.");
                return;
            }
            cargarDatosInicialesSiEsNecesario();
            log("Inicio del programa");
            registrarTodasLasClases();

            boolean salir = false;

            while (!salir) {
                mostrarMenu();
                System.out.print("Seleccione una opcion: ");
                String opcion = scanner.nextLine().trim();

                switch (opcion) {
                    case "1":
                        ejecutarConsultar(scanner);
                        break;
                    case "2":
                        ejecutarInsertar(scanner);
                        break;
                    case "3":
                        ejecutarActualizar(scanner);
                        break;
                    case "4":
                        ejecutarEliminar(scanner);
                        break;
                    case "5":
                        salir = true;
                        System.out.println("Saliendo del programa. Hasta luego.");
                        break;
                    default:
                        System.out.println("Opcion no valida. Intente de nuevo.");
                }

                if (!salir) {
                    System.out.println();
                }
            }
        }
    }

    public static void mostrarMenu() {

        System.out.println();
        System.out.println("==========================");
        System.out.println(" MENU DE LA BASE DE DATOS");
        System.out.println("==========================");
        System.out.println("1. Consultar");
        System.out.println("2. Insertar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("5. Salir");
    }

    public static void log(String mensaje) {
        System.out.println("[REGISTRO App] " + mensaje);
    }

    public static void cargarDatosInicialesSiEsNecesario() {
        if (!esquemaBibliotecaCorrecto()) {
            System.out.println("\n[REGISTRO] Esquema de la base de datos incorrecto o incompleto. Se recrearán las tablas esperadas.");
            crearEsquemaBiblioteca();
        }

        boolean necesitaCarga = tablaVacia("libros") || tablaVacia("autores") || tablaVacia("usuarios")
                || tablaVacia("ejemplares") || tablaVacia("prestamos") || tablaVacia("libros_autores")
                || tablaVacia("penalizaciones");

        if (!necesitaCarga) {
            return;
        }

        System.out.println("\n[REGISTRO] Base de datos vacía detectada. Insertando datos de ejemplo...");

        Penalizaciones[] penalizaciones = new Penalizaciones[] {
                new Penalizaciones(1, "Retraso leve", 3),
                new Penalizaciones(2, "Retraso medio", 5),
                new Penalizaciones(3, "Retraso grave", 10),
                new Penalizaciones(4, "Pérdida de libro", 30),
                new Penalizaciones(5, "Daño severo", 15)
        };
        for (Penalizaciones penalizacion : penalizaciones) {
            insertarPenalizacionBD(penalizacion);
        }

        Autor[] autores = new Autor[] {
                new Autor(11, "Isabel", "Allende", "Chile"),
                new Autor(12, "Ray", "Bradbury", "EEUU"),
                new Autor(13, "Jorge Luis", "Borges", "Argentina"),
                new Autor(14, "Albert", "Camus", "Argelia"),
                new Autor(15, "Virginia", "Woolf", "Reino Unido")
        };
        for (Autor autor : autores) {
            insertarAutorBD(autor);
        }

        Libro[] libros = new Libro[] {
                new Libro(11, "Crónica de una muerte", 9788439734130L, "Random House", "Realismo mágico", 7),
                new Libro(12, "Fahrenheit 451", 9788490325070L, "Debolsillo", "Distopía", 9),
                new Libro(13, "El resplandor", 9788497593748L, "Debolsillo", "Terror", 5),
                new Libro(14, "Sapiens", 9788499926223L, "Debate", "Ensayo", 25),
                new Libro(15, "Dune", 9788497596824L, "Debolsillo", "Ciencia ficción", 11)
        };
        for (Libro libro : libros) {
            insertarLibroBD(libro);
        }

        Usuarios[] usuarios = new Usuarios[] {
                new Usuarios(10, "01234567K", "Roberto Sanz", 600000010L, "rob10", 10123456789L, 1),
                new Usuarios(11, "11122233L", "Ana Belén", 600000011L, "anaB11", 11122334455L, 1),
                new Usuarios(12, "22233344M", "Pedro Picazo", 600000012L, "pedroP", 22233445566L, 1),
                new Usuarios(13, "33344455N", "Marta Sánchez", 600000013L, "martaS", 33344556677L, 1),
                new Usuarios(14, "44455566O", "Diego Alva", 600000014L, "diegoA", 44455667788L, 1)
        };
        for (Usuarios usuario : usuarios) {
            insertarUsuarioBD(usuario);
        }

        Ejemplares[] ejemplares = new Ejemplares[] {
                new Ejemplares(11, "Nuevo", 11),
                new Ejemplares(12, "Usado", 11),
                new Ejemplares(13, "Dañado", 12),
                new Ejemplares(14, "Nuevo", 12),
                new Ejemplares(15, "Aceptable", 13)
        };
        for (Ejemplares ejemplar : ejemplares) {
            insertarEjemplarBD(ejemplar);
        }

        Prestamos[] prestamos = new Prestamos[] {
                new Prestamos(11, "2025-11-10", "2025-11-17", 11, 10),
                new Prestamos(12, "2025-11-11", "2025-11-18", 12, 11),
                new Prestamos(13, "2025-11-12", "2025-11-19", 13, 12),
                new Prestamos(14, "2025-11-13", "2025-11-20", 14, 13),
                new Prestamos(15, "2025-11-14", "2025-11-21", 15, 14)
        };
        for (Prestamos prestamo : prestamos) {
            insertarPrestamoBD(prestamo);
        }

        Libros_Autores[] relaciones = new Libros_Autores[] {
                new Libros_Autores(11, 11),
                new Libros_Autores(12, 12),
                new Libros_Autores(13, 13),
                new Libros_Autores(14, 14),
                new Libros_Autores(15, 15)
        };
        for (Libros_Autores relacion : relaciones) {
            insertarLibrosAutoresBD(relacion);
        }
    }

    public static void registrarTodasLasClases() {
        System.out.println("\n[REGISTRO] Registro de las clases disponibles:");
        ArrayLibros.registro();
        ArrayUsuarios.registro();
        ArraysAutores.registro();
        ArraysEjemplares.registro();
        ArrayPrestamos.registro();

        Autor[] autores = new Autor[] {
            new Autor(1, "Gabriel", "García", "Española"),
            new Autor(2, "Isabela", "Martínez", "Colombiana"),
            new Autor(3, "Rafa", "López", "Mexicana"),
            new Autor(4, "Emanuel", "González", "Argentina"),
            new Autor(5, "Carlos", "Fernández", "Peruana")
        };
        for (Autor autor : autores) {
            System.out.println(autor.registro());
        }

        Libro[] libros = new Libro[] {
            new Libro(1, "El nombre del viento", 978849989113L, "Plaza & Janes", "Fantasía", 5),
            new Libro(2, "Eliza y la bestia", 978607748291L, "Debolsillo", "Romance", 3),
            new Libro(3, "El principito", 978849838959L, "Aguilar", "Infantil", 10),
            new Libro(4, "El resplandor", 978849032447L, "Editorial Planeta", "Terror", 4),
            new Libro(5, "El psicoanalista", 978846632614L, "Tusquets", "Suspense", 6)
        };
        for (Libro libro : libros) {
            System.out.println(libro.registro());
        }

        Usuarios[] usuarios = new Usuarios[] {
            new Usuarios(1, "12345678A", "Juan", 123456789L, "clave123", 123456789L, 0),
            new Usuarios(2, "87654321B", "María", 987654321L, "pass456", 987654321L, 1),
            new Usuarios(3, "45678912C", "Pedro", 456789123L, "usuario789", 456789123L, 0),
            new Usuarios(4, "78912345D", "Lucía", 789123456L, "segura123", 789123456L, 2),
            new Usuarios(5, "32165498E", "Carlos", 321654987L, "clave999", 321654987L, 0)
        };
        for (Usuarios usuario : usuarios) {
            System.out.println(usuario.registro());
        }

        Ejemplares[] ejemplares = new Ejemplares[] {
            new Ejemplares(1, "Nuevo", 101),
            new Ejemplares(2, "Usado", 102),
            new Ejemplares(3, "Dañado", 103),
            new Ejemplares(4, "Regular", 104),
            new Ejemplares(5, "Nuevo", 105)
        };
        for (Ejemplares ejemplar : ejemplares) {
            System.out.println(ejemplar.registro());
        }

        Prestamos[] prestamos = new Prestamos[] {
            new Prestamos(1, "2025-05-01", "2025-05-15", 101, 1),
            new Prestamos(2, "2025-05-03", "2025-05-17", 102, 2),
            new Prestamos(3, "2025-05-05", "2025-05-20", 103, 3),
            new Prestamos(4, "2025-05-07", "2025-05-21", 104, 4),
            new Prestamos(5, "2025-05-09", "2025-05-23", 105, 5)
        };
        for (Prestamos prestamo : prestamos) {
            System.out.println(prestamo.registro());
        }

        Penalizaciones[] penalizaciones = new Penalizaciones[] {
            new Penalizaciones(1, "Retraso leve", 3),
            new Penalizaciones(2, "Retraso medio", 5),
            new Penalizaciones(3, "Retraso grave", 10),
            new Penalizaciones(4, "Pérdida de libro", 30),
            new Penalizaciones(5, "Daño severo", 15)
        };
        for (Penalizaciones penalizacion : penalizaciones) {
            System.out.println(penalizacion.registro());
        }

        Libros_Autores[] relaciones = new Libros_Autores[] {
            new Libros_Autores(101, 1),
            new Libros_Autores(102, 2),
            new Libros_Autores(103, 3),
            new Libros_Autores(104, 4),
            new Libros_Autores(105, 5)
        };
        for (Libros_Autores relacion : relaciones) {
            System.out.println(relacion.registro());
        }

        InputOutput.registro();
    }

    public static boolean validarConexion() {
        System.out.println("\n Comprobando conexión a la base de datos...");
        Connection cnx = null;
        try {
            cnx = getConexion();
            if (cnx != null && !cnx.isClosed()) {
                System.out.println("\n Conexión establecida correctamente a la base de datos " + DATABASE + ".");
                return true;
            } else {
                System.out.println("\n No se pudo establecer conexión con la base de datos " + DATABASE + ".");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("\n Error al comprobar la conexión: " + e.getMessage());
            return false;
        } finally {
            if (cnx != null) {
                try {
                    cnx.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }

    public static void ejecutarConsultar(Scanner scanner) {
        while (true) {
            System.out.println("\n-- CONSULTAR --");
            System.out.println("1. Libros");
            System.out.println("2. Autores");
            System.out.println("3. Usuarios");
            System.out.println("4. Ejemplares");
            System.out.println("5. Prestamos");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    consultarLibrosBD();
                    return;
                case "2":
                    consultarAutoresBD();
                    return;
                case "3":
                    consultarUsuariosBD();
                    return;
                case "4":
                    consultarEjemplaresBD();
                    return;
                case "5":
                    consultarPrestamosBD();
                    return;
                case "6":
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    public static void ejecutarInsertar(Scanner scanner) {
        while (true) {
            System.out.println("\n-- INSERTAR --");
            System.out.println("1. Libro");
            System.out.println("2. Autor");
            System.out.println("3. Usuario");
            System.out.println("4. Ejemplar");
            System.out.println("5. Prestamo");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    insertarLibroMenu(scanner);
                    return;
                case "2":
                    insertarAutorMenu(scanner);
                    return;
                case "3":
                    insertarUsuarioMenu(scanner);
                    return;
                case "4":
                    insertarEjemplarMenu(scanner);
                    return;
                case "5":
                    insertarPrestamoMenu(scanner);
                    return;
                case "6":
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    public static void ejecutarActualizar(Scanner scanner) {
        while (true) {
            System.out.println("\n-- ACTUALIZAR --");
            System.out.println("1. Editorial de libro");
            System.out.println("2. Nacionalidad de autor");
            System.out.println("3. Telefono de usuario");
            System.out.println("4. Estado fisico de ejemplar");
            System.out.println("5. Fecha devolución de préstamo");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    actualizarEditorialMenu(scanner);
                    return;
                case "2":
                    actualizarNacionalidadAutorMenu(scanner);
                    return;
                case "3":
                    actualizarTelefonoUsuarioMenu(scanner);
                    return;
                case "4":
                    actualizarEstadoEjemplarMenu(scanner);
                    return;
                case "5":
                    actualizarFechaDevolucionPrestamoMenu(scanner);
                    return;
                case "6":
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    public static void ejecutarEliminar(Scanner scanner) {
        while (true) {
            System.out.println("\n-- ELIMINAR --");
            System.out.println("1. Libro");
            System.out.println("2. Autor");
            System.out.println("3. Usuario");
            System.out.println("4. Ejemplar");
            System.out.println("5. Prestamo");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    eliminarLibroMenu(scanner);
                    return;
                case "2":
                    eliminarAutorMenu(scanner);
                    return;
                case "3":
                    eliminarUsuarioMenu(scanner);
                    return;
                case "4":
                    eliminarEjemplarMenu(scanner);
                    return;
                case "5":
                    eliminarPrestamoMenu(scanner);
                    return;
                case "6":
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    public static void insertarLibroMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los datos del libro a insertar:");
            System.out.print("Id del libro: ");
            int nuevoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Titulo: ");
            String nuevoTitulo = scanner.nextLine();
            System.out.print("ISBN: ");
            long nuevoIsbn = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Editorial: ");
            String nuevaEditorial = scanner.nextLine();
            System.out.print("Genero: ");
            String nuevoGenero = scanner.nextLine();
            System.out.print("Numero de copias: ");
            int nuevasCopias = Integer.parseInt(scanner.nextLine().trim());

            insertarLibroBD(new Libro(nuevoId, nuevoTitulo, nuevoIsbn, nuevaEditorial, nuevoGenero, nuevasCopias));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar numeros donde se requiere.");
        }
    }

    public static void actualizarEditorialMenu(Scanner scanner) {
        if (tablaVacia("libros")) {
            System.out.println("La tabla libros está vacía. No se puede actualizar ningún libro.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del libro a actualizar:");
            int idLibroActualizar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nueva editorial: ");
            String editorialActualizada = scanner.nextLine();

            actualizarEditorialLibro(idLibroActualizar, editorialActualizada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void eliminarLibroMenu(Scanner scanner) {
        if (tablaVacia("libros")) {
            System.out.println("La tabla libros está vacía. No se puede eliminar ningún libro.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del libro a eliminar:");
            int idLibroEliminar = Integer.parseInt(scanner.nextLine().trim());
            eliminarLibro(idLibroEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void insertarAutorMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los datos del autor a insertar:");
            System.out.print("Id del autor: ");
            int nuevoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nombre: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Apellidos: ");
            String nuevosApellidos = scanner.nextLine();
            System.out.print("Nacionalidad: ");
            String nuevaNacionalidad = scanner.nextLine();

            insertarAutorBD(new Autor(nuevoId, nuevoNombre, nuevosApellidos, nuevaNacionalidad));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void actualizarNacionalidadAutorMenu(Scanner scanner) {
        if (tablaVacia("autores")) {
            System.out.println("La tabla autores está vacía. No se puede actualizar ningún autor.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del autor a actualizar:");
            int idAutorActualizar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nueva nacionalidad: ");
            String nacionalidadActualizada = scanner.nextLine();

            actualizarNacionalidadAutor(idAutorActualizar, nacionalidadActualizada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void eliminarAutorMenu(Scanner scanner) {
        if (tablaVacia("autores")) {
            System.out.println("La tabla autores está vacía. No se puede eliminar ningún autor.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del autor a eliminar:");
            int idAutorEliminar = Integer.parseInt(scanner.nextLine().trim());
            eliminarAutor(idAutorEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void consultarLibrosBD() {
        if (tablaVacia("libros")) {
            System.out.println("La tabla libros está vacía. No hay información para mostrar.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT id_libro, titulo, isbn, editorial, genero, num_copias FROM libros";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("===================================================");
            System.out.println("======== CONSULTA DE LIBROS ========");
            System.out.println("===================================================");
            while (rs.next()) {
                int id = rs.getInt("id_libro");
                String titulo = rs.getString("titulo");
                long isbn = rs.getLong("isbn");
                String editorial = rs.getString("editorial");
                String genero = rs.getString("genero");
                int numCopias = rs.getInt("num_copias");

                Libro libro = new Libro(id, titulo, isbn, editorial, genero, numCopias);
                System.out.println(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar libros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean existeLibro(int idLibro) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM libros WHERE id_libro = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idLibro);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            // Detectar violación de integridad referencial y mostrar mensaje más claro
            String msg = e.getMessage();
            if (msg != null && msg.toLowerCase().contains("foreign key") || msg.toLowerCase().contains("constraint")) {
                System.out.println(
                        "No se puede eliminar el libro porque existen registros relacionados (ejemplares o préstamos).\n"
                                +
                                "Elimine primero los préstamos y/o ejemplares relacionados y vuelva a intentarlo.");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertarLibroBD(Libro libro) {
        if (existeLibro(libro.getId_libro())) {
            System.out.println("El libro con id " + libro.getId_libro() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO libros (id_libro, titulo, isbn, editorial, genero, num_copias) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {

            pstmt.setInt(1, libro.getId_libro());
            pstmt.setString(2, libro.getTitulo());
            pstmt.setLong(3, libro.getIsbn());
            pstmt.setString(4, libro.getEditorial());
            pstmt.setString(5, libro.getGenero());
            pstmt.setInt(6, libro.getNum_copias());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Libro insertado exitosamente:");
                System.out.println(libro);
            } else {
                System.out.println("No se insertó ningún libro.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarLibro(int idLibro) {
        Libro libro = obtenerLibroPorId(idLibro);
        if (libro == null) {
            System.out.println("No existe un libro con id " + idLibro + ". No se eliminará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM libros WHERE id_libro = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {

            pstmt.setInt(1, idLibro);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Libro eliminado:");
                System.out.println(libro);
            } else {
                System.out.println("No se eliminó ningún libro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Libro obtenerLibroPorId(int idLibro) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return null;
        }

        String sql = "SELECT id_libro, titulo, isbn, editorial, genero, num_copias FROM libros WHERE id_libro = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idLibro);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_libro");
                    String titulo = rs.getString("titulo");
                    long isbn = rs.getLong("isbn");
                    String editorial = rs.getString("editorial");
                    String genero = rs.getString("genero");
                    int numCopias = rs.getInt("num_copias");
                    return new Libro(id, titulo, isbn, editorial, genero, numCopias);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void actualizarEditorialLibro(int idLibro, String nuevaEditorial) {
        Libro libro = obtenerLibroPorId(idLibro);
        if (libro == null) {
            System.out.println("No existe un libro con id " + idLibro + ". No se actualizará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "UPDATE libros SET editorial = ? WHERE id_libro = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {

            pstmt.setString(1, nuevaEditorial);
            pstmt.setInt(2, idLibro);
            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Libro actualizado exitosamente.");
                System.out.println("Editorial anterior: " + libro.getEditorial());
                System.out.println("Editorial nueva: " + nuevaEditorial);
            } else {
                System.out.println("No se actualizó ningún libro.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void consultarAutoresBD() {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT id_autor, nombre, apellidos, nacionalidad FROM autores";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("==================================================");
            System.out.println("======== CONSULTA DE AUTORES =====");
            System.out.println("==================================================");

            while (rs.next()) {
                int id = rs.getInt("id_autor");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String nacionalidad = rs.getString("nacionalidad");

                Autor autor = new Autor(id, nombre, apellidos, nacionalidad);
                System.out.println(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean existeAutor(int idAutor) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM autores WHERE id_autor = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idAutor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertarAutorBD(Autor autor) {
        if (existeAutor(autor.getId_autor())) {
            System.out.println("El autor con id " + autor.getId_autor() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO autores (id_autor, nombre, apellidos, nacionalidad) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, autor.getId_autor());
            pstmt.setString(2, autor.getNombre());
            pstmt.setString(3, autor.getApellidos());
            pstmt.setString(4, autor.getNacionalidad());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Autor insertado exitosamente:");
                System.out.println(autor);
            } else {
                System.out.println("No se insertó ningún autor.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar autor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Autor obtenerAutorPorId(int idAutor) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return null;
        }

        String sql = "SELECT id_autor, nombre, apellidos, nacionalidad FROM autores WHERE id_autor = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idAutor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_autor");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String nacionalidad = rs.getString("nacionalidad");
                    return new Autor(id, nombre, apellidos, nacionalidad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void actualizarNacionalidadAutor(int idAutor, String nuevaNacionalidad) {
        Autor autor = obtenerAutorPorId(idAutor);
        if (autor == null) {
            System.out.println("No existe un autor con id " + idAutor + ". No se actualizará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "UPDATE autores SET nacionalidad = ? WHERE id_autor = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, nuevaNacionalidad);
            pstmt.setInt(2, idAutor);
            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Autor actualizado exitosamente.");
                System.out.println("Nacionalidad anterior: " + autor.getNacionalidad());
                System.out.println("Nacionalidad nueva: " + nuevaNacionalidad);
            } else {
                System.out.println("No se actualizó ningún autor.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar autor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarAutor(int idAutor) {
        Autor autor = obtenerAutorPorId(idAutor);
        if (autor == null) {
            System.out.println("No existe un autor con id " + idAutor + ". No se eliminará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM autores WHERE id_autor = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idAutor);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Autor eliminado:");
                System.out.println(autor);
            } else {
                System.out.println("No se eliminó ningun autor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertarUsuarioMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los datos del usuario a insertar:");
            System.out.print("Id del usuario: ");
            int nuevoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("DNI: ");
            String nuevoDni = scanner.nextLine();
            System.out.print("Nombre: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Telefono: ");
            long nuevoTelefono = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Password: ");
            String nuevaPassword = scanner.nextLine();
            System.out.print("NSS: ");
            long nuevoNss = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Id penalizacion: ");
            int nuevaIdPenalizacion = Integer.parseInt(scanner.nextLine().trim());

            insertarUsuarioBD(new Usuarios(nuevoId, nuevoDni, nuevoNombre, nuevoTelefono, nuevaPassword, nuevoNss,
                    nuevaIdPenalizacion));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar numeros donde se requiere.");
        }
    }

    public static void actualizarTelefonoUsuarioMenu(Scanner scanner) {
        if (tablaVacia("usuarios")) {
            System.out.println("La tabla usuarios está vacía. No se puede actualizar ningún usuario.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del usuario a actualizar:");
            int idUsuarioActualizar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nuevo telefono: ");
            long telefonoActualizado = Long.parseLong(scanner.nextLine().trim());

            actualizarTelefonoUsuario(idUsuarioActualizar, telefonoActualizado);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id y telefono numericos.");
        }
    }

    public static void eliminarUsuarioMenu(Scanner scanner) {
        if (tablaVacia("usuarios")) {
            System.out.println("La tabla usuarios está vacía. No se puede eliminar ningún usuario.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del usuario a eliminar:");
            int idUsuarioEliminar = Integer.parseInt(scanner.nextLine().trim());
            eliminarUsuario(idUsuarioEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void consultarUsuariosBD() {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("====================================================");
            System.out.println("======== CONSULTA DE USUARIOS EN BASE DE DATOS =====");
            System.out.println("====================================================");

            while (rs.next()) {
                int id = rs.getInt(1);
                String dni = rs.getString(2);
                String nombre = rs.getString(3);
                long telefono = rs.getLong(4);
                String password = rs.getString(5);
                long nss = rs.getLong(6);
                int idPenalizacion = 0;
                try {
                    idPenalizacion = rs.getInt(7);
                    if (rs.wasNull())
                        idPenalizacion = -1;
                } catch (SQLException ex) {
                    idPenalizacion = -1;
                }

                Usuarios usuario = new Usuarios(id, dni, nombre, telefono, password, nss, idPenalizacion);
                System.out.println(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean existeUsuario(int idUsuario) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE id_user = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean existePenalizacion(int idPenalizacion) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM penalizaciones WHERE id_penalizacion = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idPenalizacion);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertarPenalizacionBD(Penalizaciones penalizacion) {
        if (existePenalizacion(penalizacion.getId_penalizacion())) {
            System.out.println("La penalización con id " + penalizacion.getId_penalizacion() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO penalizaciones (id_penalizacion, descripcion, num_dias) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, penalizacion.getId_penalizacion());
            pstmt.setString(2, penalizacion.getDescripcion());
            pstmt.setInt(3, penalizacion.getNum_dias_penalizacion());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Penalización insertada exitosamente:");
                System.out.println(penalizacion.registro());
            } else {
                System.out.println("No se insertó ninguna penalización.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar penalización: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean tablaExiste(String tabla) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        try {
            DatabaseMetaData meta = cnx.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, tabla, new String[] { "TABLE" })) {
                if (rs.next()) {
                    return true;
                }
            }
            try (ResultSet rs = meta.getTables(null, null, tabla.toUpperCase(), new String[] { "TABLE" })) {
                if (rs.next()) {
                    return true;
                }
            }
            try (ResultSet rs = meta.getTables(null, null, tabla.toLowerCase(), new String[] { "TABLE" })) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean esquemaBibliotecaCorrecto() {
        return tablaExiste("libros") && tablaExiste("autores") && tablaExiste("usuarios")
                && tablaExiste("ejemplares") && tablaExiste("prestamos") && tablaExiste("libros_autores")
                && tablaExiste("penalizaciones");
    }

    public static void crearEsquemaBiblioteca() {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String[] statements = new String[] {
                "DROP TABLE IF EXISTS PRESTAMOS",
                "DROP TABLE IF EXISTS EJEMPLARES",
                "DROP TABLE IF EXISTS USUARIOS",
                "DROP TABLE IF EXISTS PENALIZACIONES",
                "DROP TABLE IF EXISTS LIBROS_AUTORES",
                "DROP TABLE IF EXISTS AUTORES",
                "DROP TABLE IF EXISTS LIBROS",

                "CREATE TABLE LIBROS ("
                        + "ID_LIBRO INT PRIMARY KEY AUTO_INCREMENT,"
                        + "TITULO VARCHAR(100) NOT NULL,"
                        + "ISBN BIGINT UNIQUE,"
                        + "EDITORIAL VARCHAR(50),"
                        + "GENERO VARCHAR(30),"
                        + "NUM_COPIAS INT DEFAULT 0"
                        + ")",

                "CREATE TABLE AUTORES ("
                        + "ID_AUTOR INT PRIMARY KEY AUTO_INCREMENT,"
                        + "NOMBRE VARCHAR(50),"
                        + "APELLIDOS VARCHAR(50),"
                        + "NACIONALIDAD VARCHAR(30)"
                        + ")",

                "CREATE TABLE LIBROS_AUTORES ("
                        + "ID_LIBRO INT,"
                        + "ID_AUTOR INT,"
                        + "PRIMARY KEY (ID_LIBRO, ID_AUTOR),"
                        + "CONSTRAINT FK_ID_LIBRO FOREIGN KEY (ID_LIBRO) REFERENCES LIBROS (ID_LIBRO) ON DELETE CASCADE,"
                        + "CONSTRAINT FK_ID_AUTOR FOREIGN KEY (ID_AUTOR) REFERENCES AUTORES (ID_AUTOR) ON DELETE CASCADE"
                        + ")",

                "CREATE TABLE PENALIZACIONES ("
                        + "ID_PENALIZACION INT PRIMARY KEY AUTO_INCREMENT,"
                        + "DESCRIPCION VARCHAR(100),"
                        + "NUM_DIAS INT"
                        + ")",

                "CREATE TABLE USUARIOS ("
                        + "ID_USER INT PRIMARY KEY AUTO_INCREMENT,"
                        + "DNI_USER VARCHAR(10) UNIQUE,"
                        + "NOMBRE_USER VARCHAR(50),"
                        + "TELEFONO_USER VARCHAR(15),"
                        + "PASSWORD_USER VARCHAR(100),"
                        + "NSS BIGINT,"
                        + "ID_PENALIZACION INT,"
                        + "FECHA_PENALIZACION DATE,"
                        + "CONSTRAINT FK_ID_PENALIZACION FOREIGN KEY (ID_PENALIZACION) REFERENCES PENALIZACIONES (ID_PENALIZACION)"
                        + ")",

                "CREATE TABLE EJEMPLARES ("
                        + "ID_EJEMPLAR INT PRIMARY KEY AUTO_INCREMENT,"
                        + "ESTADO_FISICO VARCHAR(50),"
                        + "ID_LIBRO INT,"
                        + "CONSTRAINT FK_COD_LIBRO FOREIGN KEY (ID_LIBRO) REFERENCES LIBROS (ID_LIBRO) ON DELETE CASCADE"
                        + ")",

                "CREATE TABLE PRESTAMOS ("
                        + "ID_PRESTAMO INT PRIMARY KEY AUTO_INCREMENT,"
                        + "FECHA_ALQUILER DATE,"
                        + "FECHA_DEVOLUCION DATE,"
                        + "ID_EJEMPLAR INT,"
                        + "ID_USER INT,"
                        + "CONSTRAINT FK_ID_USUARIO FOREIGN KEY (ID_USER) REFERENCES USUARIOS (ID_USER),"
                        + "CONSTRAINT FK_ID_EJEMPLAR FOREIGN KEY (ID_EJEMPLAR) REFERENCES EJEMPLARES (ID_EJEMPLAR)"
                        + ")"
        };

        try (Statement stmt = cnx.createStatement()) {
            for (String sql : statements) {
                stmt.executeUpdate(sql);
            }
            System.out.println("[REGISTRO] Esquema de base de datos recreado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al recrear el esquema de la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean tablaVacia(String tabla) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return true;
        }

        String sql = "SELECT COUNT(*) AS total FROM " + tabla;

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total") == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static void insertarUsuarioBD(Usuarios usuario) {
        if (existeUsuario(usuario.getid_user())) {
            System.out.println("El usuario con id " + usuario.getid_user() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO usuarios (id_user, dni_user, nombre_user, telefono_user, password_user, nss, id_penalizacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, usuario.getid_user());
            pstmt.setString(2, usuario.getdni_user());
            pstmt.setString(3, usuario.getnombre_user());
            pstmt.setLong(4, usuario.gettelefono_user());
            pstmt.setString(5, usuario.getpassword_user());
            pstmt.setLong(6, usuario.getNss());
            if (usuario.getId_penalizacion() > 0) {
                pstmt.setInt(7, usuario.getId_penalizacion());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Usuario insertado exitosamente:");
                System.out.println(usuario);
            } else {
                System.out.println("No se insertó ningún usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static Usuarios obtenerUsuarioPorId(int idUsuario) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return null;
        }

        String sql = "SELECT id_user, dni_user, nombre_user, telefono_user, password_user, nss, id_penalizacion FROM usuarios WHERE id_user = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_user");
                    String dni = rs.getString("dni_user");
                    String nombre = rs.getString("nombre_user");
                    long telefono = rs.getLong("telefono_user");
                    String password = rs.getString("password_user");
                    long nss = rs.getLong("nss");
                    int idPenalizacion = rs.getInt("id_penalizacion");
                    return new Usuarios(id, dni, nombre, telefono, password, nss, idPenalizacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void actualizarTelefonoUsuario(int idUsuario, long nuevoTelefono) {
        Usuarios usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("No existe un usuario con id " + idUsuario + ". No se actualizará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "UPDATE usuarios SET telefono_user = ? WHERE id_user = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setLong(1, nuevoTelefono);
            pstmt.setInt(2, idUsuario);
            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Usuario actualizado exitosamente.");
                System.out.println("Teléfono anterior: " + usuario.gettelefono_user());
                System.out.println("Teléfono nuevo: " + nuevoTelefono);
            } else {
                System.out.println("No se actualizó ningún usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarUsuario(int idUsuario) {
        Usuarios usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("No existe un usuario con id " + idUsuario + ". No se eliminará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM usuarios WHERE id_user = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Usuario eliminado:");
                System.out.println(usuario);
            } else {
                System.out.println("No se eliminó ningun usuario.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertarEjemplarMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los datos del ejemplar a insertar:");
            System.out.print("Id del ejemplar: ");
            int nuevoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Estado fisico (Nuevo/Usado/Dañado/Aceptable): ");
            String nuevoEstado = scanner.nextLine();
            System.out.print("Id del libro: ");
            int nuevoIdLibro = Integer.parseInt(scanner.nextLine().trim());

            insertarEjemplarBD(new Ejemplares(nuevoId, nuevoEstado, nuevoIdLibro));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar numeros donde se requiere.");
        }
    }

    public static void actualizarEstadoEjemplarMenu(Scanner scanner) {
        if (tablaVacia("ejemplares")) {
            System.out.println("La tabla ejemplares está vacía. No se puede actualizar ningún ejemplar.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del ejemplar a actualizar:");
            int idEjemplarActualizar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nuevo estado fisico (Nuevo/Usado/Dañado/Aceptable): ");
            String estadoActualizado = scanner.nextLine();

            actualizarEstadoEjemplar(idEjemplarActualizar, estadoActualizado);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void eliminarEjemplarMenu(Scanner scanner) {
        if (tablaVacia("ejemplares")) {
            System.out.println("La tabla ejemplares está vacía. No se puede eliminar ningún ejemplar.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del ejemplar a eliminar:");
            int idEjemplarEliminar = Integer.parseInt(scanner.nextLine().trim());
            eliminarEjemplar(idEjemplarEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void consultarEjemplaresBD() {
        if (tablaVacia("ejemplares")) {
            System.out.println("La tabla ejemplares está vacía. No hay información para mostrar.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT id_ejemplar, estado_fisico, id_libro FROM ejemplares";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("===================================================");
            System.out.println("====== CONSULTA DE EJEMPLARES EN BASE DE DATOS ======");
            System.out.println("===================================================");
            while (rs.next()) {
                int id = rs.getInt("id_ejemplar");
                String estado = rs.getString("estado_fisico");
                int idLibro = rs.getInt("id_libro");

                Ejemplares ejemplar = new Ejemplares(id, estado, idLibro);
                System.out.println(ejemplar);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar ejemplares: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean existeEjemplar(int idEjemplar) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM ejemplares WHERE id_ejemplar = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idEjemplar);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertarEjemplarBD(Ejemplares ejemplar) {
        if (existeEjemplar(ejemplar.getId_ejemplar())) {
            System.out.println("El ejemplar con id " + ejemplar.getId_ejemplar() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO ejemplares (id_ejemplar, estado_fisico, id_libro) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, ejemplar.getId_ejemplar());
            pstmt.setString(2, ejemplar.getEstado_fisico());
            pstmt.setInt(3, ejemplar.getId_libro());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Ejemplar insertado exitosamente:");
                System.out.println(ejemplar);
            } else {
                System.out.println("No se insertó ningún ejemplar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar ejemplar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertarPrestamoMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los datos del préstamo a insertar:");
            System.out.print("Id del préstamo: ");
            int nuevoId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Fecha alquiler (YYYY-MM-DD): ");
            String fechaAlquiler = scanner.nextLine().trim();
            System.out.print("Fecha devolución (YYYY-MM-DD): ");
            String fechaDevolucion = scanner.nextLine().trim();
            System.out.print("Id del ejemplar: ");
            int idEjemplar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Id del usuario: ");
            int idUsuario = Integer.parseInt(scanner.nextLine().trim());

            insertarPrestamoBD(new Prestamos(nuevoId, fechaAlquiler, fechaDevolucion, idEjemplar, idUsuario));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar numeros donde se requiere.");
        }
    }

    public static void actualizarFechaDevolucionPrestamoMenu(Scanner scanner) {
        if (tablaVacia("prestamos")) {
            System.out.println("La tabla prestamos está vacía. No se puede actualizar ningún préstamo.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del préstamo a actualizar:");
            int idPrestamoActualizar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nueva fecha devolución (YYYY-MM-DD): ");
            String fechaActualizada = scanner.nextLine().trim();

            actualizarFechaDevolucionPrestamo(idPrestamoActualizar, fechaActualizada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void eliminarPrestamoMenu(Scanner scanner) {
        if (tablaVacia("prestamos")) {
            System.out.println("La tabla prestamos está vacía. No se puede eliminar ningún préstamo.");
            return;
        }

        try {
            System.out.println("\nIntroduce el id del préstamo a eliminar:");
            int idPrestamoEliminar = Integer.parseInt(scanner.nextLine().trim());
            eliminarPrestamo(idPrestamoEliminar);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar un id numerico.");
        }
    }

    public static void consultarPrestamosBD() {
        if (tablaVacia("prestamos")) {
            System.out.println("La tabla prestamos está vacía. No hay información para mostrar.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT * FROM prestamos";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("===================================================");
            System.out.println("====== CONSULTA DE PRESTAMOS ======");
            System.out.println("===================================================");
            while (rs.next()) {
                int id = rs.getInt(1);
                String fechaAlq = rs.getString(2);
                String fechaDev = rs.getString(3);
                int idEj = rs.getInt(4);
                int idUsr = rs.getInt(5);

                Prestamos prestamo = new Prestamos(id, fechaAlq, fechaDev, idEj, idUsr);
                System.out.println(prestamo);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar prestamos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // --- LIBROS_AUTORES (relación N:M) ---
    public static void consultarLibrosAutoresBD() {
        if (tablaVacia("libros_autores")) {
            System.out.println("La tabla libros_autores está vacía. No hay información para mostrar.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "SELECT * FROM libros_autores";

        try (Statement stmt = cnx.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("======================================");
            System.out.println("== RELACIÓN LIBROS - AUTORES (DB) ====");
            System.out.println("======================================");
            while (rs.next()) {
                int idLib = rs.getInt(1);
                int idAut = rs.getInt(2);
                System.out.println("Libro ID: " + idLib + "  <->  Autor ID: " + idAut);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar libros_autores: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertarLibrosAutoresMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los ids para crear la relación libro-autor:");
            System.out.print("Id libro: ");
            int idLib = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Id autor: ");
            int idAut = Integer.parseInt(scanner.nextLine().trim());

            insertarLibrosAutoresBD(new model.Libros_Autores(idLib, idAut));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar ids numericos.");
        }
    }

    public static void insertarLibrosAutoresBD(model.Libros_Autores la) {
        // verificar que libro y autor existen
        if (!existeLibro(la.getId_libro())) {
            System.out.println("No existe el libro con id " + la.getId_libro());
            return;
        }
        if (!existeAutor(la.getId_autor())) {
            System.out.println("No existe el autor con id " + la.getId_autor());
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO libros_autores (id_libro, id_autor) VALUES (?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, la.getId_libro());
            pstmt.setInt(2, la.getId_autor());
            int filas = pstmt.executeUpdate();
            if (filas > 0)
                System.out.println("Relación libro-autor insertada.");
            else
                System.out.println("No se insertó la relación.");
        } catch (SQLException e) {
            System.out.println("Error al insertar relación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarLibrosAutoresMenu(Scanner scanner) {
        try {
            System.out.println("\nIntroduce los ids de la relación a eliminar:");
            System.out.print("Id libro: ");
            int idLib = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Id autor: ");
            int idAut = Integer.parseInt(scanner.nextLine().trim());

            eliminarLibrosAutoresBD(idLib, idAut);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Asegurese de ingresar ids numericos.");
        }
    }

    public static void eliminarLibrosAutoresBD(int idLib, int idAut) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM libros_autores WHERE id_libro = ? AND id_autor = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idLib);
            pstmt.setInt(2, idAut);
            int filas = pstmt.executeUpdate();
            if (filas > 0)
                System.out.println("Relación eliminada.");
            else
                System.out.println("No se encontró la relación.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar relación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean existePrestamo(int idPrestamo) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE id_prestamo = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void insertarPrestamoBD(Prestamos prestamo) {
        if (existePrestamo(prestamo.getId_prestamo())) {
            System.out.println("El prestamo con id " + prestamo.getId_prestamo() + " ya existe. No se insertará.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "INSERT INTO prestamos (id_prestamo, fecha_alquiler, fecha_devolucion, id_ejemplar, id_user) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, prestamo.getId_prestamo());
            pstmt.setString(2, prestamo.getFecha_alquiler());
            pstmt.setString(3, prestamo.getFecha_devolucion());
            pstmt.setInt(4, prestamo.getId_ejemplar());
            pstmt.setInt(5, prestamo.getid_user());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Prestamo insertado exitosamente:");
                System.out.println(prestamo);
            } else {
                System.out.println("No se insertó ningún prestamo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar prestamo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Prestamos obtenerPrestamoPorId(int idPrestamo) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return null;
        }

        String sql = "SELECT id_prestamo, fecha_alquiler, fecha_devolucion, id_ejemplar, id_user FROM prestamos WHERE id_prestamo = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_prestamo");
                    String fechaAlq = rs.getString("fecha_alquiler");
                    String fechaDev = rs.getString("fecha_devolucion");
                    int idEj = rs.getInt("id_ejemplar");
                    int idUsr = rs.getInt("id_user");
                    return new Prestamos(id, fechaAlq, fechaDev, idEj, idUsr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void actualizarFechaDevolucionPrestamo(int idPrestamo, String nuevaFecha) {
        Prestamos prestamo = obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("No existe un prestamo con id " + idPrestamo + ". No se actualizará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "UPDATE prestamos SET fecha_devolucion = ? WHERE id_prestamo = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, nuevaFecha);
            pstmt.setInt(2, idPrestamo);
            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Prestamo actualizado exitosamente.");
                System.out.println("Fecha devolución anterior: " + prestamo.getFecha_devolucion());
                System.out.println("Fecha devolución nueva: " + nuevaFecha);
            } else {
                System.out.println("No se actualizó ningún prestamo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar prestamo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarPrestamo(int idPrestamo) {
        Prestamos prestamo = obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.out.println("No existe un prestamo con id " + idPrestamo + ". No se eliminará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM prestamos WHERE id_prestamo = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Prestamo eliminado:");
                System.out.println(prestamo);
            } else {
                System.out.println("No se eliminó ningún prestamo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar prestamo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Ejemplares obtenerEjemplarPorId(int idEjemplar) {
        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return null;
        }

        String sql = "SELECT id_ejemplar, estado_fisico, id_libro FROM ejemplares WHERE id_ejemplar = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idEjemplar);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_ejemplar");
                    String estado = rs.getString("estado_fisico");
                    int idLibro = rs.getInt("id_libro");
                    return new Ejemplares(id, estado, idLibro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void actualizarEstadoEjemplar(int idEjemplar, String nuevoEstado) {
        Ejemplares ejemplar = obtenerEjemplarPorId(idEjemplar);
        if (ejemplar == null) {
            System.out.println("No existe un ejemplar con id " + idEjemplar + ". No se actualizará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "UPDATE ejemplares SET estado_fisico = ? WHERE id_ejemplar = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idEjemplar);
            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Ejemplar actualizado exitosamente.");
                System.out.println("Estado anterior: " + ejemplar.getEstado_fisico());
                System.out.println("Estado nuevo: " + nuevoEstado);
            } else {
                System.out.println("No se actualizó ningún ejemplar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar ejemplar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void eliminarEjemplar(int idEjemplar) {
        Ejemplares ejemplar = obtenerEjemplarPorId(idEjemplar);
        if (ejemplar == null) {
            System.out.println("No existe un ejemplar con id " + idEjemplar + ". No se eliminará nada.");
            return;
        }

        Connection cnx = getConexion();
        if (cnx == null) {
            System.out.println("Sin conexion a la base de datos :(");
            return;
        }

        String sql = "DELETE FROM ejemplares WHERE id_ejemplar = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, idEjemplar);
            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Ejemplar eliminado:");
                System.out.println(ejemplar);
            } else {
                System.out.println("No se eliminó ningún ejemplar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar ejemplar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
