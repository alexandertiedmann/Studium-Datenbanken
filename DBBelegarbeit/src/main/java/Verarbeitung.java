import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Verarbeitung der Daten
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 28.04.2017
 */
public class Verarbeitung {
  DBConnection conn;

  public Verarbeitung() {
    conn = new DBConnection();
  }

  /**
   * gibt alle Zeilen der Tabelle book zurueck
   *
   * @return String Ergebnis
   */
  protected String eintragAnzeigenAlle() {
    String query = "SELECT * FROM book ORDER BY book_id;";
    ResultSet rs = conn.executeSelectQuery(query);
    ArrayList<String> anzahlSpalten = new ArrayList<String>();
    anzahlSpalten.addAll(Arrays.asList("book_id", "title", "subtitle", "category", "price"));
    String back = this.ergebnisToString(anzahlSpalten, rs);
    this.closeResult(rs);
    return back;
  }

  /**
   * Einen Neuen Datensatz eingeben
   *
   * @param title    Buchtitel
   * @param subtitle Untertitel
   * @param category Kategorie
   * @param price    Preis
   * @return int Anzahl der veraenderten Zeilen
   * @throws Exception wenn Kategorie nicht existiert
   */
  protected int eintragNeu(String title, String subtitle, int category, double price) throws Exception {
    //Anzahl der Kategorien (Abhaengigkeit)
    String abfrage = "SELECT count(*) FROM category;";
    int anzahl = conn.executeCountQuery(abfrage);
    boolean ok = false;
    do {
      if (!(category > anzahl) && !(category < 1)) throw new Exception("Die Kategorie muss existieren");
    } while (!ok);
    //Ende Eingabe der Werte
    String query = "INSERT INTO book (title,subtitle,category,price) VALUES (\'" + title + "\',\'" + subtitle + "\'," + category + "," + price + ");";
    System.out.println("Buch wird nun eingefuegt");
    int erg = conn.executeUpdateQuery(query);
    if (erg > 0) return erg;
    else return 0;
  }

  /**
   * loescht die Zeile mit dem genannten Index in der Tabelle book
   *
   * @return int Anzahl der veraenderten Zeilen
   * @throws Exception wenn book_id nicht vorhanden
   */
  protected int eintragLoeschen(int book_id) throws Exception {
    String abfrage = "SELECT book_id FROM book;";
    ResultSet result = conn.executeSelectQuery(abfrage);
    ArrayList<Integer> book_ids = new ArrayList<>();
    try {
      while (result.next()) {
        book_ids.add(result.getInt(1));
      }
    } catch (SQLException e) {
      System.out.println("Ein Fehler bei der Ergebnisabfrage ist aufgetreten");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
      return 0;
    }
    if (book_ids.contains(book_id)) {
      String query = "DELETE FROM book WHERE book_id=" + book_id + ";";
      int erg = conn.executeUpdateQuery(query);
      if (erg > 0) {
        this.closeResult(result);
        return erg;
      } else {
        this.closeResult(result);
        return 0;
      }
    } else {
      this.closeResult(result);
      throw new Exception("Bitte nur vorhandene Buecher loeschen.");
    }
  }

  /**
   * Fuellt das ResultSet in Navigation mit dem Ergebnis des Querys
   *
   * @param nav
   * @return gibt die Anzahl der Zeilen im ResultSet
   */
  protected int fillRSInNAvigation(Navigation nav) {
    nav.setRs(conn.executeSelectQuery(nav.getQuery()));
    try {
      nav.getRs().last();
      int zeilen = nav.getRs().getRow();
      nav.getRs().first();
      return zeilen;
    } catch (SQLException e) {
      System.out.println("Ein Fehler ist aufgetreten");
      return 0;
    }
  }

  /**
   * Gibt die Zeile(maker) im nav-Resultset als String zurueck
   *
   * @param nav Navigation
   * @return Ergebniszeile
   */
  protected String getNavRow(Navigation nav) {
    ArrayList<String> anzahlSpalten = new ArrayList<>();
    anzahlSpalten.addAll(Arrays.asList("book_id", "title", "subtitle", "category", "price"));
    String back = "";
    try {
      nav.getRs().first();
      for (int i = 1; i < nav.getMarker(); i++) {
        nav.getRs().next();
      }
      for (int i = 1; i <= anzahlSpalten.size(); i++) {
        back = back + nav.getRs().getString(i) + "\t";
      }
      return back;
    } catch (SQLException e) {
      System.out.println("Es ist Fehler aufgetreten");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
      return null;
    }
  }

  /**
   * ruft die genannten Spalten der Tabelle book auf
   *
   * @param erg Boolean Array welche der Spalten ausgewaehlt werden sollen
   * @return String Ergebnis
   */
  protected String eintragAnzeigenAuswahl(boolean[] erg) {
    ArrayList<String> anzahlSpalten = new ArrayList<>();
    String back = "";
    if (erg[0]) anzahlSpalten.add("book_id");
    if (erg[1]) anzahlSpalten.add("title");
    if (erg[2]) anzahlSpalten.add("subtitle");
    if (erg[3]) anzahlSpalten.add("category");
    if (erg[4]) anzahlSpalten.add("price");
    String auswahl = listeToString(anzahlSpalten);
    back = back + auswahl + System.lineSeparator();
    String query = "SELECT " + auswahl + " FROM book;";
    ResultSet rs = conn.executeSelectQuery(query);
    back = back + this.ergebnisToString(anzahlSpalten, rs);
    this.closeResult(rs);
    return back;
  }

  /**
   * wandelt eine ArrayListe in einen String um
   * (einzelne Elemente mit Komma getrennt)
   *
   * @param liste ArrayListe
   * @return rueckgabe der liste als String
   */
  private String listeToString(ArrayList<String> liste) {
    String ausgabe = "";
    for (int i = 0; i <= liste.size(); i++) {
      if (i < liste.size() - 1) {
        ausgabe = ausgabe + liste.get(i) + ", ";
      } else if (i == liste.size() - 1) {
        ausgabe = ausgabe + liste.get(i);
      }
    }
    return ausgabe;
  }

  /**
   * Gibt die Ergebnisse einer Abfrage auf die Konsole aus
   *
   * @param anzahlSpalten Liste mit den Spaltennamen
   * @param rs            SQL Query Abfrage
   */
  private String ergebnisToString(ArrayList<String> anzahlSpalten, ResultSet rs) {
    String back = "";
    try {
      while (rs.next()) {
        for (int i = 1; i <= anzahlSpalten.size(); i++) {
          back = back + rs.getString(i) + "\t";
        }
        back = back + System.lineSeparator();
      }
    } catch (SQLException e) {
      System.out.println("Ergebnisfehler nach der Abfrage ist aufgetreten:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    } catch (NullPointerException s) {
      System.out.println("Es ist ein Fehler aufgetreten:");
      System.out.println(s.getMessage() + System.lineSeparator() + s.getStackTrace());
    }
    return back;
  }

  /**
   * schliesst das Ergebnis
   *
   * @param result zu schliessendes Ergebnis
   */
  private void closeResult(ResultSet result) {
    try {
      result.close();
    } catch (SQLException e) {
      System.out.println("Das ResultSet konnte nicht geschlossen werden.");
    }
  }

  /**
   * schliesst die Verbindung zur DB
   */
  public void close() {
    conn.closeConnection();
  }
}
