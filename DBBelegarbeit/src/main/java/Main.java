import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programm zur Belegarbeit
 * Das Programm verbindet sich mit einer Postgres DB
 * und fuehrt dort verschiedene Operationen durch
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 21.04.2017
 */
public class Main {

  /**
   * Hauptprogramm ruft menue und Unterprogramme auf
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Belegarbeit (Tabelle :");
    boolean ende = false;
    boolean correct = false;
    int wahl = 0;
    do {
      menueAufrufen();
      do {
        wahl = eingabeInt("Bitte waehlen:");
        if ((wahl < 1) || (wahl > 6)) {
          System.out.println("Bitte eine gueltige Zahl eingeben");
        } else {
          correct = true;
        }
      } while (!correct);
      switch (wahl) {
        case 1:
          allesAusgeben();
          break;
        case 2:
          auswahlNeu();
          break;
        case 3:
          auswahlLoeschen();
          break;
        case 4:
          navigieren();
          break;
        case 5:
          auswahlAnzeigen();
          break;
        case 6:
          ende = true;
          System.out.println("Das Programm wird beendet");
        default:
          System.out.println("Bitte gueltige Zahl eingeben");
          break;
      }
      System.out.println("");
    } while (!ende);
  }

  /**
   * ruft das Menue auf und gibt es in der Konsole aus
   */
  private static void menueAufrufen() {
    System.out.println("Bitte waehlen:");
    System.out.println("1: Alles ausgeben");
    System.out.println("2: Eingabe eines Datensatzes");
    System.out.println("3: Loeschen eines Datensatzes");
    System.out.println("4: Navigieren durch die Datensaetze");
    System.out.println("5: Auswaehlen der anzuzeigenden Spalten");
    System.out.println("6: Programm beenden");
  }

  /**
   * gibt alle Zeilen der Tabelle book aus
   */
  private static void allesAusgeben() {
    System.out.println("Alles ausgeben");
    String query = "SELECT * FROM book ORDER BY book_id;";
    ResultSet rs = executeSelectQuery(query);
    ArrayList<String> anzahlSpalten = new ArrayList<String>();
    anzahlSpalten.addAll(Arrays.asList("book_id", "title", "subtitle", "category", "price"));
    ausgabeErgebnis(anzahlSpalten, rs);
  }

  /**
   * Einen Neuen Datensatz eingeben
   */
  private static void auswahlNeu() {
    System.out.println("Neuen Datensatz eingeben");
    //Eingabe der Werte
    String title = eingabeString("Bitte title eingeben");
    String subtitle = eingabeString("Bitte subtitle eingeben");
    //Anzahl der Kategorien (Abhaengigkeit)
    String abfrage = "SELECT count(*) FROM category;";
    int anzahl = executeCountQuery(abfrage);
    boolean ok = false;
    int category;
    do {
      category = eingabeInt("Bitte vorhandene category eingeben");
      if (!(category > anzahl) && !(category < 0)) ok = true;
    } while (!ok);
    double price = eingabeDouble("Bitte price festlegen");
    //Ende Eingabe der Werte
    String query = "INSERT INTO book (title,subtitle,category,price) VALUES (\'" + title + "\',\'" + subtitle + "\'," + category + "," + price + ");";
    System.out.println("Ihr Buch wird nun eingefuegt");
    int erg = executeUpdateQuery(query);
    if (erg > 0) System.out.println("Der Insert war erfolgreich und " + erg + " Zeilen wurden geaendert");
    else System.out.println("Der Insert war nicht erfolgreich");
  }

  /**
   * loescht die Zeile mit dem genannten Index in der Tabelle book
   */
  private static void auswahlLoeschen() {
    System.out.println("Auswahl loeschen");
    String abfrage = "SELECT book_id FROM book;";
    ResultSet result = executeSelectQuery(abfrage);
    ArrayList<Integer> book_ids = new ArrayList<>();
    try {
      while (result.next()) {
        book_ids.add(result.getInt(1));
      }
    } catch (SQLException e) {
      System.out.println("Ein Fehler bei der Ergebnisabfrage ist aufgetreten");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    }
    boolean correct = false;
    int wahl = 0;
    do {
      wahl = eingabeInt("Bitte geben Sie eine book_id ein welche geloescht werden soll");
      if (wahl < 0) {
        System.out.println("Bitte nur vorhandene Buecher loeschen");
      } else {
        boolean contains = false;
        for (int element : book_ids) {
          if (element == wahl) contains = true;
        }
        if (contains) correct = true;
      }
    } while (!correct);
    String query = "DELETE FROM book WHERE book_id=" + wahl + ";";
    System.out.println("Ihr Buch wird nun geloescht");
    int erg = executeUpdateQuery(query);
    if (erg > 0) System.out.println("Das Loeschen war erfolgreich und " + erg + " Zeilen wurden geaendert");
    else System.out.println("Das Loeschen war nicht erfolgreich");
  }

  /**
   * ruft alle Zeilen einzeln in der Tabelle book auf
   * mit den Pfeiltasten laesst sich hier navigieren
   */
  private static void navigieren() {
    System.out.println("Durch alles navigieren");
    String query = "SELECT * FROM book ORDER BY title;";
    ResultSet rs = executeSelectQuery(query);
    ArrayList<String> anzahlSpalten = new ArrayList<>();
    anzahlSpalten.addAll(Arrays.asList("book_id", "title", "subtitle", "category", "price"));
    try { //SQLException abfangen
      char eingabe = '0';
      rs.last();
      int anzahlZeilen = rs.getRow();
      rs.first();
      System.out.println("Navigation:" + System.lineSeparator() + "n:next   p:previos   e:exit");
      do {
        System.out.print("\r");
        for (int i = 1; i <= anzahlSpalten.size(); i++) {
          System.out.print(rs.getString(i) + "\t");
        }
        eingabe = eingabeChar();
        switch (eingabe){
          case 'n':
            if (rs.getRow() == anzahlZeilen){
              rs.first();
            }else{
              rs.next();
            }
            break;
          case 'p':
            if (rs.getRow() == 1){
              rs.last();
            }else{
              rs.previous();
            }
            break;
          case 'e':
            System.out.println("Das Unterprogramm wird beendet");
            break;
          default:
            break;
        }
      } while (eingabe != 'e');
    }catch (SQLException e){
      System.out.println("Es ist Fehler aufgetreten");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    }
  }

  /**
   * ruft die genannten Spalten der Tabelle book auf
   */
  private static void auswahlAnzeigen() {
    final int spaltenInBook = 5;
    System.out.println("Auswahl ausgeben");
    boolean ok = false;
    int wahl = 0;
    boolean[] erg = new boolean[spaltenInBook];
    Arrays.fill(erg, Boolean.FALSE);
    //bis exit gewuenscht oder alle spalten ausgewaehlt
    do {
      String msg = "Geben Sie bitte die Spalten an die Sie angezeigt bekommen wollen:  0:fertig,  1:book_id,  2:title,  3:subtitle,  4:category,  5:price";
      //bis ok
      do {
        wahl = eingabeInt(msg);
        if ((wahl < 0) || (wahl > spaltenInBook)) {
          System.out.println("Bitte eine der angegebenen Zahlen eingeben");
        } else {
          switch (wahl) {
            case 0:
              System.out.println("fertig");
              break;
            case 1:
              erg[0] = true;
              break;
            case 2:
              erg[1] = true;
              break;
            case 3:
              erg[2] = true;
              break;
            case 4:
              erg[3] = true;
              break;
            case 5:
              erg[4] = true;
              break;
            default:
              System.out.println("Bitte eine der angegebenen Moeglichkeiten waehlen");
              continue;
          }
          ok = true;
        }
      } while (!ok);
    } while (wahl != 0);
    ArrayList<String> anzahlSpalten = new ArrayList<String>();
    if (erg[0]) anzahlSpalten.add("book_id");
    if (erg[1]) anzahlSpalten.add("title");
    if (erg[2]) anzahlSpalten.add("subtitle");
    if (erg[3]) anzahlSpalten.add("category");
    if (erg[4]) anzahlSpalten.add("price");
    String auswahl = listeToString(anzahlSpalten);
    System.out.println(auswahl);
    String query = "SELECT " + auswahl + " FROM book;";
    ResultSet rs = executeSelectQuery(query);
    ausgabeErgebnis(anzahlSpalten, rs);
  }

  /**
   * fuehrt ein Update aus
   *
   * @param query Query
   * @return Anzahl der geanderten Zeilen
   */
  private static int executeUpdateQuery(String query) {
    Statement stmt = DBConnection.createConnection();
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
  private static ResultSet executeSelectQuery(String query) {
    Statement stmt = DBConnection.createConnection();
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
  private static int executeCountQuery(String query) {
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
   * neuen String eingeben
   *
   * @param msg Anzuzeigende Nachricht was eingegeben werden soll
   * @return eingabe des users
   */
  private static String eingabeString(String msg) {
    boolean ok = false;
    String eingabe = "";
    Scanner scan = new Scanner(System.in);
    do {
      System.out.println(msg);
      try {
        eingabe = scan.next();
        ok = true;
      } catch (InputMismatchException e) {
        System.out.println("Bitte gueltige Eingabe taetigen");
      } finally {
        scan.nextLine();
      }
    } while (!ok);
    return eingabe;
  }

  /**
   * neuen char eingeben
   *
   * @return eingabe des users
   */
  private static char eingabeChar() {
    boolean ok = false;
    char eingabe = '0';
    Scanner scan = new Scanner(System.in);
    do {
      try {
        eingabe = scan.next().charAt(0);
        ok = true;
      } catch (InputMismatchException e) {
        //Nothing to do
      } finally {
        scan.nextLine();
        System.out.print("\r");
      }
    } while (!ok);
    return eingabe;
  }

  /**
   * neue Zahl eingeben
   *
   * @param msg Anzuzeigende Nachricht was eingegeben werden soll
   * @return eingabe des users
   */
  private static int eingabeInt(String msg) {
    boolean ok = false;
    int eingabe = 0;
    Scanner scan = new Scanner(System.in);
    do {
      System.out.println(msg);
      try {
        eingabe = scan.nextInt();
        ok = true;
      } catch (InputMismatchException e) {
        System.out.println("Bitte gueltige Eingabe taetigen");
        continue;
      } finally {
        scan.nextLine();
      }
    } while (!ok);
    return eingabe;
  }

  /**
   * neue Flieskommazahl eingeben
   *
   * @param msg Anzuzeigende Nachricht was eingegeben werden soll
   * @return eingabe des users
   */
  private static double eingabeDouble(String msg) {
    boolean ok = false;
    double eingabe = 0;
    Scanner scan = new Scanner(System.in);
    do {
      System.out.println(msg);
      try {
        eingabe = scan.nextDouble();
        ok = true;
      } catch (InputMismatchException e) {
        System.out.println("Bitte gueltige Eingabe taetigen");
        continue;
      } finally {
        scan.nextLine();
      }
    } while (!ok);
    return eingabe;
  }

  /**
   * wandelt eine ArrayListe in einen String um
   * (einzelne Elemente mit Komma getrennt)
   *
   * @param liste ArrayListe
   * @return rueckgabe der liste als String
   */
  private static String listeToString(ArrayList<String> liste) {
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
  private static void ausgabeErgebnis(ArrayList<String> anzahlSpalten, ResultSet rs) {
    try {
      while (rs.next()) {
        for (int i = 1; i <= anzahlSpalten.size(); i++) {
          System.out.print(rs.getString(i) + "\t");
        }
        System.out.print(System.lineSeparator());
      }
    } catch (SQLException e) {
      System.out.println("Ergebnisfehler nach der Abfrage ist aufgetreten:");
      System.out.println(e.getMessage() + System.lineSeparator() + e.getSQLState());
    } catch (NullPointerException s) {
      System.out.println("Es ist ein Fehler aufgetreten:");
      System.out.println(s.getMessage() + System.lineSeparator() + s.getStackTrace());
    }
  }
}
