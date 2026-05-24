import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InputOutput {

    public static Connection getConexion(String u, String n, String p) {

        try {
            Connection conn = null;
            conn = DriverManager.getConnection(u, n, p);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
