package de.tivsource.page.admin.actions.system.salutation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.backup.BackupSalutation;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class BackupAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 9106555025651578404L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupAction.class);

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

    private InputStream fileStream;

    private static byte[] buffer = new byte[1024];

    @Autowired
    public BackupAction(PropertyService propertyService) {
        super(propertyService);
    }

    @Override
    @Actions({
        @Action(
            value = "backup",
            results = { @Result(
                    name = "success",
                    type="stream",
                    params={"contentType", "text/plain", "inputName", "fileStream", "contentDisposition", "attachment;filename=salutation.zip"}
            )}
        )
    })
    public String execute() throws Exception {
        LOGGER.info("execute() aufgerufen.");
        File backupFile = this.getZipFile();
        fileStream = new FileInputStream(backupFile);
        backupFile.delete();
        return SUCCESS;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    private File getZipFile() throws IOException {
        LOGGER.info("getZipFile() aufgerufen.");

        // Zip-Datei erstellen und Stream bereitstellen.
        File zipFile = File.createTempFile("salutation", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        /*
         * Backup Salutation
         */
        BackupSalutation backupArticle = new BackupSalutation(salutationDaoLocal);
        Iterator<File> salutationIterator = backupArticle.getBackupFiles().iterator();
        while(salutationIterator.hasNext()) {
            File next = salutationIterator.next();
            addData(next, outZipFile, next.getName());
        }

        // Schließe die Zip-Datei.
        outZipFile.close();

        return zipFile;
    }

    private static void addData(File file, ZipOutputStream zipOutputStream, String filename) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        // Ab hier beginnt der Exhibition Teil
        zipOutputStream.putNextEntry(new ZipEntry(filename));

        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }

        zipOutputStream.closeEntry();
        fileInputStream.close();

        // Lösche die Dateien nur wenn es nicht die Orginal Bilder sind.
        if(!file.getName().endsWith(".png")) {
            file.delete();
        }

    }
    
}// Ende class
