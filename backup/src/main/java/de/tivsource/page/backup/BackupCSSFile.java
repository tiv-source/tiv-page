package de.tivsource.page.backup;

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

import de.tivsource.page.common.css.CSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

/**
 * Klasse die dazu dient eine Datensicherung der benutzen CSS-Dateien und
 * Objekte zu erstellen.
 *
 * @author Marc Michele
 *
 */
public class BackupCSSFile {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager
            .getLogger(BackupCSSFile.class);

    /**
     * DAO-Klasse für CSSFile Objekte.
     */
    private CSSFileDaoLocal cssFileDaoLocal;

    /**
     * Dateien die in die Sicherung mit einbezogen werden sollen.
     */
    private List<File> backupFiles = new ArrayList<File>();

    /**
     * Konstruktor der Sicherungsklasse, es muss lediglich die DAO-Klasse
     * übergeben werden.
     *
     * @param cssFileDaoLocalInput - DAO Klasse für CSSFile Objekte
     */
    public BackupCSSFile(final CSSFileDaoLocal cssFileDaoLocalInput) {
        super();
        this.cssFileDaoLocal = cssFileDaoLocalInput;
    }

    /**
     * Methode die das Backup erzeugt.
     *
     * @return
     * @throws IOException
     */
    public List<File> getBackupFiles() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");

        // Datei Kram
        File backupFile = new File("/tmp/cssFile.csv");
        FileWriter fileWriter = new FileWriter(backupFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // Format Definition
        bufferedWriter.write("[Format Definition] => uuid|name|description|priority|path|created|modified|modifiedBy|modifiedAddress|version|");

        Iterator<CSSFile> cssFileIterator = cssFileDaoLocal.findAll(0, cssFileDaoLocal.countAll()).iterator();
        while (cssFileIterator.hasNext()) {
            CSSFile next = cssFileIterator.next();
            bufferedWriter.write("\n");
            bufferedWriter.write(convertToCsvLine(next));
            storeCSSFileInList(next);
        }

        bufferedWriter.close();
        fileWriter.close();

        backupFiles.add(backupFile);

        return backupFiles;
    }

    /**
     * Methode zum konvertieren eines CSSFile Objekte in einen String.
     * 
     * @param next - CSSFile Objekt das konvertiert werden soll
     * @return String - Der String der dem übergebenen CSSFile Objekt entspricht.
     */
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

    /**
     * Methode zum einfügen der CSS-Datei die zu dem in CSSFile Objekt gehört in
     * die Liste der Dateien die gesichert werden sollen.
     * 
     * @param next - CSSFile Objekt von dem die CSS-Datei in die Liste eingefügt
     *            werden soll
     */
    private void storeCSSFileInList(final CSSFile next) {
        File file = new File(next.getPath());
        backupFiles.add(file);
    }

}// Ende class
