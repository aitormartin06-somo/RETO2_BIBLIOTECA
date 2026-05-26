import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InputOutput {

    public static Connection getConexion(String u, String n, String p) {
        log("Intentando conectar a: " + u);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(u, n, p);
            log("Conexión establecida correctamente.");
            return conn;
        } catch (ClassNotFoundException e) {
            log("No se encontró el driver JDBC de MySQL. Asegúrate de tener el JAR en el classpath.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            log("Error al conectar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void log(String mensaje) {
        System.out.println("[REGISTRO InputOutput] " + mensaje);
    }

    public static void registro() {
        log("Clase de acceso a conexión JDBC preparada.");
    }
}
