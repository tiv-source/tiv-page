/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import de.tivsource.page.entity.mail.MailAddress;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface MailAddressDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse MailAddress.
     * @param mailAddress - MailAddress Objekt das verändert werden soll
     */
    public void merge(MailAddress mailAddress);

    /**
     * Methode zum löschen eines Objektes der Klasse MailAddress.
     * @param mailAddress - zu löschendes MailAddress Objekt
     */
    public void delete(MailAddress mailAddress);

    /**
     * Metholde um zu überprüfen ob zu der angegebenen Mail Adresse im Verteiler vorhanden ist.
     * 
     * @param mailAddress - Die Mail Adresse die überprüft werden soll.
     * @return Boolean - true wenn es ein Lock gibt sonst false.
     */
    public Boolean mailAddressExist(String mailAddress);

    public MailAddress findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von MailAddress Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen MailAddress Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<MailAddress> - Liste von MailAddress Objekten
     */
    public List<MailAddress> findAll(Integer start, Integer max);

    public List<MailAddress> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller MailAddress Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der MailAddress Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
