package de.tivsource.page.dao.captcha;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.common.captcha.Captcha;

/**
 * 
 * @author Marc Michele
 *
 */
@Local
public interface CaptchaDaoLocal {

    /**
     * Methode zum updaten eines Objektes der Klasse Captcha.
     *
     * @param captcha - Captcha Objekt das verändert werden soll
     */
    public void merge(Captcha captcha);

    /**
     * Methode zum löschen eines Objektes der Klasse Captcha.
     *
     * @param captcha - zu löschendes Captcha Objekt
     */
    public void delete(Captcha captcha);

    /**
     * Methode um ein Objekt der Klasse Captcha anhand seiner UUID zu laden.
     *
     * @param uuid - UUID des Captcha Objektes das geladen werden soll.
     * @return Captcha - Captcha-Objekt das die angegebene UUID besitzt.
     */
    public Captcha findByUuid(String uuid);

    /**
     * Gibt alle Captcha-Objekte zurück die es gibt.
     *
     * @param start - Startwert ab dem die Objekte ausgegeben werden sollen.
     * @param max - Anzahl der Objekte die zurückgegeben werden sollen.
     * @return List<Captcha> - Liste mit Captcha-Objekten.
     */
    public List<Captcha> findAll(Integer start, Integer max);

    /**
     * Methode die zum laden einer sortierten Liste von Captcha-Objekten dient.
     * 
     * @param start - Startwert ab dem die Objekte ausgegeben werden sollen.
     * @param max - Anzahl der Objekte die zurückgegeben werden sollen.
     * @param field - Feld nachdem sortiert werden soll
     * @param order - Die Richtung nach der sortiert werden soll.
     * @return List<Captcha> - Liste mit den sortierten Captcha-Objekten.
     */
    public List<Captcha> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Captcha Objekte liefert.
     *
     * @return Integer - Anzahl der Captcha Objekte in der Datenbank
     */
    public Integer countAll();

    /**
     * Methode die eine zufälliges Captach Objekt zurückliefert.
     *
     * @return Captcha - zufällig ausgewähltes Captcha Objekt
     */
    public Captcha random();

}// Ende interface
