package de.tivsource.page.user.actions.lecturer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.lecturer.Lecturer;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="lecturer", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/lecturer/lecturer.jsp")
  })
})
public class IndexAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3175566381397319895L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(IndexAction.class);

    @Autowired
    @Qualifier("pageDaoLocal")
    private PageDaoLocal pageDaoLocal;

    @Autowired
    @Qualifier("propertyDaoLocal")
    private PropertyDaoLocal propertyDaoLocal;

    @Autowired
    @Qualifier("lecturerDaoLocal")
    private LecturerDaoLocal lecturerDaoLocal;

    private Page page;

    private List<Lecturer> list;

    private Lecturer lecturer;
    
    @Override
    public void prepare() {
        // Lade die Filialübersichtsseite aus der Datenbank
        page = pageDaoLocal.findByTechnical("lecturer");
        list = lecturerDaoLocal.findAllVisible(0, lecturerDaoLocal.countAllVisible());
    }

    @Override
    @Actions({
        @Action(
        		value = "index", 
        		results = {
        		  @Result(name = "success", type="tiles", location = "lecturer"),
                  @Result(name = "lecturer", type = "redirectAction", location = "index.html", params={"namespace", "/lecturer/%{lecturer.technical}"}),
        		  @Result(name = "error", type = "redirectAction", location = "index.html", params={"namespace", "/"})
        		}
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        // Hole Eigenschaft aus der Datenbank
        boolean moduleEnabled = propertyDaoLocal.findByKey("module.lecturer").getValue().equals("true") ? true : false;

        // Prüfe ob das Module aktiviert ist
        if(moduleEnabled && list.size() > 0) {
            lecturer = list.get(0);
            if(list.size() > 1) {
                // Hole Action Locale
                this.getLanguageFromActionContext();
                return SUCCESS;
            }
            return "lecturer";
        }
        // Wennn das Modul nicht aktiviert ist.
        return ERROR;

    }// Ende execute()

    public Page getPage() {
    	return page;
    }// Ende getPage()

    public List<Lecturer> getList() {
        return list;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

}// Ende class