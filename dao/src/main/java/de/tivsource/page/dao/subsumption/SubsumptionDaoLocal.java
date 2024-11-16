/**
 * 
 */
package de.tivsource.page.dao.subsumption;

import java.util.List;

import de.tivsource.page.entity.subsumption.Subsumption;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface SubsumptionDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse Subsumption.
     * @param subsumption - Subsumption Objekt das verändert werden soll
     */
    public void merge(Subsumption subsumption);

    /**
     * Methode zum löschen eines Objektes der Klasse Subsumption.
     * @param subsumption - zu löschendes Subsumption Objekt
     */
    public void delete(Subsumption subsumption);

    /**
     * Metholde um zu überprüfen ob der angegebene UrlName zu einer Einordnung (Subsumption) gehört.
     * 
     * @param urlName - Der UrlName der überprüft werden soll.
     * @return Boolean - true wenn es eine Einordnung (Subsumption) ist.
     */
    public Boolean isSubsumptionUrl(String urlName);

    /**
     * Methode um zu Testen ob die Einordnung (Subsumption) in einem MenüEintrag verwendet wird. 
     * @param uuid
     * @return
     */
    public Boolean hasMenuEntry(String uuid);

    /**
     * Methode zum laden eines Objektes der Klasse Subsumption anhand der
     * Benutzernames.
     * 
     * @param subsumptionname - Benutzername des Objektes das geladen werden soll.
     * @return Subsumption - Objekt das den angegebenen Benutzernamen besitzt.
     */
    public Subsumption findByTechnical(String technical);

    public Subsumption findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Subsumption Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Subsumption Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Subsumption> - Liste von Subsumption Objekten
     */
    public List<Subsumption> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Subsumption Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Subsumption> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Subsumption Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Subsumption Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
