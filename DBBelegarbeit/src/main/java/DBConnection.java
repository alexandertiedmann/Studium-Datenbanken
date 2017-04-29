import java.sql.*;

/**
 * Verbindung fuer DB
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 21.04.2017
 */
public class DBConnection {
  private Connection db;
  private Statement stmt;

  /**
   * Konstruktor fuer die Verbindung
   * Automatische Verbindung zur Belegarbeitsdatenbank
   */
  public DBConnection(){
    String url = "jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0556127__belegarbeit";
    String user = "_s0556127__belegarbeit_generic";
    String password = "passwortausderhoelle";
    db = null;
    stmt = null;
    try {
      db = DriverManager.getConnection(url, user, password);
      stmt = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    } catch (SQLException e) {
      System.out.println("Eine Verbindung ist nicht moeglich:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
      System.exit(1);
    }
  }

  /**
   * fuehrt ein Update aus
   *
   * @param query Query
   * @return Anzahl der geanderten Zeilen
   */
  public int executeUpdateQuery(String query) {
    int erg = 0;
    try {
      erg = stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("Ein SQL Fehler ist aufgetreten:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    }
    return erg;
  }

  /**
   * fuehrt einen select query aus
   *
   * @param query auszufuehrender query
   * @return ergebnisse des Querys
   */
  public ResultSet executeSelectQuery(String query) {
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery(query);
    } catch (SQLException e) {
      System.out.println("Ein SQL Fehler ist aufgetreten:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    }
    return rs;
  }

  /**
   * Query der eine Anzahl Zeilen ausgibt
   *
   * @param query Query
   * @return anzahl der Zeilen
   */
  public int executeCountQuery(String query) {
    ResultSet rs = executeSelectQuery(query);
    int anzahl = 0;
    try {
      while (rs.next()) {
        anzahl = rs.getInt(1);
      }
    } catch (SQLException e) {
      System.out.println("Ergebnisfehler nach der Abfrage ist aufgetreten:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    } catch (NullPointerException s) {
      System.out.println("Es ist ein Fehler aufgetreten:");
      System.out.println(s.getMessage() + System.lineSeparator() + s.getStackTrace());
    }
    return anzahl;
  }

  /**
   * Beenden der Verbindung und aufraeumen
   */
  public void closeConnection(){
    try {
      stmt.close();
      db.close();
    } catch (SQLException e) {
      System.out.println("Die Verbindung konnte nicht geschlossen werden.");
    }
  }
}
