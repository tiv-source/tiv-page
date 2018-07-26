/**
 * 
 */
package de.tivsource.page.dao.appointment;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.appointment.Appointment;

/**
 * @author Marc Michele
 *
 */
@Local
public interface AppointmentDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Appointment.
     * @param appointment - Appointment Objekt das gespeichert werden soll
     */
    public void save(Appointment appointment);

    /**
     * Methode zum verändern eines Objektes der Klasse Appointment.
     * @param appointment - Appointment Objekt das verändert werden soll
     */
    public void merge(Appointment appointment);

    /**
     * Methode zum löschen eines Objektes der Klasse Appointment.
     * @param appointment - zu löschendes Appointment Objekt
     */
    public void delete(Appointment appointment);

    /**
     * Metholde um zu überprüfen ob die angegebene UUIDe zu einem Termin (Appointment) gehört.
     * 
     * @param uuid - Die UUID die überprüft werden soll.
     * @return Boolean - true wenn es ein Termin (Appointment) ist.
     */
    public Boolean isAppointmentUuid(String uuid);
    
    /**
     * Methode zum laden eines Objektes der Klasse Appointment anhand der
     * Uuid.
     * 
     * @param uuid - UUID des Objektes das geladen werden soll.
     * @return Appointment - Objekt das die angegebene UUID besitzt.
     */
    public Appointment findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Appointment Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen Appointment Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Appointment> - Liste von Appointment Objekten
     */
    public List<Appointment> findAll(Integer start, Integer max);

    /**
     * Methode zu laden einer Liste von Appointment Objekten, es muss dabei ein
     * Startwert, die Anzahl der zu ladenen Objekte, das Feld nach dem sortiert
     * werden soll und die sortier Richtung angegeben werden.
     * 
     * @param start
     * @param max
     * @param field
     * @param order
     * @return
     */
    public List<Appointment> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode zu laden der aktuell sichtbaren Apointment Objekte, dabei werden
     * nur Objekte geladen die nicht vor dem aktuellen Datum liegen. Das heißt
     * Objekte die in der Vergangenheit liegen werden nicht angezeigt.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<Appointment> findAllVisible(Integer start, Integer max);

    /**
     * Methode zu laden der archivierten sichtbaren Apointment Objekte, dabei
     * werden nur Objekte geladen die nicht nach dem aktuellen Datum liegen. Das
     * heißt es werden nur Objekte die in der Vergangenheit liegen angezeigt.
     * 
     * @param start
     * @param max
     * @return
     */
    public List<Appointment> findAllArchiveVisible(Integer start, Integer max);

    /**
     * Methode die die Anzahl aller Appointment Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Appointment Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

    public Integer countAllVisible();

    public Integer countAllArchiveVisible();

}// Ende interface
