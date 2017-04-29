import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Gibt die benoetigten Eingaben zurueck
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 28.04.2017
 */
public class Eingaben {
  /**
   * neuen String eingeben
   *
   * @param msg Anzuzeigende Nachricht was eingegeben werden soll
   * @return eingabe des users
   */
  public static String eingabeString(String msg) {
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
  public static char eingabeChar() {
    boolean ok = false;
    char eingabe = '0';
    Scanner scan = new Scanner(System.in);
    do {
      try {
        eingabe = scan.next().charAt(0);
        System.out.print("\r");
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
  public static int eingabeInt(String msg) {
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
  public static double eingabeDouble(String msg) {
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
}
