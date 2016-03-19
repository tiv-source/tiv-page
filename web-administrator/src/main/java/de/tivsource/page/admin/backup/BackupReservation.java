package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.entity.reservation.Reservation;

public class BackupReservation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupReservation.class);

	private static ReservationDaoLocal reservationDaoLocal;

	private static final int max = 10000;

	private List<File> backupFiles = new ArrayList<File>();

    public static void setReservationDaoLocal(ReservationDaoLocal reservationDaoLocal) {
        BackupReservation.reservationDaoLocal = reservationDaoLocal;
    }

    public List<File> getBackupFiles() throws IOException {
	    LOGGER.info("getBackupFiles() aufgerufen.");

		// Datei Kram
		File file = new File("/tmp/reservation.csv");
    	FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    	// Format Definition 
    	bufferedWriter.write("[Format Definition] => uuid|gender|firstname|lastname|email|telephone|quantity|time|wishes|event|confirmed|created|createdAddress|confirmedAddress|confirmedDate|confirmedBy|modified|modifiedAddress|modifiedBy|");

    	Iterator<Reservation> reservationIterator = reservationDaoLocal.findAll(0, max).iterator();
    	while(reservationIterator.hasNext()) {
    	    Reservation next = reservationIterator.next();
    		bufferedWriter.write("\n");
    		bufferedWriter.write(convertToCsvLine(next));
    		generateContentFile(next);
    	}

    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);

		return backupFiles;
	}

	private String convertToCsvLine(Reservation next) {
	    LOGGER.info("convertToCsvLine(Page next) aufgerufen.");

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// uuid|gender|firstname|lastname|email|telephone|quantity|time|wishes|event|confirmed|created|createdAddress|confirmedAddress|confirmedDate|confirmedBy|modified|modifiedAddress|modifiedBy|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

        nextString.append(next.getGender().toString());
        nextString.append("|");

		nextString.append(next.getFirstname());
		nextString.append("|");

		nextString.append(next.getLastname());
		nextString.append("|");

		nextString.append(next.getEmail());
		nextString.append("|");

        nextString.append(next.getTelephone());
        nextString.append("|");

        nextString.append(next.getQuantity().toString());
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getTime()));
        nextString.append("|");

        nextString.append("reservation_" + next.getUuid() + ".txt");
		nextString.append("|");

        nextString.append(next.getEvent().getUuid());
        nextString.append("|");

        nextString.append(next.getConfirmed().toString());
        nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getCreated()));
		nextString.append("|");

		nextString.append(next.getCreatedAddress());
		nextString.append("|");

		nextString.append(next.getConfirmedAddress() != null ? next.getConfirmedAddress() : "");
		nextString.append("|");

		nextString.append(next.getConfirmedDate() != null ? simpleDateFormat.format(next.getConfirmedDate()) : "");
		nextString.append("|");

		nextString.append(next.getConfirmedBy() != null ? next.getConfirmedBy() : "");
		nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getModified()));
		nextString.append("|");

        nextString.append(next.getModifiedAddress());
        nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");

		return nextString.toString();
	}

	private void generateContentFile(Reservation message) throws IOException {
		File file = new File("/tmp/reservation_" + message.getUuid() + ".txt");
		FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    	bufferedWriter.write(message.getWishes());
    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);
	}

}// Ende class
