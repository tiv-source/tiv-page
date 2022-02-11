package de.tivsource.page.admin.actions.others.pdf;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

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
import de.tivsource.page.entity.property.Property;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pdfAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/pdf/add_form.jsp")
  }),
  @TilesDefinition(name="pdfAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/pdf/add_error.jsp")
  })
})
public class AddAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6033997203518511979L;

	/**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name="PDFDao")
    private PDFDaoLocal pdfDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    private PDF pdf;

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }

    @Override
    public void prepare() {
        super.prepare();
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pdfAddForm"),
        				@Result(name = "error", type="tiles", location = "pdfAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(pdf != null) {
    	    pdf.setUuid(UUID.randomUUID().toString());
    	    pdf.setModified(new Date());
    	    pdf.setCreated(new Date());
    	    pdf.setModifiedBy(remoteUser);
    	    pdf.setModifiedAddress(remoteAddress);

    	    pdf.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    pdf.getDescriptionMap().get(Language.DE).setPdf(pdf);
    	    pdf.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = pdf.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    pdf.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);

            pdf.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            pdf.getDescriptionMap().get(Language.EN).setPdf(pdf);
            pdf.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            // Erstelle die PDF Datei
            pdf.generate();

            pdf.getImage().setPdf(pdf);
            pdf.getImage().setUuid(UUID.randomUUID().toString());
            pdf.getImage().generate();
            pdf.getImage().setCreated(new Date());
            pdf.getImage().setModified(new Date());
            pdf.getImage().setModifiedAddress(remoteAddress);
            pdf.getImage().setModifiedBy(remoteUser);
            
    		pdfDaoLocal.merge(pdf);
    		setLastModified();
            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<PDFType> getPDFTypeList() {
        return Arrays.asList(PDFType.values());
    }

    private void setLastModified() {
        // Lese Daten des Remote Benutzers
        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

        // Erstelle neues Aktualisierungsdatum
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String newDate = simpleDateFormat.format(new Date());

        // Hole Schlüssel mit Aktualisierungsdatum aus der Datenbank oder erstelle neues Objekt
        Property lastModified = propertyDaoLocal.findByKey("pdf.lastModified");

        if(lastModified != null) {
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        } else {
            lastModified = new Property();
            lastModified.setCreated(new Date());
            lastModified.setKey("pdf.lastModified");
            lastModified.setModified(new Date());
            lastModified.setModifiedAddress(remoteAddress);
            lastModified.setModifiedBy(remoteUser);
            lastModified.setValue(newDate);
        }
        propertyDaoLocal.merge(lastModified);

        Property imageLastModified = propertyDaoLocal.findByKey("pdf.image.lastModified");
        if(imageLastModified != null) {
            imageLastModified.setModified(new Date());
            imageLastModified.setModifiedAddress(remoteAddress);
            imageLastModified.setModifiedBy(remoteUser);
            imageLastModified.setValue(newDate);
        } else {
            imageLastModified = new Property();
            imageLastModified.setCreated(new Date());
            imageLastModified.setKey("pdf.image.lastModified");
            imageLastModified.setModified(new Date());
            imageLastModified.setModifiedAddress(remoteAddress);
            imageLastModified.setModifiedBy(remoteUser);
            imageLastModified.setValue(newDate);
        }
        propertyDaoLocal.merge(lastModified);
    }// Ende setLastModified()

}// Ende class
