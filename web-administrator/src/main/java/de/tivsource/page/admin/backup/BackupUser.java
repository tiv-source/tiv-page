package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.Role;
import de.tivsource.page.entity.administration.User;

public class BackupUser {

	private static final int MAX = 1500;
	
	private static UserDaoLocal userDaoLocal;

	public static void setUserDaoLocal(UserDaoLocal userDaoLocal) {
		BackupUser.userDaoLocal = userDaoLocal;
	}

	public static File getBackupFile() throws IOException {

		// Datei Kram
		File backupFile = new File("/tmp/user.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|username|email|firstname|lastname|password|roles|created|modified|ip|modifiedBy|");

    	Iterator<User> typeIterator = userDaoLocal.findAll(0, MAX).iterator();
    	while(typeIterator.hasNext()) {
    		User next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(User next) {

	    // uuid|username|email|firstname|lastname|password|roles|created|modified|ip|modifiedBy|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

		nextString.append(next.getUsername());
		nextString.append("|");

		nextString.append(next.getEmail());
		nextString.append("|");

		nextString.append(next.getFirstname());
		nextString.append("|");

		nextString.append(next.getLastname());
		nextString.append("|");

		nextString.append(next.getPassword());
		nextString.append("|");

		nextString.append(generateRoles(next.getRoles()));
		nextString.append("|");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nextString.append(simpleDateFormat.format(next.getCreated()));
		nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getModified()));
		nextString.append("|");

		nextString.append(next.getIp());
		nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");
		
		return nextString.toString();
	}

	private static String generateRoles(List<Role> roles) {
	    StringBuffer nextString = new StringBuffer();
		Iterator<Role> tagIterator = roles.iterator();
		while(tagIterator.hasNext()) {
			Role next = tagIterator.next();
		    nextString.append(next.getUuid());
		    nextString.append(";");
		}
	    return nextString.toString();
	}
	
}// Ende class
