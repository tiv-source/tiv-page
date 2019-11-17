package de.tivsource.page.admin.actions.maintenance.cssfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.backup.BackupCSSFile;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;

/**
 * 
 * @author Marc Michele
 *
 */
public class BackupAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4095835501014581918L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(BackupAction.class);

    @InjectEJB(name="CSSFileDao")
    private CSSFileDaoLocal cssFileDaoLocal;

    private InputStream fileStream;

    private static byte[] buffer = new byte[1024];

    @Override
    @Actions({
        @Action(
            value = "backup",
            results = { @Result(
                    name = "success",
                    type="stream",
                    params={"contentType", "text/plain", "inputName", "fileStream", "contentDisposition", "attachment;filename=cssFile.zip"}
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
        File zipFile = File.createTempFile("cssFile", "zip");
        ZipOutputStream outZipFile = new ZipOutputStream(new FileOutputStream(zipFile));

        /*
         * Backup Gallery
         */
        BackupCSSFile backupCSSFile = new BackupCSSFile(cssFileDaoLocal);
        addData(backupCSSFile.getBackupFile(), outZipFile, "cssFile.csv");

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
        file.delete();
    }
    
}// Ende class
