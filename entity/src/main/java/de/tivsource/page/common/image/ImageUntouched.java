package de.tivsource.page.common.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;

import de.tivsource.page.common.file.FileActions;
import de.tivsource.page.common.file.UploadFile;
import jakarta.persistence.Basic;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.Transient;

/**
 * Die Klasse Image dient dazu Bilder zu verwalten
 *
 * @author Marc Michele
 *
 */
@MappedSuperclass
public class ImageUntouched implements Comparable<ImageUntouched>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7403879687974877269L;

    /**
     * Statischer Logger der Klasse Image für die Ausgabe mit log4j. Es gibt nur
     * Ausgaben mit dem Log-Level "TRACE".
     */
    private static final Logger logger = LogManager.getLogger(ImageUntouched.class);

    /**
     * Lokaler Dateisystem Pfad zu den hochgeladenen Bildern.
     */
    @Transient
    protected String uploadPath = "/srv/tiv-page/upload/";

    /**
     * Lokaler Pfad der Orginal-Datei.
     */
    private String original;

    /**
     * Lokaler Pfad der kleinen Abbildung mit 1000 x 1000 Pixeln.
     */
    private String large;

    /**
     * Lokaler Pfad der normalen Abbildung mit 650 x 650 Pixeln.
     */
    private String normal;
    
    /**
     * Lokaler Pfad der kleinen Abbildung mit 350 x 350 Pixeln.
     */
    private String small;

    /**
     * Lokaler Pfad der Abbildung für Übersichtsseiten mit 148 x 148 Pixeln.
     */
    private String thumbnail;

    /**
     * Lokaler Pfad der kleinen Abbildung mit 50 x 50 Pixeln.
     */
    private String micro;

    /**
     * Boolean ob das Bild ein Default Bild ist (Ja/Nein).
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean standard;

    /**
     * Datei, die aus einem Formular hochgeladen wird.
     */
    @Transient
    private UploadFile uploadFile;

    @Transient
    private String uploadFileContentType;

    @Transient
    private String uploadFileFileName;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    private String modifiedBy;

    private String modifiedAddress;

    public ImageUntouched() {
        super();
    }

    /**
     * @return the orginal
     */
    public String getOriginal() {
        return original;
    }

    /**
     * @param original the orginal to set
     */
    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMicro() {
        return micro;
    }

    public void setMicro(String micro) {
        this.micro = micro;
    }

    public Boolean isStandard() {
        return standard;
    }

    public void setStandard(Boolean standard) {
        this.standard = standard;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedAddress
     */
    public String getModifiedAddress() {
        return modifiedAddress;
    }

    /**
     * @param modifiedAddress the modifiedAddress to set
     */
    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    public FileInputStream getOriginalFileInputStream() {
        try {
            return new FileInputStream(new File(this.original));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileInputStream getLargeFileInputStream() {
        try {
            return new FileInputStream(new File(this.large));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileInputStream getNormalFileInputStream() {
        try {
            return new FileInputStream(new File(this.normal));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileInputStream getSmallFileInputStream() {
        try {
            return new FileInputStream(new File(this.small));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileInputStream getThumbnailFileInputStream() {
        try {
            return new FileInputStream(new File(this.thumbnail));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileInputStream getMicroFileInputStream() {
        try {
            return new FileInputStream(new File(this.micro));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return the uploadFile
     */
    public UploadFile getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadedFile
     *            the uploadFile to set
     */
    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFileContentType = uploadFile.getContentType();
        this.uploadFileFileName = uploadFile.getInputName();
        this.uploadFile = uploadFile;
    }

    /**
     * @return the uploadFileContentType
     */
    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    /**
     * @return the uploadFileFileName
     */
    public String getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void generate() {

        // Generiere UUID für den Dateinamen
        String pictureSaveName = UUID.randomUUID().toString();

        try {
            // Datei die erstellt werden soll
            File fileToCreate = new File(uploadPath, pictureSaveName + ".org.png");
            logger.debug("Absoluter Pfad der neuen Picture-Datei : "
                    + fileToCreate.getAbsolutePath());

            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fileToCreate.exists()) {
                FileActions.savePictureFile(new File(uploadFile.getAbsolutePath()), fileToCreate);
            }// Ende if
        } // Ende try
        catch (Exception e) {
            logger.error(e.getMessage());
        }// Ende Catch

        String s = null;

        String file = uploadPath + pictureSaveName + ".org.png";
        String largeFile = uploadPath + pictureSaveName + ".large.png";
        String normalFile = uploadPath + pictureSaveName + ".normal.png";
        String smallFile = uploadPath + pictureSaveName + ".small.png";
        String thumbFile = uploadPath + pictureSaveName + ".thumb.png";
        String microFile = uploadPath + pictureSaveName + ".micro.png";
        try {

            Process process = Runtime.getRuntime().exec(
                    "/usr/bin/convert -resize 1000x1000 -quality 85 " + file + " "+ largeFile);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            process = Runtime.getRuntime().exec(
                    "/usr/bin/convert -resize 650x650 -quality 85 " + file + " "+ normalFile);
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            process = Runtime.getRuntime().exec(
                    "/usr/bin/convert -resize 350x350 -quality 85 " + file + " "+ smallFile);
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            process = Runtime.getRuntime().exec(
                    "/usr/bin/convert -resize 148x148 -quality 85 " + file + " " + thumbFile);
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            process = Runtime.getRuntime().exec(
                    "/usr/bin/convert -resize 50x50 -quality 85 " + file + " " + microFile);
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }

        this.setOriginal(file);
        this.setLarge(largeFile);
        this.setNormal(normalFile);
        this.setSmall(smallFile);
        this.setThumbnail(thumbFile);
        this.setMicro(microFile);
        this.setStandard(true);
    }

    public void delete() {
        File microFile = new File(micro);
        if (microFile.exists()) {
            FileUtils.delete(microFile);
        }// Ende if

        File thumbnailFile = new File(thumbnail);
        if (thumbnailFile.exists()) {
            FileUtils.delete(thumbnailFile);
        }// Ende if

        File smallFile = new File(small);
        if (smallFile.exists()) {
            FileUtils.delete(smallFile);
        }// Ende if

        File normalFile = new File(normal);
        if (normalFile.exists()) {
            FileUtils.delete(normalFile);
        }// Ende if

        File largeFile = new File(large);
        if (largeFile.exists()) {
            FileUtils.delete(largeFile);
        }// Ende if

        File originalFile = new File(original);
        if (originalFile.exists()) {
            FileUtils.delete(originalFile);
        }// Ende if
    }// Ende delete()
    
    public int compareTo(ImageUntouched image) {
        logger.trace("compareTo() aufgerufen.");
        return image.getMicro().compareTo(this.getMicro()) * -1;
    }

}// Ende class
