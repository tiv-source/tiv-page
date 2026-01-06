package de.tivsource.page.user.actions.contact;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import org.apache.struts2.tiles.annotation.TilesDefinition;
import org.apache.struts2.tiles.annotation.TilesDefinitions;
import org.apache.struts2.tiles.annotation.TilesPutAttribute;

import de.tivsource.ejb3plugin.InjectEJB;
import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.message.Message;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.exceptions.NoMailSessionCreatedException;
import de.tivsource.page.helper.MailSender;
import de.tivsource.page.helper.MailTemplate;
import de.tivsource.page.user.actions.EmptyAction;
import jakarta.mail.MessagingException;

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

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="MessageDao")
    private MessageDaoLocal messageDaoLocal;
    
	private Message message;

	private Page page;

    private Captcha captcha;

    private String answer;

    @Actions({
        @Action(
        		value = "sent", 
        		results = { 
        				@Result(name = "success", type="tiles", location = "page"), 
        				@Result(name = "input", type="tiles", location = "contactForm"),
                        @Result(name = "couldNotSendMail",    type="tiles", location = "page")
        				}
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();


        if(answer != null && captcha != null && !answer.trim().equals("") && captcha.getContent().equals(answer)) {
            // Versuche die Mail zu versenden, wenn das nicht funktioniert
            // leite den Benutzer auf eine Fehlerseite
            try {
                sendMail();
            } catch (MessagingException | IOException e) {
                page = pageDaoLocal.findByTechnical("couldnotsendmail");
                LOGGER.debug(e.getMessage());
                return "couldNotSendMail";
            }

            // Speichere Message Objekt
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            message.setCreated(new Date());
            message.setCreatedAddress(remoteAddress);
            message.setUuid(UUID.randomUUID().toString());
            messageDaoLocal.merge(message);

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

    @StrutsParameter(depth=1)
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
    @StrutsParameter
    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    /**
     * @param answer the answer to set
     */
    @StrutsParameter
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private void sendMail() throws UnsupportedEncodingException, IOException, MessagingException {
        LOGGER.info("sendMail() aufgerufen.");
        MailSender sendIt = new MailSender(getProperties(), createMailTemplate());
        Boolean debugMailSession = getProperty("mail.contactform.debug.session").equals("true") ? true : false;
        sendIt.createSession(debugMailSession);
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
                    sendIt.send(argu);
                } catch (NoMailSessionCreatedException | MessagingException | UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return; // to stop the thread
            }
        }).start();

		/*
		jakarta.mail.Authenticator auth = new jakarta.mail.Authenticator() {
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
		 */
		
	}// Ende sendMail()

    private void setUpPage() {
        LOGGER.info("Action Errors: " + this.getFieldErrors().size());
        if(this.getFieldErrors().size() > 0) {
            page = pageDaoLocal.findByTechnical("contact");
        } else {
            page = pageDaoLocal.findByTechnical("sent");
        }
    }

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
        //TODO: Reihenfolge aufräumen
        props.put("mail.smtp.ssl.protocols", 
                propertyDaoLocal.findByKey("mail.smtp.ssl.protocols").getValue());
        props.put("mail.smtp.ssl.trust", 
                propertyDaoLocal.findByKey("mail.smtp.ssl.trust").getValue());
        return props;
    } // Ende getProperties()

    /**
     * Methode zum erstellen des Mail-Templates
     *
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws MessagingException
     */
    private MailTemplate createMailTemplate()
            throws UnsupportedEncodingException, IOException, MessagingException {
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setFrom(
                message.getFirstname() + " " + message.getLastname(),
                getProperty("mail.contactform.from.address")
                );
        mailTemplate.addTo(
                getProperty("mail.contactform.to.personal"),
                getProperty("mail.contactform.to.address")
                );
        mailTemplate.addBcc(
                getProperty("mail.contactform.bcc.personal"),
                getProperty("mail.contactform.bcc.address")
                );
        mailTemplate.addReplyTo(
                message.getFirstname() + " " + message.getLastname(),
                message.getMail()
                );
        if(getProperty("mail.contactform.language").equals("en")) {
            mailTemplate.setSubject(getProperty("mail.contactform.subject.en"));
            mailTemplate.setBody(getProperty("mail.contactform.body.en"));
            mailTemplate.setHtml(getProperty("mail.contactform.html.en"));
            mailTemplate.addImages(getProperty("mail.contactform.images.en"));
        } else {
            mailTemplate.setSubject(getProperty("mail.contactform.subject.de"));
            mailTemplate.setBody(getProperty("mail.contactform.body.de"));
            mailTemplate.setHtml(getProperty("mail.contactform.html.de"));
            mailTemplate.addImages(getProperty("mail.contactform.images.de"));
        }
        return mailTemplate;
    }

}// Ende class
