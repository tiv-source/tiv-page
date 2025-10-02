/**
 * 
 */
package de.tivsource.page.dao.mail;

import java.util.List;

import de.tivsource.page.entity.mail.MailAddressLock;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface MailAddressLockDaoLocal {

    /**
     * Methode zum verändern eines Objektes der Klasse MailAddressLock.
     * @param mailAddressLock - MailAddressLock Objekt das verändert werden soll
     */
    public void merge(MailAddressLock mailAddressLock);

    /**
     * Methode zum löschen eines Objektes der Klasse MailAddressLock.
     * @param mailAddressLock - zu löschendes MailAddressLock Objekt
     */
    public void delete(MailAddressLock mailAddressLock);

    /**
     * Metholde um zu überprüfen ob zu der angegebenen Mail Adresse ein Lock existiert.
     * 
     * @param mailAddress - Die Mail Adresse die überprüft werden soll.
     * @return Boolean - true wenn es ein Lock gibt sonst false.
     */
    public Boolean existMailAddressLock(String mailAddress);

    public MailAddressLock findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von MailAddressLock Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzahl der zu ladenen MailAddressLock Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<MailAddressLock> - Liste von MailAddressLock Objekten
     */
    public List<MailAddressLock> findAll(Integer start, Integer max);

    public List<MailAddressLock> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller MailAddressLock Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der MailAddressLock Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
