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

import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

public class BackupSalutation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupSalutation.class);

    private SalutationDaoLocal salutationDaoLocal;

    private List<File> backupFiles = new ArrayList<File>();

    public BackupSalutation(SalutationDaoLocal salutationDaoLocal) {
        super();
        this.salutationDaoLocal = salutationDaoLocal;
    }

    public List<File> getBackupFiles() throws IOException {
        LOGGER.info("getBackupFile() aufgerufen.");
        
        // Datei Kram
        File backupFile = new File("/tmp/salutation.csv");
        FileWriter backupFileWriter = new FileWriter(backupFile);
        BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

        // Format Definition
        backupFileWriterOut.write("[Format Definition] => uuid|uuid(de)|name(de)|description(de)|keywords(de)|longSalutation(de)|locale(de)|uuid(en)|name(en)|description(en)|keywords(en)|longSalutation(en)|locale(en)|version|visible|created|modified|modifiedBy|modifiedAddress|");

        Iterator<Salutation> typeIterator = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll()).iterator();
        while(typeIterator.hasNext()) {
            Salutation next = typeIterator.next();
            backupFileWriterOut.write("\n");
            backupFileWriterOut.write(convertToCsvLine(next));
            generateDescriptionFile(next, "de");
            generateDescriptionFile(next, "en");
        }
        backupFileWriterOut.close();
        backupFileWriter.close();
        backupFiles.add(backupFile);

        return backupFiles;
    }// Ende getBackupFiles()

    private static String convertToCsvLine(Salutation next) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // key|value|created|modified|modifiedBy|modifiedAddress|
        StringBuffer nextString = new StringBuffer();

        nextString.append(next.getUuid());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescriptionObject(Language.DE).getName());
        nextString.append("|");

        nextString.append("salutation_" + next.getUuid() + "_de.txt");
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getKeywords());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getLongSalutation());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.DE).getLocale());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescriptionObject(Language.EN).getName());
        nextString.append("|");

        nextString.append("salutation_" + next.getUuid() + "_en.txt");
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getKeywords());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getLongSalutation());
        nextString.append("|");

        nextString.append(next.getDescriptionObject(Language.EN).getLocale());
        nextString.append("|");

        nextString.append(0);
        nextString.append("|");

        nextString.append(next.getVisible());
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

    private void generateDescriptionFile(Salutation salutation, String locale) throws IOException {
        File file = new File("/tmp/salutation_" + salutation.getUuid() + "_" + locale +".txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(salutation.getDescriptionObject(Language.valueOf(locale.toUpperCase())).getDescription());
        bufferedWriter.close();
        fileWriter.close();
        backupFiles.add(file);
    }

}// Ende class
