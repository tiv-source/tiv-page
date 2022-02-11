package de.tivsource.page.admin.actions.others.pdf;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.dao.pdf.PDFDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.enumeration.PDFType;
import de.tivsource.page.entity.pdf.PDF;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pdfEditForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/pdf/edit_form.jsp")
  }),
  @TilesDefinition(name="pdfEditError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/pdf/edit_error.jsp")
  })
})
public class EditAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8055397581589809541L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(EditAction.class);

    @InjectEJB(name="PDFDao")
    private PDFDaoLocal pdfDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private PDF pdf;

    private String lang = "DE";

    public PDF getPdf() {
        return pdf;
    }

	public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }
	
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    @Actions({
        @Action(
        		value = "edit", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input",   type = "tiles", location = "pdfEditForm"),
        				@Result(name = "error",   type = "tiles", location = "pdfEditError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(pdf != null) {
    		LOGGER.info("PDF UUID: " + pdf.getUuid());
    		PDF dbPdf = pdfDaoLocal.findByUuid(pdf.getUuid());

            if(lang.contentEquals(new StringBuffer("EN"))) {
                pdf.getDescriptionMap().put(Language.DE, dbPdf.getDescriptionObject(Language.DE));
                String noLineBreaks = pdf.getDescription(Language.EN).replaceAll("(\\r|\\n)", "");
                dbPdf.getDescriptionMap().get(Language.EN).setDescription(noLineBreaks);
                dbPdf.getDescriptionMap().get(Language.EN).setKeywords(pdf.getKeywords(Language.EN));
                dbPdf.getDescriptionMap().get(Language.EN).setName(pdf.getName(Language.EN));
            } else {
                String noLineBreaks = pdf.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
                dbPdf.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
                dbPdf.getDescriptionMap().get(Language.DE).setKeywords(pdf.getKeywords(Language.DE));;
                dbPdf.getDescriptionMap().get(Language.DE).setName(pdf.getName(Language.DE));
            }

            dbPdf.setDateOfPublication(pdf.getDateOfPublication());
            dbPdf.setPdfType(pdf.getPdfType());
            dbPdf.setOrderNumber(pdf.getOrderNumber());
            dbPdf.setVisible(pdf.getVisible());
            dbPdf.setEdition(pdf.getEdition());
    		dbPdf.setModified(new Date());
    		dbPdf.setModifiedBy(remoteUser);
    		dbPdf.setModifiedAddress(remoteAddress);
    		pdfDaoLocal.merge(dbPdf);
            return SUCCESS;
    	}
    	return ERROR;

    }// Ende execute()

    public List<PDFType> getPDFTypeList() {
        return Arrays.asList(PDFType.values());
    }

}// Ende class
