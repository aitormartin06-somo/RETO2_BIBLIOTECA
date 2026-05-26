import java.sql.Connection;

public class testConexion {

    public static void main(String[] args) {

        String hostname = "localhost";
        String database = "biblioteca";
        String username = "root";
        String password = "";
        Connection cnx = null;

        String url = "jdbc:mysql://" + hostname + ":3306/" + database + "?useSSL=false";

        cnx = InputOutput.getConexion(url, username, password);
        if (cnx == null) {
            System.out.println("[REGISTRO testConexion] Sin conexion a la base de datos :(");
            return;
        }
        System.out.println("[REGISTRO testConexion] Conexion a la base de datos exitosa !!!");
    }
}
