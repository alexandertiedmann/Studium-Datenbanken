import java.sql.SQLException;
import java.util.Arrays;

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
    Verarbeitung ver = new Verarbeitung();
    System.out.println("Belegarbeit (Tabelle :");
    boolean ende = false;
    boolean correct = false;
    int wahl = 0;
    do {
      menueAufrufen();
      do {
        wahl = Eingaben.eingabeInt("Bitte waehlen:");
        if ((wahl < 1) || (wahl > 6)) {
          System.out.println("Bitte eine gueltige Zahl eingeben");
        } else {
          correct = true;
        }
      } while (!correct);
      switch (wahl) {
        case 1:
          System.out.println("Alles ausgeben");
          System.out.println(ver.eintragAnzeigenAlle());
          break;
        case 2:
          System.out.println("Neuen Datensatz eingeben");
          auswahlNeu(ver);
          break;
        case 3:
          System.out.println("Auswahl loeschen");
          auswahlLoeschen(ver);
          break;
        case 4:
          System.out.println("Durch alles navigieren");
          navigieren(ver);
          break;
        case 5:
          System.out.println("Auswahl ausgeben");
          auswahlAnzeigen(ver);
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
    ver.close();
  }

  /**
   * Eingabe einer neuen Zeile in die Tabelle book
   *
   * @param ver Verarbeitung der Daten
   */
  private static void auswahlNeu(Verarbeitung ver) {
    //Eingabe der Werte
    String title = Eingaben.eingabeString("Bitte title eingeben");
    String subtitle = Eingaben.eingabeString("Bitte subtitle eingeben");
    int category;
    double price = Eingaben.eingabeDouble("Bitte price festlegen");
    boolean fehler = false;
    do {
      try {
        category = Eingaben.eingabeInt("Bitte vorhandene category eingeben");
        if (ver.eintragNeu(title, subtitle, category, price) > 0) {
          fehler = false;
        }
      } catch (Exception e) {
        System.out.println("Die Kategorie muss existieren");
        fehler = true;
      }
    } while (fehler);
  }

  /**
   * Loeschen der Zeile aus der Tabelle book mit der einzugebenen ID
   *
   * @param ver Verarbeitung der Daten
   */
  private static void auswahlLoeschen(Verarbeitung ver) {
    boolean correct = false;
    int wahl = 0;
    int back = 0;
    do {
      wahl = Eingaben.eingabeInt("Bitte geben Sie eine book_id ein welche geloescht werden soll");
      if (wahl < 0) {
        System.out.println("Bitte nur vorhandene Buecher loeschen");
        correct = false;
      } else {
        try {
          back = ver.eintragLoeschen(wahl);
          if (back > 0) {
            System.out.println("Es wurden " + back + "Zeilen geloescht.");
            correct = true;
          }
        } catch (Exception e) {
          System.out.println("Bitte nur vorhandene Buecher loeschen");
          correct = false;
        }
      }
    } while (!correct);
  }

  /**
   * Anzeigen der ausgewaehlten Spalten der Tabelle book
   *
   * @param ver Verarbeitung der Daten
   */
  private static void auswahlAnzeigen(Verarbeitung ver) {
    final int spaltenInBook = 5;
    boolean ok = false;
    int wahl = 0;
    boolean[] erg = new boolean[spaltenInBook];
    Arrays.fill(erg, Boolean.FALSE); //initiale Befuellung auf false
    //bis exit gewuenscht oder alle spalten ausgewaehlt
    do {
      String msg = "Geben Sie bitte die Spalten an die Sie angezeigt bekommen wollen:  0:fertig,  1:book_id,  2:title,  3:subtitle,  4:category,  5:price";
      //bis ok
      do {
        wahl = Eingaben.eingabeInt(msg);
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
    System.out.println(ver.eintragAnzeigenAuswahl(erg));
  }

  /**
   * navigieren mit 'p' und 'n' durch alle Zeilen der Tabelle book
   *
   * @param ver Verarbeitung der Daten
   */
  private static void navigieren(Verarbeitung ver) {
    Navigation nav = new Navigation("SELECT * FROM book ORDER BY book_id;");
    int anzahlZeilen = ver.fillRSInNAvigation(nav);
    char eingabe;
    System.out.println("Navigation:" + System.lineSeparator() + "n:next   p:previos   e:exit");
    try {
      do {
        System.out.print("\r");
        System.out.print(ver.getNavRow(nav));
        eingabe = Eingaben.eingabeChar();
        switch (eingabe) {
          case 'n':
            if (nav.getRs().getRow() == anzahlZeilen) {
              nav.setMarker(1);
            } else {
              nav.setMarkerHoch();
            }
            break;
          case 'p':
            if (nav.getRs().getRow() == 1) {
              nav.setMarker(anzahlZeilen);
            } else {
              nav.setMarkerRunter();
            }
            break;
          case 'e':
            System.out.println("Ende");
            break;
          default:
            break;
        }
      } while (eingabe != 'e');
    }catch (SQLException e){
      System.out.println("Es ist ein Fehler aufgetreten");
    }
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
}
