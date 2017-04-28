import java.sql.*;

/**
 * Verbindung fuer DB
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 21.04.2017
 */
public class DBConnection {
  public static Statement createConnection() {
    String url = "jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0556127__belegarbeit";
    String user = "_s0556127__belegarbeit_generic";
    String password = "passwortausderhoelle";
    Connection db = null;
    Statement stmt = null;
    try {
      db = DriverManager.getConnection(url, user, password);
      stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    } catch (SQLException e) {
      System.out.println("Eine Verbindung ist nicht moeglich:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
      System.exit(1);
    }
    return stmt;
  }
}
