package de.tivsource.page.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Klasse die zur Verwaltung von Dateien dient.
 *
 * @author Marc Michele
 *
 */
public class FileActions {

    private static final Logger logger = LogManager.getLogger(FileActions.class);

    // TODO: Muss umbenannt werden / Gibts vielleicht schon was besseres ??
    public static void savePictureFile(File source, File destination) throws Exception {

        logger.debug("savePictureFile aufgerufen");
		
        byte[] buffer = new byte[ (int) source.length() ]; 
        InputStream in = new FileInputStream(source); 
        in.read( buffer ); 
        FileOutputStream fileOutStream = new FileOutputStream(destination);
        fileOutStream.write(buffer);
        fileOutStream.flush();
        fileOutStream.close();
        in.close();
    }

}// Ende class
