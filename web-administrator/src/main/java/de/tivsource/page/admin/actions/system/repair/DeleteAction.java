package de.tivsource.page.admin.actions.system.repair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.entity.picture.Picture;

/**
 * 
 * @author Marc Michele
 *
 */
public class DeleteAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -7853019180748121334L;

    @Autowired
    @Qualifier("pictureDaoLocal")
    private PictureDaoLocal pictureDaoLocal;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(DeleteAction.class);

    @Autowired
    public DeleteAction(PropertyService propertyService) {
        super(propertyService);
    }

    @Override
    @Actions({ 
        @Action(
            value = "delete", 
            results = { @Result(name = "success", type = "tiles", location = "deleteSuccess") }
        ) 
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        if(true) {
            return ERROR;
        }

        InputStream inputStream = DeleteAction.class.getClassLoader().getResourceAsStream("tmp/Bilder_zu_loeschen.csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("modifiedBy")) {
                    String[] properties = line.split(";");
                    // properties[0], properties[1]
                    LOGGER.info("Bild von: " + properties[0]);
                    if(pictureDaoLocal.isPicture(properties[1])) {
                        Picture picture = pictureDaoLocal.findByUuid(properties[1]);
                        LOGGER.info("Lösche das Bild mit der UUID: " + properties[1]);
                        pictureDaoLocal.delete(picture);
                    } else {
                        LOGGER.info("Die UUID ist kein Bild: " + properties[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }// Ende execute()

}// Ende class
