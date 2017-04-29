import java.sql.ResultSet;

/**
 * Klasse zum navigieren
 *
 * @author Alexander Tiedmann (s0556127)
 * @version 1.0
 * @since 28.04.2017
 */
public class Navigation {
  private ResultSet rs;
  private int marker;
  private String query;

  /**
   * NAvigation mit Query
   *
   * @param query
   */
  public Navigation(String query) {
    this.marker = 1;
    this.rs = null;
    this.query = query;
  }

  /**
   * Navigation mit vorhandenem ResultSet
   *
   * @param rs
   */
  public Navigation(ResultSet rs) {
    this.marker = 1;
    this.rs = rs;
  }

  /**
   * setzt das ResultSet
   *
   * @param rs neues ResultSet
   */
  public void setRs(ResultSet rs) {
    this.rs = rs;
  }

  /**
   * gibt das ResultSet zurueck
   *
   * @return Resultset
   */
  public ResultSet getRs() {
    return this.rs;
  }

  /**
   * erhoeht den Marker um 1
   */
  public void setMarkerHoch() {
    this.marker++;
  }

  /**
   * setzt den Marker um 1 runter
   */
  public void setMarkerRunter() {
    this.marker--;
  }

  /**
   * gibt des Marker zurueck
   *
   * @return
   */
  public int getMarker() {
    return marker;
  }

  /**
   * setzt den Marker
   *
   * @param zahl neuer Marker
   */
  public void setMarker(int zahl) {
    this.marker = zahl;
  }

  /**
   * Gibt den Query zurueck
   */
  public String getQuery() {
    return query;
  }
}
