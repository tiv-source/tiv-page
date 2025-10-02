package de.tivsource.page.admin.actions.others.pdf;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.pdf.PDFDaoLocal;
import de.tivsource.page.entity.pdf.PDF;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
public class PdfEditAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4914014195306827435L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(PdfEditAction.class);

    @InjectEJB(name="PDFDao")
    private PDFDaoLocal pdfDaoLocal;

    private PDF pdf;

    @StrutsParameter(depth=3)
    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    @Override
    @Actions({
        @Action(
        		value = "pdf", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "editForm.html", params = {"uncheckPdf", "%{pdf.uuid}"}),
        				@Result(name = "input",   type = "tiles", location = "imageForm"),
        				@Result(name = "error",   type = "tiles", location = "imageEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(pdf != null) {
    		LOGGER.info("UUID des PDF Objektes: " + pdf.getUuid());
    		PDF dbPdf = pdfDaoLocal.findByUuid(pdf.getUuid());

    		dbPdf.setModified(new Date());
    		dbPdf.setModifiedBy(remoteUser);
    		dbPdf.setModifiedAddress(remoteAddress);

    		dbPdf.setUploadFile(this.pdf.getUploadFile());
    		dbPdf.generate();

    		// TODO: Es werden keine Dateien gelöscht entwerde muss das noch hier hin oder es muss eine Aufräumfunktion geben.
            pdfDaoLocal.merge(dbPdf);
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}

    }// Ende execute()

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("uploadedFiles ist nicht leer.");
            UploadedFile uploadedFile = uploadedFiles.get(0);
            this.pdf = new PDF();
            this.pdf.setUploadFile(UploadedFileToUploadFile.convert(uploadedFile));
         }
    }

}// Ende class