package de.tivsource.page.user.actions.newsletter.lockrequest;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

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
import de.tivsource.page.dao.mail.MailAddressLockDaoLocal;
import de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.mail.MailAddressLock;
import de.tivsource.page.entity.mail.MailAddressLockRequest;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="page", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/content.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/page.jsp")
  })
})
public class ConfirmAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -8685932196334893410L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(ConfirmAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="MailAddressLockDao")
    private MailAddressLockDaoLocal mailAddressLockDaoLocal;

    @InjectEJB(name="MailAddressLockRequestDao")
    private MailAddressLockRequestDaoLocal mailAddressLockRequestDaoLocal;

	private String lockRequestUuid;

	private Page page;

    @Actions({
        @Action(
            value = "*/confirm",
            results = {
                @Result(name = "success",             type="tiles", location = "page"),
                @Result(name = "input",               type="tiles", location = "page")
            }
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	lockRequestUuid = ServletActionContext.getRequest().getServletPath();
        LOGGER.info("LockRequest Uuid: " + lockRequestUuid);

        lockRequestUuid = lockRequestUuid.replaceAll("/confirm.html", "");
        lockRequestUuid = lockRequestUuid.replaceAll("/newsletter/lockrequest/", "");

        LOGGER.info("LockRequest Uuid: " + lockRequestUuid);

    	// Überprüfen ob die angegebene UUID 
    	if(isValid(lockRequestUuid) && lockRequestUuid.length() > 35 && lockRequestUuid.length() < 61) {

    	    MailAddressLockRequest mailAddressLockRequest = mailAddressLockRequestDaoLocal.findByUuid(lockRequestUuid);
    	    
    	    if(!mailAddressLockRequest.getUsed()) {
                mailAddressLockRequest.setUsed(true);
                mailAddressLockRequestDaoLocal.merge(mailAddressLockRequest);

                MailAddressLock mailAddressLock = convert(mailAddressLockRequest);
                mailAddressLockDaoLocal.merge(mailAddressLock);

                page = pageDaoLocal.findByTechnical("lockrequestconfirm");

                return SUCCESS;            
    	    }
        }
    	page = pageDaoLocal.findByTechnical("lockrequesterror");
    	return INPUT;
	}// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }// Ende getPage()

    private Boolean isValid(String input) {
        if (Pattern.matches("[abcdef0-9-]*", input)) {
            return true;
        }
        return false;
    }// Ende isValid(String input)

    private MailAddressLock convert(MailAddressLockRequest mailAddressLockRequest) {
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        String remoteUser = ServletActionContext.getRequest().getRemoteUser();
        MailAddressLock mailAddressLock = new MailAddressLock();
        mailAddressLock.setCreated(new Date());
        mailAddressLock.setCreatedAddress(remoteAddress);
        mailAddressLock.setCreatedBy(remoteUser != null ? remoteUser : "Internet-Seite");
        mailAddressLock.setHash(mailAddressLockRequest.getHash());
        mailAddressLock.setUuid(UUID.randomUUID().toString());
        return mailAddressLock;
    }// Ende convert(MailAddressLockRequest mailAddressLockRequest)

}// Ende class
