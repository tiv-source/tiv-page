package de.tivsource.page.rewriteobject;

import de.tivsource.page.common.file.UploadFile;

/**
 * Klasse um eine Objekt vom Typ UploadedFile in eine Objekt 
 * vom Typ UploadFile umzuschreiben. Es werden keine Checks 
 * vorgenommen.
 *  
 * @author Marc Michele
 *
 */
public class URIToUploadFile {

    public static UploadFile convert(String absolutePath, 
            String contentType, Boolean file, 
            String inputName, String name, 
            String originalName) {
        UploadFile uploadFile;
        uploadFile = new UploadFile();
        uploadFile.setAbsolutePath(absolutePath);
        uploadFile.setContentType(contentType);
        uploadFile.setFile(file);
        uploadFile.setInputName(inputName);
        uploadFile.setName(name);
        uploadFile.setOriginalName(originalName);
        return uploadFile;
    }

}// Ende class
