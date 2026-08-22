package de.tivsource.page.admin.actions.system.repair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.common.file.UploadFile;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.gallery.Gallery;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.pictureitem.PictureItemImage;

/**
 * 
 * @author Marc Michele
 *
 */
public class PictureAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 6087278797726264276L;

    @Autowired
    @Qualifier("appointmentDaoLocal")
    private AppointmentDaoLocal appointmentDaoLocal;

    @Autowired
    @Qualifier("galleryDaoLocal")
    private GalleryDaoLocal galleryDaoLocal;

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PictureAction.class);

    @Autowired
    public PictureAction(PropertyService propertyService) {
        super(propertyService);
    }

    @Override
    @Actions({ 
        @Action(
            value = "picture", 
            results = { @Result(name = "success", type = "tiles", location = "pictureSuccess") }
        ) 
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        if(true) {
            return ERROR;
        }

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        InputStream inputStream = PictureAction.class.getClassLoader().getResourceAsStream("csv/Appointment_Bilder.csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("classname")) {
                    // Format: key|value|comment|created|modified|modifiedBy|modifiedAddress|
                    String[] properties = line.split(";");
                    // properties[0], properties[1], properties[2], properties[7], properties[3], properties[4], properties[5], properties[6]
                    LOGGER.info("Klasse: " + properties[0]);
                    if(properties[0].startsWith("Appointment")) {
                        if(appointmentDaoLocal.isAppointmentUuidVisibleCheck(properties[1])) {
                            Appointment appointment = appointmentDaoLocal.findByUuid(properties[1]);
                            String imagePath = "/srv/tiv-page/FULL/" + properties[3];
                            Path path = Paths.get(imagePath);
                            if (Files.exists(path)) {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert.");
                                if(appointment.getImage() == null) {
                                    appointment.setImage(new PictureItemImage());
                                    appointment.getImage().setUuid(UUID.randomUUID().toString());
                                    appointment.getImage().setPictureItem(appointment);
                                    appointment.getImage().setCreated(new Date());
                                    appointment.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    appointment.getImage().generate();
                                    appointment.getImage().setModified(new Date());
                                    appointment.getImage().setModifiedAddress(remoteAddress);
                                    appointment.getImage().setModifiedBy(remoteUser);
                                } else {
                                    appointment.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    appointment.getImage().generate();
                                }
                                appointmentDaoLocal.merge(appointment);
                            } else {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert nicht");
                            }
                        }
                    }
                    if(properties[0].startsWith("Gallery")) {
                        if(galleryDaoLocal.isGalleryWithoutVisibleCheck(properties[1])) {
                            Gallery gallery = galleryDaoLocal.findByUuid(properties[1]);
                            String imagePath = "/srv/tiv-page/FULL/" + properties[3];
                            Path path = Paths.get(imagePath);
                            if (Files.exists(path)) {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert.");
                                if(gallery.getImage() == null) {
                                    gallery.setImage(new PictureItemImage());
                                    gallery.getImage().setUuid(UUID.randomUUID().toString());
                                    gallery.getImage().setPictureItem(gallery);
                                    gallery.getImage().setCreated(new Date());
                                    gallery.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    gallery.getImage().generate();
                                    gallery.getImage().setModified(new Date());
                                    gallery.getImage().setModifiedAddress(remoteAddress);
                                    gallery.getImage().setModifiedBy(remoteUser);
                                } else {
                                    gallery.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    gallery.getImage().generate();
                                }
                                galleryDaoLocal.merge(gallery);
                            } else {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert nicht");
                            }
                        }
                    }
                    if(properties[0].startsWith("Page")) {
                        if(pageDaoLocal.isPageUuidWithoutVisibleCheck(properties[1])) {
                            Page page = pageDaoLocal.findByUuid(properties[1]);
                            String imagePath = "/srv/tiv-page/FULL/" + properties[3];
                            Path path = Paths.get(imagePath);
                            if (Files.exists(path)) {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert.");
                                if(page.getImage() == null) {
                                    page.setImage(new PictureItemImage());
                                    page.getImage().setUuid(UUID.randomUUID().toString());
                                    page.getImage().setPictureItem(page);
                                    page.getImage().setCreated(new Date());
                                    page.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    page.getImage().generate();
                                    page.getImage().setModified(new Date());
                                    page.getImage().setModifiedAddress(remoteAddress);
                                    page.getImage().setModifiedBy(remoteUser);
                                } else {
                                    page.getImage().setUploadFile(
                                            convert(imagePath, "image/png", true, "csvList", properties[3], properties[3])
                                            );
                                    page.getImage().generate();
                                }
                                pageDaoLocal.merge(page);
                            } else {
                                LOGGER.info("Die Datei der Klasse: " + properties[0] + " mit dem Namen: " + properties[3] + " existiert nicht");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }// Ende execute()

    private UploadFile convert(String absolutePath, String contentType, Boolean isFile, String inputName, String name, String orginalName) {
        UploadFile uploadFile;
        uploadFile = new UploadFile();
        uploadFile.setAbsolutePath(absolutePath);
        uploadFile.setContentType(contentType);
        uploadFile.setFile(isFile);
        uploadFile.setInputName(inputName);
        uploadFile.setName(name);
        uploadFile.setOriginalName(orginalName);
        return uploadFile;
    }

}// Ende class
