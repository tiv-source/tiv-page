/**
 * 
 */
package de.tivsource.page.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.contentitem.Content;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.entity.property.Property;
import de.tivsource.page.rewriteobject.URIToUploadFile;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * @author Marc Michele
 *
 */
public class AppStartup implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger("INFO");

    @EJB
    private RoleDaoLocal roleDaoLocal;

    @EJB
    private UserDaoLocal userDaoLocal;

    @EJB
    private PropertyDaoLocal propertyDaoLocal;

    @EJB
    private PageDaoLocal pageDaoLocal;

    @EJB
    private CSSGroupDaoLocal cssGroupDaoLocal;
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        // Hole Eigenschaft aus der Datenbank
        Property dbProperty = propertyDaoLocal.findByKey("isInstalled");
        boolean isInstalled = false;
        // Prüfe ob der Wert in der Datenbank vorhanden ist
        if(dbProperty != null) {
            isInstalled = dbProperty.getValue().equals("true") ? true : false;
        }

        // Wenn noch nichts Installiert wurde
        if(!isInstalled) {
            CreateRole createRole = new CreateRole();
            createRole.setRoleDaoLocal(roleDaoLocal);
            createRole.generate();

            CreateUser createUser = new CreateUser();
            createUser.setUserDaoLocal(userDaoLocal);
            createUser.setRoleDaoLocal(roleDaoLocal);
            createUser.generate();

            // Setze den Wert das die Installation ausgeführt wurde
            Property property = new Property();
            property.setKey("isInstalled");
            property.setModified(new Date());
            property.setModifiedAddress("localhost");
            property.setModifiedBy("Installer");
            property.setValue("true");
            propertyDaoLocal.merge(property);
        }

        checkAllAttributesExisting();
        checkAllPagesExisting();

    }// Ende contextInitialized

    /**
     * Methode um zu überprüfen ob alle benötigten Attribute in der Datenbank vorhanden sind.
     */
    private void checkAllAttributesExisting() {
        LOGGER.info("checkAllAttributesExisting() aufgerufen");
        InputStream inputStream = AppStartup.class.getClassLoader().getResourceAsStream("csv/property.csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    // Format: key|value|comment|created|modified|modifiedBy|modifiedAddress|
                    String[] properties = line.split("\\|");
                    if(!checkAttribute(properties[0], properties[1], properties[2], properties[3], properties[4], properties[5], properties[6])) {
                        LOGGER.info("Eigenschaft: " + properties[0] + " hinzugefügt.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }// Ende checkAllAttributesExisting()

    private Boolean checkAttribute(String inputKey, String inputValue,
            String inputComment, String inputCreated, String inputModified, 
            String inputModifiedBy, String inputModifiedAddress) throws ParseException {
        Property dbProperty = propertyDaoLocal.findByKey(inputKey);
        // Prüfe ob der Wert in der Datenbank vorhanden ist
        if(dbProperty != null) {
            return true;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm:ss", Locale.GERMAN);
        Property property = new Property();
        property.setKey(inputKey);
        property.setModified(simpleDateFormat.parse(inputModified));
        property.setModifiedAddress(inputModifiedAddress);
        property.setModifiedBy(inputModifiedBy);
        property.setValue(inputValue);
        property.setCreated(simpleDateFormat.parse(inputCreated));
        property.setComment(inputComment);
        propertyDaoLocal.merge(property);
        return false;
    }// Ende checkAttribute(String inputKey, String inputValue, String inputComment, String inputCreated, String inputModified, String inputModifiedBy, String inputModifiedAddress)

    // TODO: Hier müssen noch Dinge zum aufräumen hin !!
    // Die folgenden Schlüssel werden nicht mehr gebraucht:
    // - gallery.uuid.for.page.picture
    // - pictureItem.image.type
    // - gallery.uuid.for.vacancy.picture
    // - gallery.uuid.for.location.picture
    // - gallery.uuid.for.vacancy.picture
    // - gallery.uuid.for.event.picture
    // - gallery.uuid.for.event.picture

    private void checkAllPagesExisting() {
        InputStream inputStream = AppStartup.class.getClassLoader().getResourceAsStream("csv/page/page.csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Object lock = new Object();
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                    synchronized(lock){
                        Page page = convert(line);
                        LOGGER.info("Teste Seite mit der Technical: " + page.getTechnical());
                        Page dbPage = pageDaoLocal.findByTechnical(page.getTechnical());
                        if(dbPage == null) {
                            LOGGER.info("Seite mit der UUID: " + page.getUuid() + " erstellt");
                            page.getImage().generate();
                            // Bild wird nicht gelöscht
                            //page.getImage().getUploadFile().delete();
                            pageDaoLocal.merge(page);
                        }
                    }// Ende lock
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }// Ende checkAllPagesExisting()

    private Page convert(String line) throws FileNotFoundException, IOException, URISyntaxException {

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // uuid|technical|version|
        // de_uuid|de_name|de_description|de_keywords|de_content_uuid|de_content_file|de_content_created|de_content_modified|
        // en_uuid|en_name|en_description|en_keywords|en_content_uuid|en_content_file|en_content_created|en_content_modified|
        // image|pictureOnPage|cssGroup|special|visible|created|modified|modifiedBy|modifiedAddress|

        Page page = new Page();

        page.setUuid(items[0]);
        page.setTechnical(items[1]);

        // Wenn es die Seite mit der UUID gibt dann wird die Versionsnummer aus
        // dem Datenbankobjekt genommen sonst die aus dem Backup
        page.setVersion(Integer.parseInt(items[2]));            

        Map<Language, Description> descriptionMap = new HashMap<Language, Description>();
        descriptionMap.put(Language.DE, createDescription(
                items[3], items[4], "DE", items[5], 
                items[6], page));
        descriptionMap.put(Language.EN, createDescription(
                items[11], items[12], "EN", items[13], 
                items[14], page));
        page.setDescriptionMap(descriptionMap);

        Map<Language, Content> contentMap = new HashMap<Language, Content>();
        contentMap.put(Language.DE, createContent(
                items[7], "DE", items[8], items[9], 
                items[10], page));
        contentMap.put(Language.EN, createContent(
                items[15], "EN", items[16], items[17], 
                items[18], page));
        page.setContentMap(contentMap);

        page.setImage(createImage(items[19], page));
        page.setPictureOnPage(items[20].equals("true") ? true : false);

        page.setCssGroup(cssGroupDaoLocal.findByUuid(items[21]));

        page.setSpecial(items[22].equals("true") ? true : false);
        page.setVisible(items[23].equals("true") ? true : false);
        page.setCreated(convertDateString(items[24]));
        page.setModified(convertDateString(items[25]));
        page.setModifiedBy(items[26]);
        page.setModifiedAddress(items[27]);

        return page;
    }


    private Description createDescription(String uuid, String name,
            String locale, String description, String keywords, Page page) {
        Description returnDescription = new Description();
        returnDescription.setUuid(uuid);
        returnDescription.setName(name);
        returnDescription.setLanguage(Language.valueOf(locale));
        returnDescription.setDescription(description);
        returnDescription.setKeywords(keywords);
        returnDescription.setNamingItem(page);
        return returnDescription;
    }

    private Content createContent(String uuid, String locale,
            String contentFile, String created, String modified, Page page) {
        InputStream inputStream = AppStartup.class.getClassLoader().getResourceAsStream("csv/page/" + contentFile);
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
        Content returnContent = new Content();
        returnContent.setUuid(uuid);
        returnContent.setContent(contentString.toString());
        returnContent.setLanguage(Language.valueOf(locale));
        returnContent.setCreated(convertDateString(created));
        returnContent.setModified(convertDateString(modified));
        returnContent.setContentItem(page);
        return returnContent;
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

    private PictureItemImage createImage(String line, Page page) throws FileNotFoundException, IOException, URISyntaxException {
        LOGGER.info("Zeile die konvertiert wird: " + line);
        // Zerlege CSV-Zeile in String-Array.
        String[] imageItem = line.split(":");
        LOGGER.info("PictureItemImage UUID: " + imageItem[0]);
        LOGGER.info("PictureItemImage UploadFile: " + imageItem[1]);
        PictureItemImage pictureItemImage = new PictureItemImage();
        pictureItemImage.setUuid(imageItem[0]);
        pictureItemImage.setPictureItem(page);
        URL inputUrl = AppStartup.class.getClassLoader().getResource("csv/page/" + imageItem[1]);
        File inputFile = new File(inputUrl.toURI());
        pictureItemImage.setUploadFile(
                URIToUploadFile.convert(
                        inputFile.getAbsolutePath(),
                        Files.probeContentType(Paths.get(inputUrl.toURI())),
                        true, 
                        "inputName" + inputFile.getName(),
                        inputFile.getName(), 
                        "originalName" + inputFile.getName() 
                )
        );

        pictureItemImage.setModified(new Date());
        pictureItemImage.setModifiedAddress("localhost");
        pictureItemImage.setModifiedBy("Installer");
        pictureItemImage.setCreated(new Date());
        pictureItemImage.setStandard(true);

        return pictureItemImage;
    }

}// Ende class
