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
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.message.Message;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.helper.EmailSender;
import de.tivsource.page.helper.EmailTemplate;
import de.tivsource.page.user.actions.EmptyAction;

public class SentAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -899397629172153047L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = Logger.getLogger(SentAction.class);

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

			URL templatePath = this.getClass().getClassLoader().getResource("template_mail.xml");
			LOGGER.info("Pfad zur template Datei: " + templatePath);
            template = templatePath.openStream();
            LOGGER.info("Template eingelesen");
            Object notification = EmailTemplate.getEmailTemplate(template);
            LOGGER.info("Template eingelesen");

            // Get session
            Session session = Session.getInstance(getProperties(), auth);
            session.setDebug(true);
            
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
            sendIt.send("Zur Zeit nicht genutzt", (EmailTemplate)notification, argu, session);
		} catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
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
        props.put("mail.smtp.auth", 
                propertyDaoLocal.findByKey("mail.smtp.auth").getValue());
        props.put("mail.smtp.tls", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
        props.put("mail.smtp.localhost", 
                propertyDaoLocal.findByKey("mail.smtp.tls").getValue());
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
