package de.tivsource.page.user.actions.contact;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
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
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
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

    @InjectEJB(name="SalutationDao")
    private SalutationDaoLocal salutationDaoLocal;

	private Message message;

	private Page page;

    private Captcha captcha;

    private String answer;

    private List<Salutation> salutations;

    /**
     * Anti-Bot Checks
     */
    private String fax;
    private String notEmpty;
    private String protectionTimeString;

    @Override
    public void prepare() {
        // Lade die Kontaktseite aus der Datenbank
        page = pageDaoLocal.findByTechnical("contact");
        // Anreden aus der Datenbank geholt
        salutations = salutationDaoLocal.findAll(0, salutationDaoLocal.countAll());
    }

    @Actions({
       @Action(
           value = "sent",
           results = {
               @Result(name = "success",          type = "redirectAction", location = "contactsendsuccess/index.html", params={"namespace", "/"}),
               @Result(name = "input",            type = "tiles", location = "contactForm"),
               @Result(name = "couldNotSendMail", type = "redirectAction", location = "contactsenderror/index.html", params={"namespace", "/"}),
               @Result(name = "error",            type = "redirectAction", location = "contacterror/index.html", params={"namespace", "/"}),
           }
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	LOGGER.debug("Inhalt der Variable \"fax\": " + fax);
    	// Wenn der String gesetzt wurde, dann ist dass mit Absicht 
    	// geschehen (Manipulation) und/oder es war ein Spambot 
    	if(fax == null || !fax.trim().equals("") || fax.length() != 0) {
    	    LOGGER.debug("Die Variable \"fax\" wurde gesetzt (Bot detected/Spam)");
    	    return ERROR;
    	}

    	LOGGER.debug("Inhalt der Variable \"notEmpty\": " + notEmpty);
    	// Wenn der String gesetzt wurde, dann ist dass mit Absicht 
        // geschehen (Manipulation) und/oder es war ein Spambot 
        if(notEmpty == null || notEmpty.trim().equals("") || notEmpty.length() == 0 || !notEmpty.equals("48ac-8a00-9737505b7c82") ) {
            LOGGER.debug("Die Variable \"notEmpty\" wurde gesetzt (Bot detected/Spam)");
            return ERROR;
        }

        LOGGER.debug("Inhalt der Variable \"protectionTimeString\": " + protectionTimeString);
        // Wenn der String gesetzt wurde, dann ist dass mit Absicht 
        // geschehen (Manipulation) und/oder es war ein Spambot 
        if(protectionTimeString == null || !protectionTimeString.trim().equals("") || protectionTimeString.length() != 0) {
            LOGGER.debug("Die Variable \"protectionTimeString\" wurde gesetzt (Bot detected/Spam)");
            return ERROR;
        }

        LOGGER.debug("Inhalt der Variable \"message.gender\": " + message.getGender());
        // Wenn der String gesetzt wurde, dann ist dass mit Absicht 
        // geschehen (Manipulation) und/oder es war ein Spambot 
        if(message.getGender() == null || message.getGender()) {
            LOGGER.debug("Die Variable \"message.gender\" wurde gesetzt und ist nicht false (Bot detected/Spam)");
            return ERROR;
        }

        // Wenn eine Anwort gegeben wurde und diese 
        // nicht leer ist und dem geforderten String entspricht
        if(answer != null && captcha != null && !answer.trim().equals("") && captcha.getContent().equals(answer)) {
            // Versuche die Mail zu versenden, wenn das nicht funktioniert
            // leite den Benutzer auf eine Fehlerseite
            try {
                // Setze die UUID des Benutzern Captchas
                message.setCaptchaUuid(captcha.getUuid());
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
        }

        // Lade Zufälligen Captcha aus der Datenbank
        captcha = captchaDaoLocal.random();
        addFieldError("answer", "Bitte geben Sie die Hausnummer ein.");
        return INPUT;
	}// Ende execute()

    @Override
    public Page getPage() {
        return page;
    }// Ende getPage()

    @StrutsParameter(depth=1)
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    @StrutsParameter
    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    @StrutsParameter
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Salutation> getSalutations() {
        return salutations;
    }

    @StrutsParameter
    public void setFax(String fax) {
        this.fax = fax;
    }

    @StrutsParameter
    public void setNotEmpty(String notEmpty) {
        this.notEmpty = notEmpty;
    }

    @StrutsParameter
    public void setProtectionTimeString(String protectionTimeString) {
        this.protectionTimeString = protectionTimeString;
    }

    private void sendMail() throws UnsupportedEncodingException, IOException, MessagingException {
        LOGGER.info("sendMail() aufgerufen.");
        MailSender sendIt = new MailSender(getProperties(), createMailTemplate());
        Boolean debugMailSession = getProperty("mail.contactform.debug.session").equals("true") ? true : false;
        sendIt.createSession(debugMailSession);
        String[] argu = {
                message.getSalutation().getName(getProperty("mail.contactform.language")),
                message.getFirstname(), 
                message.getLastname(), 
                message.getMail(), 
                message.getTelephone(),
                message.getCaptchaUuid(),
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
