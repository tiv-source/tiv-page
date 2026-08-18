/**
 * 
 */
package de.tivsource.page.install;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.common.salutation.description.SalutationDescription;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.entity.enumeration.Language;

/**
 * @author Marc Michele
 *
 */
public class CreateSalutation {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger("INFO");
    
    private SalutationDaoLocal salutationDaoLocal;

    public CreateSalutation(SalutationDaoLocal salutationDaoLocal) {
        super();
        this.salutationDaoLocal = salutationDaoLocal;
    }

    public void generate() {
        LOGGER.info("restore() aufgerufen.");

        InputStream inputStream = CreateSalutation.class
                .getClassLoader()
                .getResourceAsStream("csv/salutation/salutation.csv");

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        
        try {
            String line = in.readLine();
            LOGGER.info("Erste Zeile: " + line);
            LOGGER.info("Ausgangsformat => [Format Definition]");
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    Salutation salutation = convert(line);
                    if(salutationDaoLocal.findByUuid(salutation.getUuid()) == null) {
                        LOGGER.info("Salutation: " + salutation.getName("de") + " erstellt.");
                        salutationDaoLocal.merge(salutation);
                    } else {
                        LOGGER.info("Salutation: " + salutation.getName("de") + " schon vorhanden.");
                    }
                }
            }// Ende while
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Salutation convert(String line) throws FileNotFoundException, IOException {

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // uuid|
        // uuid(de)|name(de)|description(de)|keywords(de)|longSalutation(de)|locale(de)|
        // uuid(en)|name(en)|description(en)|keywords(en)|longSalutation(en)|locale(en)|
        // version|visible|created|modified|modifiedBy|modifiedAddress|

        Salutation salutation = new Salutation();

        salutation.setUuid(items[0]);

        Map<Language, SalutationDescription> descriptionMap = new HashMap<Language, SalutationDescription>();
        descriptionMap.put(Language.DE, createDescription(
                items[1], items[2], items[3], items[4], 
                items[5], items[6], salutation));
        descriptionMap.put(Language.EN, createDescription(
                items[7], items[8], items[9], items[10], 
                items[11], items[12], salutation));
        salutation.setDescriptionMap(descriptionMap);

        salutation.setVersion(Integer.parseInt(items[13]));
        salutation.setVisible(items[14].equals("true") ? true : false);

        salutation.setCreated(convertDateString(items[15]));
        salutation.setModified(convertDateString(items[16]));
        salutation.setModifiedBy(items[17]);
        salutation.setModifiedAddress(items[18]);

        return salutation;
    }

    private SalutationDescription createDescription(String uuid, String name, String descriptionFile,
            String keywords, String longSalutation, String locale, Salutation salutation) {
        
        InputStream inputStream = CreateSalutation.class
                .getClassLoader()
                .getResourceAsStream("csv/salutation/" + descriptionFile);
        BufferedReader contentInput = new BufferedReader(new InputStreamReader(inputStream));
        
        StringBuffer contentString = new StringBuffer();
        try {
            String line = null;
            while ((line = contentInput.readLine()) != null) {
                contentString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SalutationDescription salutationDescription = new SalutationDescription();
        salutationDescription.setUuid(uuid);
        salutationDescription.setName(name);
        salutationDescription.setDescription(contentString.toString());
        salutationDescription.setKeywords(keywords);
        salutationDescription.setLongSalutation(longSalutation);
        salutationDescription.setLocale(Language.valueOf(locale));
        salutationDescription.setSalutation(salutation);
        return salutationDescription;
    }

    /**
     * Methode zum Konvertieren eines Strings des Formates "1970-12-01 23:59:59" in ein Date-Object. 
     * @param dateString
     * @return
     */
    private Date convertDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }// Ende convertDateString(String dateString)

}// Ende class
