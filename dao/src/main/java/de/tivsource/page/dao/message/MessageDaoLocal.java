/**
 * 
 */
package de.tivsource.page.dao.message;

import java.util.List;

import javax.ejb.Local;

import de.tivsource.page.entity.message.Message;

/**
 * @author Marc Michele
 *
 */
@Local
public interface MessageDaoLocal {

    /**
     * Methode zum speichern eines Objektes der Klasse Message.
     * @param message - Message Objekt das gespeichert werden soll
     */
    public void save(Message message);

    /**
     * Methode zum verändern eines Objektes der Klasse Message.
     * @param message - Message Objekt das verändert werden soll
     */
    public void merge(Message message);

    /**
     * Methode zum löschen eines Objektes der Klasse Message.
     * @param message - zu löschendes Message Objekt
     */
    public void delete(Message message);

    /**
     * Methode um ein Objekt der Klasse Message anhand seine UUID aus der
     * Datenbank zu laden.
     * 
     * @param uuid - UUID des Message Objektes das geladen werden soll
     * @return Message - Message Objekt das die angegebene UUID besitzt
     */
    public Message findByUuid(String uuid);

    /**
     * Methode zum laden einer Liste von Message Objekten, es muss dabei ein
     * Startwert angegeben werden und die Anzhal der zu ladenen Message Objekte.
     *
     * @param start - Startwert ab der die Liste beginnen soll
     * @param max - Maximale Anzahl an Objekten die die Liste enthalten soll
     * @return List<Message> - Liste von Message Objekten
     */
    public List<Message> findAll(Integer start, Integer max);

    public List<Message> findAll(Integer start, Integer max, String field, String order);

    /**
     * Methode die die Anzahl aller Message Objekte die sich in der Datenbank
     * befinden zurück liefert.
     *
     * @return Integer - Anzahl der Message Objekte die sich in der Datenbank befinden.
     */
    public Integer countAll();

}// Ende interface
