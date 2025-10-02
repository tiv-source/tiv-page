package de.tivsource.page.admin.actions.others.page;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.UploadedFilesAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.common.css.CSSGroup;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.pictureitem.PictureItemImage;
import de.tivsource.page.rewriteobject.UploadedFileToUploadFile;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="pageAddForm",  extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/chosen.jsp"),
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/add_form.jsp")
  }),
  @TilesDefinition(name="pageAddError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/others.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/add_error.jsp")
  })
})
public class AddAction extends EmptyAction implements UploadedFilesAware {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = -5428084532283133908L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(AddAction.class);

    @InjectEJB(name = "CSSGroupDao")
    private CSSGroupDaoLocal cssGroupDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;
    
    private Page page;
    
    private List<CSSGroup> cssGroupList;

    @StrutsParameter(depth=3)
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public void prepare() {
        super.prepare();
        cssGroupList = cssGroupDaoLocal.findAll(0, cssGroupDaoLocal.countAll());
    }

	@Override
    @Actions({
        @Action(
        		value = "add", 
        		results = { 
        				@Result(name = "success", type = "redirectAction", location = "index.html"),
        				@Result(name = "input", type="tiles", location = "pageAddForm"),
        				@Result(name = "error", type="tiles", location = "pageAddError")
        				}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();

    	if(page != null) {
    	    page.setUuid(UUID.randomUUID().toString());
    	    page.setModified(new Date());
    	    page.setCreated(new Date());
    	    page.setModifiedBy(remoteUser);
    	    page.setModifiedAddress(remoteAddress);

    	    page.getDescriptionMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getDescriptionMap().get(Language.DE).setNamingItem(page);
    	    page.getDescriptionMap().get(Language.DE).setLanguage(Language.DE);
    	    String noLineBreaks = page.getDescription(Language.DE).replaceAll("(\\r|\\n)", "");
    	    page.getDescriptionMap().get(Language.DE).setDescription(noLineBreaks);
    	    
    	    page.getContentMap().get(Language.DE).setUuid(UUID.randomUUID().toString());
    	    page.getContentMap().get(Language.DE).setContentItem(page);
    	    page.getContentMap().get(Language.DE).setLanguage(Language.DE);
    	    page.getContentMap().get(Language.DE).setCreated(new Date());
    	    page.getContentMap().get(Language.DE).setModified(new Date());


            page.getDescriptionMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            page.getDescriptionMap().get(Language.EN).setNamingItem(page);
            page.getDescriptionMap().get(Language.EN).setLanguage(Language.EN);

            page.getContentMap().get(Language.EN).setUuid(UUID.randomUUID().toString());
            page.getContentMap().get(Language.EN).setContentItem(page);
            page.getContentMap().get(Language.EN).setLanguage(Language.EN);
            page.getContentMap().get(Language.EN).setCreated(new Date());
            page.getContentMap().get(Language.EN).setModified(new Date());

            page.getImage().setUuid(UUID.randomUUID().toString());
            page.getImage().generate();
            page.getImage().setCreated(new Date());
            page.getImage().setModified(new Date());
            page.getImage().setModifiedAddress(remoteAddress);
            page.getImage().setModifiedBy(remoteUser);

    		pageDaoLocal.merge(page);

            return SUCCESS;
    	}
    	else {
    		return ERROR;
    	}
    	
    	
    }// Ende execute()

    public List<CSSGroup> getCssGroupList() {
        LOGGER.info("getCssGroupList() aufgerufen.");
        LOGGER.info("Anzahl der CSS-Gruppen in der Liste: " + cssGroupList.size());
        return cssGroupList;
    }

    @Override
    public void withUploadedFiles(List<UploadedFile> uploadedFiles) {
        LOGGER.info("withUploadedFiles(List<UploadedFile> uploadedFiles) aufgerufen.");
        if (!uploadedFiles.isEmpty()) {
            LOGGER.info("Variable uploadedFiles ist nicht leer.");
            Iterator<UploadedFile> ufIterator = uploadedFiles.iterator();
            while(ufIterator.hasNext()) {
                UploadedFile next = ufIterator.next();
                LOGGER.info("UploadedFile für Input-Name: " + next.getInputName() + " gefunden.");
                if(next.getInputName().equalsIgnoreCase("page.image")) {
                    this.page = new Page();
                    this.page.setImage(new PictureItemImage());
                    this.page.getImage().setPictureItem(this.page);
                    this.page.getImage().setUploadFile(UploadedFileToUploadFile.convert(next));
                }                
            }// Ende while()
         }
    }// Ende withUploadedFiles(List<UploadedFile> uploadedFiles)

}// Ende class
