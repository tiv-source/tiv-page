package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.log4j.Logger;

import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.picture.Picture;
import de.tivsource.page.enumeration.UrlType;

public class BackupPicture {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(BackupPicture.class);

	private static PictureDaoLocal pictureDaoLocal;

    public static void setPictureDaoLocal(PictureDaoLocal pictureDaoLocal) {
		BackupPicture.pictureDaoLocal = pictureDaoLocal;
	}

	public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/picture.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|uuid(de)|name(de)|description(de)|keywords(de)|uuid(en)|name(en)|description(en)|keywords(en)|pictureUrls|orderNumber|visible|gallery|created|modified|modifiedBy|modifiedAddress|");

    	Iterator<Picture> pictureIterator = pictureDaoLocal.findAll(0, pictureDaoLocal.countAll()).iterator();
    	while(pictureIterator.hasNext()) {
    		Picture next = pictureIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Picture next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|
	    // uuid(de)|name(de)|description(de)|keywords(de)|
	    // uuid(en)|name(en)|description(en)|keywords(en)|
	    // pictureUrls|orderNumber|visible|gallery|created|modified|modifiedBy|modifiedAddress|
	    
		StringBuffer nextString = new StringBuffer();

		
		nextString.append(next.getUuid());
		nextString.append("|");


        nextString.append(next.getDescriptionMap().get(Language.DE).getUuid());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.DE).getName());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.DE).getDescription());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.DE).getKeywords());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.EN).getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescriptionMap().get(Language.EN).getName());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.EN).getDescription());
        nextString.append("|");

        nextString.append(next.getDescriptionMap().get(Language.EN).getKeywords());
        nextString.append("|");

        
        nextString.append(next.getPictureUrls().get(UrlType.THUMBNAIL).getUuid());
        nextString.append(",");
        nextString.append(next.getPictureUrls().get(UrlType.THUMBNAIL).getUrl());
        nextString.append(",");
        nextString.append("THUMBNAIL");
        nextString.append(";");

        nextString.append(next.getPictureUrls().get(UrlType.NORMAL).getUuid());
        nextString.append(",");
        nextString.append(next.getPictureUrls().get(UrlType.NORMAL).getUrl());
        nextString.append(",");
        nextString.append("NORMAL");
        nextString.append(";");

        nextString.append(next.getPictureUrls().get(UrlType.LARGE).getUuid());
        nextString.append(",");
        nextString.append(next.getPictureUrls().get(UrlType.LARGE).getUrl());
        nextString.append(",");
        nextString.append("LARGE");
        nextString.append(";");

        nextString.append(next.getPictureUrls().get(UrlType.FULL).getUuid());
        nextString.append(",");
        nextString.append(next.getPictureUrls().get(UrlType.FULL).getUrl());
        nextString.append(",");
        nextString.append("FULL");
        nextString.append(";");
        nextString.append("|");

        nextString.append(next.getOrderNumber().toString());
        nextString.append("|");

        nextString.append(next.getVisible().toString());
        nextString.append("|");

        nextString.append(next.getGallery().getUuid());
        nextString.append("|");
        
        nextString.append(simpleDateFormat.format(next.getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getModified()));
        nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");

        nextString.append(next.getModifiedAddress());
        nextString.append("|");

		return nextString.toString();
	}

}// Ende class
