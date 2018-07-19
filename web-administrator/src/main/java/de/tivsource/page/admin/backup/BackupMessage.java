package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.entity.message.Message;

public class BackupMessage {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupMessage.class);

	private static MessageDaoLocal messageDaoLocal;

	private static final int max = 2500;

	private List<File> backupFiles = new ArrayList<File>();

	public static void setMessageDaoLocal(MessageDaoLocal messageDaoLocal) {
        BackupMessage.messageDaoLocal = messageDaoLocal;
    }

    public List<File> getBackupFiles() throws IOException {
	    LOGGER.info("getBackupFiles() aufgerufen.");

		// Datei Kram
		File file = new File("/tmp/message.csv");
    	FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    	// Format Definition 
    	bufferedWriter.write("[Format Definition] => uuid|gender|firstname|lastname|mail|telephone|fax|content|privacy|created|createdAddress|");

    	Iterator<Message> messageIterator = messageDaoLocal.findAll(0, max).iterator();
    	while(messageIterator.hasNext()) {
    		Message next = messageIterator.next();
    		bufferedWriter.write("\n");
    		bufferedWriter.write(convertToCsvLine(next));
    		generateContentFile(next);
    	}

    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);

		return backupFiles;
	}

	private String convertToCsvLine(Message next) {
	    LOGGER.info("convertToCsvLine(Page next) aufgerufen.");

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// uuid|gender|firstname|lastname|mail|telephone|fax|content|privacy|created|createdAddress|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

        nextString.append(next.getGender().toString());
        nextString.append("|");

		nextString.append(next.getFirstname());
		nextString.append("|");

		nextString.append(next.getLastname());
		nextString.append("|");

		nextString.append(next.getMail());
		nextString.append("|");

        nextString.append(next.getTelephone());
        nextString.append("|");

        nextString.append(next.getFax());
        nextString.append("|");
        
		nextString.append("message_" + next.getUuid() + ".txt");
		nextString.append("|");

        nextString.append(next.getPrivacy().toString());
        nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getCreated()));
		nextString.append("|");

		nextString.append(next.getCreatedAddress());
		nextString.append("|");

		return nextString.toString();
	}

	private void generateContentFile(Message message) throws IOException {
		File file = new File("/tmp/message_" + message.getUuid() + ".txt");
		FileWriter fileWriter = new FileWriter(file);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    	bufferedWriter.write(message.getContent());
    	bufferedWriter.close();
    	fileWriter.close();
    	backupFiles.add(file);
	}

}// Ende class
