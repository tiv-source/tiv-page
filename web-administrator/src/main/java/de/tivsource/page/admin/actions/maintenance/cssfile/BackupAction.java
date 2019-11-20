package de.tivsource.page.admin.actions.maintenance.cssfile;

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

        BackupCSSFile backupCSSFile = new BackupCSSFile(cssFileDaoLocal);
        Iterator<File> cssFileIterator = backupCSSFile.getBackupFiles().iterator();
        while(cssFileIterator.hasNext()) {
            File next = cssFileIterator.next();
            addData(next, outZipFile, next.getName());
        }

        // Schließe die Zip-Datei.
        outZipFile.close();

        return zipFile;
    }

    private static void addData(File file, ZipOutputStream zipOutputStream, String filename) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        // Erstelle neuen ZIP Datei Eintrag
        zipOutputStream.putNextEntry(new ZipEntry(filename));

        // Transferiere bytes von der Datei in die ZIP Datei
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }

        zipOutputStream.closeEntry();
        fileInputStream.close();

        // Lösche die Dateien nur wenn es nicht die Orginal Bilder sind.
        if(!file.getName().endsWith(".css")) {
            file.delete();
        }
    }
    
}// Ende class
