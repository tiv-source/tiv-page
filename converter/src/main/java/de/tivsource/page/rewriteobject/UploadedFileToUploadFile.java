package de.tivsource.page.rewriteobject;

import org.apache.struts2.dispatcher.multipart.UploadedFile;

import de.tivsource.page.common.file.UploadFile;

/**
 * Klasse um eine Objekt vom Typ UploadedFile in eine Objekt 
 * vom Typ UploadFile umzuschreiben. Es werden keine Checks 
 * vorgenommen.
 *  
 * @author Marc Michele
 *
 */
public class UploadedFileToUploadFile {

    public static UploadFile convert(UploadedFile uploadedFile) {
        UploadFile uploadFile;
        uploadFile = new UploadFile();
        uploadFile.setAbsolutePath(uploadedFile.getAbsolutePath());
        uploadFile.setContentType(uploadedFile.getContentType());
        uploadFile.setFile(uploadedFile.isFile());
        uploadFile.setInputName(uploadedFile.getInputName());
        uploadFile.setName(uploadedFile.getName());
        uploadFile.setOriginalName(uploadedFile.getOriginalName());
        return uploadFile;
    }

}// Ende class
