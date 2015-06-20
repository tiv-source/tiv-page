package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.entity.administration.Role;

public class BackupRole {

	private static final int MAX = 1500;
	
	private static RoleDaoLocal roleDaoLocal;

	public static void setRoleDaoLocal(RoleDaoLocal roleDaoLocal) {
		BackupRole.roleDaoLocal = roleDaoLocal;
	}

	public static File getBackupFile() throws IOException {

		// Datei Kram
		File backupFile = new File("/tmp/role.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|technical|created|modified|modifiedBy|ip|");

    	Iterator<Role> typeIterator = roleDaoLocal.findAll(0, MAX).iterator();
    	while(typeIterator.hasNext()) {
    		Role next = typeIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Role next) {

		// uuid|technical|created|modified|modifiedBy|ip|

		StringBuffer nextString = new StringBuffer();

		nextString.append(next.getUuid());
		nextString.append("|");

		nextString.append(next.getTechnical());
		nextString.append("|");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nextString.append(simpleDateFormat.format(next.getCreated()));
		nextString.append("|");

		nextString.append(simpleDateFormat.format(next.getModified()));
		nextString.append("|");

		nextString.append(next.getModifiedBy());
		nextString.append("|");

		nextString.append(next.getIp());
		nextString.append("|");

		return nextString.toString();
	}
	
	
}// Ende class
