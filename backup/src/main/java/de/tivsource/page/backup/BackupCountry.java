/**
 * 
 */
package de.tivsource.page.backup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.dao.country.CountryDaoLocal;

/**
 * @author Marc Michele
 * 
 */
public class BackupCountry {

    private CountryDaoLocal countryDaoLocal;

    public BackupCountry(CountryDaoLocal countryDaoLocal) {
        super();
        this.countryDaoLocal = countryDaoLocal;
    }

    public File getBackupFile() throws IOException {

        // Datei Kram
        File backupFile = new File("/tmp/country.csv");
        FileWriter backupFileWriter = new FileWriter(backupFile);
        BufferedWriter backupFileWriterOut = new BufferedWriter(backupFileWriter);

        // Format Definition
        backupFileWriterOut.write("[Format Definition] => uuid|language|alpha2|alpha3|numeric|areaCode|name_de(uuid)|name_de(name)|name_en(uuid)|name_en(name)|created|modified|modifiedAddress|modifiedBy|");

        Iterator<Country> countryIterator = countryDaoLocal.findAll(0, countryDaoLocal.countAll()).iterator();
        while(countryIterator.hasNext()) {
            Country next = countryIterator.next();
            backupFileWriterOut.write("\n");
            backupFileWriterOut.write(convertToCsvLine(next));
        }
        backupFileWriterOut.close();
        backupFileWriter.close();

        return backupFile;
    }// Ende getBackupFiles()

    private String convertToCsvLine(Country next) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // [Format Definition] => uuid|language|alpha2|alpha3|numeric|areaCode|name_de(uuid)|name_de(name)|name_en(uuid)|name_en(name)|created|modified|modifiedAddress|modifiedBy|
        StringBuffer nextString = new StringBuffer();

        nextString.append(next.getUuid());
        nextString.append("|");

        nextString.append(next.getLanguage());
        nextString.append("|");

        nextString.append(next.getAlpha2());
        nextString.append("|");

        nextString.append(next.getAlpha3());
        nextString.append("|");

        nextString.append(next.getNumeric());
        nextString.append("|");

        nextString.append(next.getAreaCode());
        nextString.append("|");

        nextString.append(next.getDescription("de").getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescription("de").getName());
        nextString.append("|");

        nextString.append(next.getDescription("en").getUuid());
        nextString.append("|");
        
        nextString.append(next.getDescription("en").getName());
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getCreated()));
        nextString.append("|");

        nextString.append(simpleDateFormat.format(next.getModified()));
        nextString.append("|");

        nextString.append(next.getModifiedAddress());
        nextString.append("|");

        nextString.append(next.getModifiedBy());
        nextString.append("|");

        return nextString.toString();

    }// Ende convertToCsvLine(Country next)

}// Ende class
