/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import de.tivsource.page.entity.mail.MailAddressLockRequest;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface MailAddressLockRequestDaoLocal {

    /**
     * Methode zum verändern/speichern eines Objektes der Klasse
     * MailAddressLockRequest.
     * 
     * @param mailAddressLockRequest - MailAddressLockRequest Objekt das
     *            gespeichert/verändert werden soll
     */
    public void merge(MailAddressLockRequest mailAddressLockRequest);

    /**
     * Methode zum löschen eines Objektes der Klasse MailAddressLockRequest.
     * @param mailAddressLockRequest - zu löschendes MailAddressLockRequest Objekt
     */
    public void delete(MailAddressLockRequest mailAddressLockRequest);

    /**
     * Methode zum laden eines Antrages anhand der UUID des Antrages.
     *
     * @param uuid - UUID des MailAddressLockRequest Objektes
     * @return MailAddressLockRequest - gesuchtes Objekt
     */
    public MailAddressLockRequest findByUuid(String uuid);

    /**
     * Methode zum überprüfen ob es für den angegebenen Hash einen Sperrantrag gibt.
     *
     * @param hash - Der zu überprüfende Hash
     * @return Boolean - true wenn es einen Sperrantrag gibt, false wenn nicht
     */
    public Boolean existsALockRequest(String hash);

    /**
     * Methode zum laden einer Liste von MailAddressLockRequest Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen MailAddressLockRequest Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<MailAddressLockRequest> - Liste von MailAddressLockRequest Objekten
     */
    public List<MailAddressLockRequest> findAll(Integer start, Integer max);

    public List<MailAddressLockRequest> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller MailAddressLockRequest Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der MailAddressLockRequest Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
