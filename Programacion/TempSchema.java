import java.sql.*;
public class TempSchema {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca?useSSL=false","root","" );
    try (Statement stmt = cnx.createStatement()) {
      try (ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE usuarios")) {
        while (rs.next()) {
          System.out.println(rs.getString(1));
          System.out.println(rs.getString(2));
        }
      }
      System.out.println("--- COLUMNS ---");
      try (ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM usuarios")) {
        while (rs.next()) {
          System.out.println(rs.getString(1) + " | " + rs.getString(2));
        }
      }
    } finally {
      cnx.close();
    }
  }
}
