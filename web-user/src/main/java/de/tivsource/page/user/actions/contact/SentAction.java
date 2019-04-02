package de.tivsource.page.user.actions.contact;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.message.Message;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.helper.EmailSender;
import de.tivsource.page.helper.EmailTemplate;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="contactForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/contact/contact_form.jsp")
  }),
  @TilesDefinition(name="page", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/content.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/page.jsp")
  })
})
public class SentAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -899397629172153047L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(SentAction.class);

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;
    
	private Message message;

	private Page page;

	public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Actions({
        @Action(
        		value = "sent", 
        		results = { 
        				@Result(name = "success", type="tiles", location = "page"), 
        				@Result(name = "input", type="tiles", location = "contactForm")
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	// Sende Mail
    	sendMail();
    	
    	// Speichere Message Objekt
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        message.setCreated(new Date());
        message.setCreatedAddress(remoteAddress);
        message.setUuid(UUID.randomUUID().toString());
        messageDaoLocal.merge(message);
        
		return SUCCESS;
	}

    @Override
    public Page getPage() {
        if(page == null) {
            setUpPage();
        }
        return page;
    }// Ende getPage()

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
		    URL templatePath = new URL(propertyDaoLocal.findByKey("mail.template.path").getValue());
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
                    message.getGender() ? "Frau" : "Herr",
            		message.getFirstname(), 
            		message.getLastname(), 
            		message.getMail(), 
            		message.getTelephone(),
            		message.getFax(),
            		message.getContent(),
            		message.getContent().replace("\n", "<br/>")
            		};

            new Thread(new Runnable() {
                public void run(){
                    try {
                        sendIt.send("Kontaktformular", (EmailTemplate)notification, argu, session);
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
            page = pageDaoLocal.findByTechnical("contact");
        } else {
            page = pageDaoLocal.findByTechnical("sent");
        }
    }
    
}// Ende class
