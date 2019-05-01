package de.tivsource.page.admin.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.slider.Slider;

public class BackupSlider {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupSlider.class);

	private static SliderDaoLocal sliderDaoLocal;

	/**
     * @param sliderDaoLocal the sliderDaoLocal to set
     */
    public static void setSliderDaoLocal(SliderDaoLocal sliderDaoLocal) {
        BackupSlider.sliderDaoLocal = sliderDaoLocal;
    }

    public static File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
		// Datei Kram
		File backupFile = new File("/tmp/slider.csv");
    	FileWriter backupFileWriter = new FileWriter(backupFile);
    	BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

    	// Format Definition 
    	backupFileWriterOut.write("[Format Definition] => uuid|clickable|url|name|page|orderNumber|uuid;original;large;normal;small;thumbnail;micro;standard;created;modified;modifiedBy;modifiedAddress;|visible|created|modified|modifiedBy|modifiedAddress|");

    	Iterator<Slider> sliderIterator = sliderDaoLocal.findAll(0, sliderDaoLocal.countAll()).iterator();
    	while(sliderIterator.hasNext()) {
    		Slider next = sliderIterator.next();
    		backupFileWriterOut.write("\n");
    		backupFileWriterOut.write(convertToCsvLine(next));
    	}
    	backupFileWriterOut.close();
    	backupFileWriter.close();

    	return backupFile;
	}// Ende getBackupFiles()

	private static String convertToCsvLine(Slider next) {

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// uuid|clickable|url|name|page|orderNumber|
	    // uuid;original;large;normal;small;thumbnail;micro;standard;created;modified;modifiedBy;modifiedAddress;|
	    // visible|created|modified|modifiedBy|modifiedAddress

		StringBuffer nextString = new StringBuffer();

		
		nextString.append(next.getUuid());
		nextString.append("|");

		nextString.append(next.getClickable().toString());
		nextString.append("|");

		nextString.append(next.getUrl());
		nextString.append("|");

		nextString.append(next.getName());
		nextString.append("|");

		nextString.append(next.getPage());
		nextString.append("|");

		nextString.append(next.getOrderNumber().toString());
		nextString.append("|");

		nextString.append(next.getImage().getUuid());
		nextString.append(";");

        nextString.append(next.getImage().getOriginal());
        nextString.append(";");

        nextString.append(next.getImage().getLarge());
        nextString.append(";");

        nextString.append(next.getImage().getNormal());
        nextString.append(";");

        nextString.append(next.getImage().getSmall());
        nextString.append(";");

        nextString.append(next.getImage().getThumbnail());
        nextString.append(";");

        nextString.append(next.getImage().getMicro());
        nextString.append(";");

        nextString.append(next.getImage().isStandard().toString());
        nextString.append(";");

        nextString.append(simpleDateFormat.format(next.getImage().getCreated()));
        nextString.append(";");

        nextString.append(simpleDateFormat.format(next.getImage().getModified()));
        nextString.append(";");

        nextString.append(next.getImage().getModifiedBy());
        nextString.append(";");

        nextString.append(next.getImage().getModifiedAddress());
        nextString.append(";");
        nextString.append("|");

        nextString.append(next.getVisible().toString());
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
