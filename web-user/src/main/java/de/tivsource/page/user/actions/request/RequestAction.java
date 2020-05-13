package de.tivsource.page.user.actions.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.dao.request.RequestDaoLocal;
import de.tivsource.page.entity.location.Location;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.entity.request.Reason;
import de.tivsource.page.entity.request.Request;
import de.tivsource.page.helper.EmailSender;
import de.tivsource.page.helper.EmailTemplate;
import de.tivsource.page.user.actions.EmptyAction;

/**
 * 
 * @author Marc Michele
 *
 */
@InterceptorRef(value="uploadStack")
@TilesDefinitions({
  @TilesDefinition(name="request", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/request/form.jsp")
  }),
  @TilesDefinition(name="page", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/content.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/page.jsp")
  })
})
public class RequestAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -899397629172153047L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(RequestAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="RequestDao")
    private RequestDaoLocal requestDaoLocal;

    @InjectEJB(name="LocationDao")
    private LocationDaoLocal locationDaoLocal;

    @InjectEJB(name="ReasonDao")
    private ReasonDaoLocal reasonDaoLocal;

	private Request requestInput;

	private Page page;

    private Captcha captcha;

    private String answer;

    @Actions({
        @Action(
        		value = "sent", 
        		results = { 
        				@Result(name = "success", type="tiles", location = "page"), 
        				@Result(name = "input", type="tiles", location = "request")
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");
        LOGGER.info("Anrede: " + requestInput.getGender());

        // Hole Action Locale
    	this.getLanguageFromActionContext();


        if(answer != null && captcha != null && !answer.trim().equals("") && captcha.getContent().equals(answer)) {
            //sendMail();
            // Speichere Message Objekt
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            requestInput.setCreated(new Date());
            requestInput.setCreatedAddress(remoteAddress);
            requestInput.setUuid(UUID.randomUUID().toString());
            requestInput.generate();
            requestDaoLocal.merge(requestInput);

            return SUCCESS;            
        } else {
            captcha = captchaDaoLocal.random();
            addFieldError("answer", "Bitte geben Sie die Hausnummer ein.");
            return INPUT;
        }
	}

    @Override
    public Page getPage() {
        if(page == null) {
            setUpPage();
        }
        return page;
    }// Ende getPage()

    /**
     * @return the requestInput
     */
    public Request getRequestInput() {
        return requestInput;
    }

    /**
     * @param requestInput the requestInput to set
     */
    public void setRequestInput(Request requestInput) {
        this.requestInput = requestInput;
    }

    public List<Location> getLocationList() {
        return locationDaoLocal.findAllVisible(0, locationDaoLocal.countAllVisible());
    }

    public List<Reason> getReasonList() {
        return reasonDaoLocal.findAllVisible(0, reasonDaoLocal.countAllVisible());
    }

    /**
     * @return the captcha
     */
    public Captcha getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private void sendMail() {
		LOGGER.info("sendMail() aufgerufen.");
		
		javax.mail.Authenticator auth = new javax.mail.Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
				        propertyDaoLocal.findByKey("mail.user").getValue(),
				        propertyDaoLocal.findByKey("mail.password").getValue());
			}
		};
		
		InputStream template;
		try {
		    URL templatePath = new URL(propertyDaoLocal.findByKey("request.template.path").getValue());
			LOGGER.info("Pfad zur template Datei: " + templatePath);
            template = templatePath.openStream();
            LOGGER.info("Template eingelesen");
            Object notification = EmailTemplate.getEmailTemplate(template);
            LOGGER.info("Template eingelesen");

            // Get session
            Session session = Session.getInstance(getProperties(), auth);
            session.setDebug(false);
            
            EmailSender sendIt = new EmailSender();
            String[] argu = {
                    requestInput.getGender() == 'F' ? "Frau" :
                        requestInput.getGender() == 'M' ? "Herr" :
                            "",
                    requestInput.getFirstname(), 
                    requestInput.getLastname(), 
                    requestInput.getMail(),
                    requestInput.getCreatedAddress(),
                    requestInput.getBirthday().toString(),
                    requestInput.getCreated().toString(),
                    requestInput.getLocation().getName("DE"),
                    requestInput.getReason().getName("DE"),
                    requestInput.getComment(),
                    requestInput.getComment().replace("\n", "<br/>")
            		};

            new Thread(new Runnable() {
                public void run(){
                    try {
                        sendIt.send("Kartenantrag", (EmailTemplate)notification, argu, session);
                    } catch (UnsupportedEncodingException | MessagingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return; // to stop the thread
                }
            }).start();

		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}// Ende sendMail()

    private Properties getProperties() {
        LOGGER.info("getProperties() aufgerufen.");

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.transport.protocol", 
                propertyDaoLocal.findByKey("mail.transport.protocol").getValue());
        props.put("mail.host", 
                propertyDaoLocal.findByKey("mail.host").getValue());
        props.put("mail.port", 
                propertyDaoLocal.findByKey("mail.port").getValue());
        props.put("mail.smtp.auth", 
                propertyDaoLocal.findByKey("mail.smtp.auth").getValue());
        props.put("mail.smtp.tls", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
        props.put("mail.smtp.starttls.enable",
                propertyDaoLocal.findByKey("mail.smtp.starttls.enable").getValue());
        props.put("mail.smtp.localhost", 
                propertyDaoLocal.findByKey("mail.smtp.localhost").getValue());
        props.put("mail.user", 
                propertyDaoLocal.findByKey("mail.user").getValue());
        props.put("mail.password", 
                propertyDaoLocal.findByKey("mail.password").getValue());
        props.put("mail.mime.charset", 
                propertyDaoLocal.findByKey("mail.mime.charset").getValue());
        props.put("mail.use8bit", 
                propertyDaoLocal.findByKey("mail.use8bit").getValue());
        
        return props;
    } // Ende getProperties()

    private void setUpPage() {
        LOGGER.info("Action Errors: " + this.getFieldErrors().size());
        if(this.getFieldErrors().size() > 0) {
            Set<Map.Entry<String,List<String>>> errorSet = this.getFieldErrors().entrySet();
            Iterator<Map.Entry<String,List<String>>> setIterator = errorSet.iterator();
            while(setIterator.hasNext()) {
                Map.Entry<String,List<String>> next = setIterator.next();
                LOGGER.info("Action Error Key: " + next.getKey());
            }
            page = pageDaoLocal.findByTechnical("request");
        } else {
            page = pageDaoLocal.findByTechnical("requestsent");
        }
    }
    
}// Ende class
