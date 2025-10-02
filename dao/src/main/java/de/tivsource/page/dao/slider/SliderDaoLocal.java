/**
 * 
 */
package de.tivsource.page.dao.slider;

import java.util.List;

import de.tivsource.page.entity.slider.Slider;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface SliderDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Slider.
     * @param slider - Slider Objekt das gespeichert werden soll
     */
    public void save(Slider slider);

    /**
     * Methode zum verändern eines Objektes der Klasse Slider.
     * @param slider - Slider Objekt das verändert werden soll
     */
    public void merge(Slider slider);

    /**
     * Methode zum löschen eines Objektes der Klasse Slider.
     * @param slider - zu löschendes Slider Objekt
     */
    public void delete(Slider slider);

    public Slider findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Slider Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Slider Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Slider> - Liste von Slider Objekten
     */
    public List<Slider> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Slider Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Slider> findAll(Integer start, Integer max, String field, String order);

    public List<Slider> findAllVisible(Integer start, Integer max, String page);

    /**
     * Methode die die Anzahl aller Slider Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Slider Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible(String page);

}// Ende interface
