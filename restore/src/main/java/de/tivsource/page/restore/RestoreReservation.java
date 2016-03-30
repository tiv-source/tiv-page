/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.enumeration.Origin;

/**
 * @author Marc Michele
 *
 */
public class RestoreReservation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(RestoreReservation.class);
    
    private ReservationDaoLocal reservationDaoLocal;

    private EventDaoLocal eventDaoLocal;

    private Map<String, InputStream> streams;

    public RestoreReservation(ReservationDaoLocal reservationDaoLocal,
            EventDaoLocal eventDaoLocal, Map<String, InputStream> streams) {
        super();
        this.reservationDaoLocal = reservationDaoLocal;
        this.eventDaoLocal = eventDaoLocal;
        this.streams = streams;
    }

    /**
     * Generiert aus der Datei die entsprechenden News-Einträge.
     * @throws ParseException 
     */
    public void generate() {
        LOGGER.info("generate() aufgerufen.");
        cleanup();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(streams.get("reservation.csv")));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Reservation reservation = convert(line);
                    reservationDaoLocal.merge(reservation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Ende generate()
    
    private Reservation convert(String line) {
        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // uuid|gender|firstname|lastname|email|telephone|quantity|time|wishes|event|
        // confirmed|created|createdAddress|confirmedAddress|confirmedDate|confirmedBy|
        // modified|modifiedAddress|modifiedBy|

        Reservation reservation = new Reservation();

        reservation.setUuid(items[0]);
        reservation.setGender(items[1].equals("true") ? true : false);
        reservation.setFirstname(items[2]);
        reservation.setLastname(items[3]);
        reservation.setEmail(items[4]);
        reservation.setTelephone(items[5]);
        reservation.setQuantity(Integer.parseInt(items[6]));
        reservation.setTime(convertDateString(items[7]));
        reservation.setWishes(createContent(items[8]));
        reservation.setEvent(eventDaoLocal.findByUuid(items[9]));
        reservation.setConfirmed(items[10].equals("true") ? true : false);
        reservation.setCreated(convertDateString(items[11]));
        reservation.setCreatedAddress(items[12]);
        
        reservation.setConfirmedAddress(items[13]);
        reservation.setConfirmedDate(items[14].length() > 0 ? convertDateString(items[14]) : null);
        reservation.setConfirmedBy(items[15]);

        reservation.setModified(convertDateString(items[16]));
        reservation.setModifiedAddress(items[17]);
        reservation.setModifiedBy(items[18]);

        // TODO: Restore from file
        reservation.setOrigin(Origin.WEBSITE);

        return reservation;
    }

    private String createContent(String filename) {
        BufferedReader input = new BufferedReader(new InputStreamReader(streams.get(filename)));
        StringBuffer contentString = new StringBuffer();
        try {
            String lineInput = null;
            while ((lineInput = input.readLine()) != null) {
                contentString.append(lineInput);
                contentString.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentString.toString();
    }

    /**
     * Methode zum Konvertieren eines Strings des Formates "1970-12-01 23:59:59" in ein Date-Object. 
     * @param dateString
     * @return
     */
    private Date convertDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }// Ende convertDateString(String dateString)

    private void cleanup() {
        if(reservationDaoLocal.countAll() > 0) {
            Iterator<Reservation> reservationIterator = reservationDaoLocal.findAll(0, reservationDaoLocal.countAll()).iterator();
            while(reservationIterator.hasNext()) {
                Reservation next = reservationIterator.next();
                reservationDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()
    
}// Ende class
