package de.tivsource.page.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

public class BackupCSSFile {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupCSSFile.class);

    private CSSFileDaoLocal cssFileDaoLocal;

    public BackupCSSFile(CSSFileDaoLocal cssFileDaoLocal) {
        super();
        this.cssFileDaoLocal = cssFileDaoLocal;
    }

    public File getBackupFile() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");

        // Erzeuge eine zufällige UUID
        String randomId = UUID.randomUUID().toString();

        // Datei Kram
        File file = new File("/tmp/cssFile_" + randomId + ".csv");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Format Definition
        bufferedWriter.write("[Format Definition] => uuid|name|description|priority|path|created|modified|modifiedBy|modifiedAddress|version|");

        Iterator<CSSFile> cssFileIterator = cssFileDaoLocal.findAll(0,cssFileDaoLocal.countAll()).iterator();
        while (cssFileIterator.hasNext()) {
            CSSFile next = cssFileIterator.next();
            bufferedWriter.write("\n");
            bufferedWriter.write(convertToCsvLine(next));
        }

        bufferedWriter.close();
        fileWriter.close();

        return file;
    }

    private String convertToCsvLine(CSSFile next) {
        LOGGER.info("convertToCsvLine(CSSFile next) aufgerufen.");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // uuid|
        // name|description|priority|path|
        // created|modified|modifiedBy|modifiedAddress|version|

        StringBuffer nextString = new StringBuffer();

        nextString.append(next.getUuid());
        nextString.append("|");

        nextString.append(next.getName());
        nextString.append("|");

        nextString.append(next.getDescription());
        nextString.append("|");

        nextString.append(next.getPriority().toString());
        nextString.append("|");

        nextString.append(next.getPath());
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getModified()));
        nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");

        nextString.append(next.getModifiedAddress());
        nextString.append("|");

        nextString.append(next.getVersion().toString());
        nextString.append("|");

        return nextString.toString();
    }

}// Ende class
