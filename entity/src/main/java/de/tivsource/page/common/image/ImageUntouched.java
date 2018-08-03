package de.tivsource.page.common.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;
import org.hibernate.annotations.Type;

import de.tivsource.page.common.file.FileActions;

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
     * Lokaler Pfad der Orginal-Datei.
     */
    private String orginal;

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
    @Type(type = "yes_no")
    private Boolean standard;

    /**
     * Datei, die aus einem Formular hochgeladen wird.
     */
    @Transient
    private File uploadFile;

    public ImageUntouched() {
        super();
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
     * @return the orginal
     */
    public String getOrginal() {
        return orginal;
    }

    /**
     * @param orginal the orginal to set
     */
    public void setOrginal(String orginal) {
        this.orginal = orginal;
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

    public FileInputStream getOrginalFileInputStream() {
        try {
            return new FileInputStream(new File(this.orginal));
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
    public File getUploadFile() {
        return uploadFile;
    }

    /**
     * @param uploadFile
     *            the uploadFile to set
     */
    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void generate() {
        String filePath = "/srv/tiv-page/upload/";

        // Generiere UUID für den Dateinamen
        String pictureSaveName = UUID.randomUUID().toString();

        try {
            // Datei die erstellt werden soll
            File fileToCreate = new File(filePath, pictureSaveName + ".org.png");
            logger.debug("Absoluter Pfad der neuen Picture-Datei : "
                    + fileToCreate.getAbsolutePath());

            // Wenn die Datei noch nicht existiert wird Sie erstellt.
            if (!fileToCreate.exists()) {
                FileActions.savePictureFile(this.getUploadFile(), fileToCreate);
            }// Ende if
        } // Ende try
        catch (Exception e) {
            logger.error(e.getMessage());
        }// Ende Catch

        String s = null;

        String file = filePath + pictureSaveName + ".org.png";
        String largeFile = filePath + pictureSaveName + ".large.png";
        String normalFile = filePath + pictureSaveName + ".normal.png";
        String smallFile = filePath + pictureSaveName + ".small.png";
        String thumbFile = filePath + pictureSaveName + ".thumb.png";
        String microFile = filePath + pictureSaveName + ".micro.png";
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

        this.setOrginal(file);
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

        File orginalFile = new File(orginal);
        if (orginalFile.exists()) {
            FileUtils.delete(orginalFile);
        }// Ende if
    }// Ende delete()
    
    public int compareTo(ImageUntouched image) {
        logger.trace("compareTo() aufgerufen.");
        return image.getMicro().compareTo(this.getMicro()) * -1;
    }

}// Ende class
