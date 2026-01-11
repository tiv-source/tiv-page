package de.tivsource.page.user.actions.newsletter.lockrequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import jakarta.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;
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
import de.tivsource.page.common.captcha.Captcha;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.mail.MailAddressLockDaoLocal;
import de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.entity.mail.MailAddressLockRequest;
import de.tivsource.page.entity.page.Page;
import de.tivsource.page.exceptions.NoMailSessionCreatedException;
import de.tivsource.page.helper.MailSender;
import de.tivsource.page.helper.MailTemplate;
import de.tivsource.page.helper.Passwords;
import de.tivsource.page.user.actions.EmptyAction;

@TilesDefinitions({
  @TilesDefinition(name="mailAddressLockRequestForm", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/newsletter/mailAddressLockRequest_form.jsp")
  }),
  @TilesDefinition(name="page", extend = "userTemplate", putAttributes = {
    @TilesPutAttribute(name = "meta",       value = "/WEB-INF/tiles/active/meta/content.jsp"),
    @TilesPutAttribute(name = "twitter",    value = "/WEB-INF/tiles/active/twitter/content.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/page/page.jsp")
  })
})
public class InscribeAction extends EmptyAction {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 4813085742584978823L;

    /**
	 * Statischer Logger der Klasse.
	 */
    private static final Logger LOGGER = LogManager.getLogger(InscribeAction.class);

    @InjectEJB(name="CaptchaDao")
    private CaptchaDaoLocal captchaDaoLocal;

    @InjectEJB(name="PageDao")
    private PageDaoLocal pageDaoLocal;

    @InjectEJB(name="PropertyDao")
    private PropertyDaoLocal propertyDaoLocal;

    @InjectEJB(name="MailAddressLockDao")
    private MailAddressLockDaoLocal mailAddressLockDaoLocal;

    @InjectEJB(name="MailAddressLockRequestDao")
    private MailAddressLockRequestDaoLocal mailAddressLockRequestDaoLocal;
    
	private String mailAddress;

	private Page page;

    private Captcha captcha;

    private String answer;

    @Actions({
        @Action(
            value = "inscribe",
            results = {
                @Result(name = "success",             type="tiles", location = "page"),
                @Result(name = "input",               type="tiles", location = "mailAddressLockRequestForm"),
                @Result(name = "existingLock",        type="tiles", location = "page"),
                @Result(name = "existingLockRequest", type="tiles", location = "page"),
                @Result(name = "couldNotSendMail",    type="tiles", location = "page")
            }
        )
    })
	public String execute() {
        LOGGER.info("execute() aufgerufen.");

        // Hole Action Locale
    	this.getLanguageFromActionContext();

    	// Überprüfen ob die Sicherheitsabfrage richtig beantwortet wurde
    	if(answer != null && captcha != null && !answer.trim().equals("") && captcha.getContent().equals(answer)) {

    	    // Erzeuge Hash aus der angebenen Mail-Adresse
    	    // und codiere den Hash als Base64 String
            byte[] hashBytes = Passwords.hash(mailAddress.toCharArray(), "UltrageheimerSaltfürMailAdressen2022!".getBytes());
            String hash = Base64.encodeBase64String(hashBytes);

    	    // Überprüfen ob die Mail-Adresse Zeichen enthält die nicht erlaubt sind
    	    // oder länger als 254 Zeichen ist.
    	    if(!isValid(mailAddress)) {
    	        addFieldError("mailAddress", "Die Mail-Adresse enthält Zeichen die nicht erlaubt sind oder ist zu lang.");
    	        return INPUT;
    	    }

    	    // TODO: Überprüfen ob die Mail-Adresse schon in den Verteiler eingetragen wurde

            // Überprüfe ob Mail-Adresse schon gesperrt wurde
    	    if(mailAddressLockDaoLocal.existMailAddressLock(mailAddress)) {
                page = pageDaoLocal.findByTechnical("existinglock");
                return "existingLock";
    	    }

    	    // Überprüfe ob schon einen Sperrantrag vorliegt
    	    if(mailAddressLockRequestDaoLocal.existsALockRequest(hash)) {
    	        page = pageDaoLocal.findByTechnical("existinglockrequest");
    	        return "existingLockRequest";
    	    }

    	    // Erzeuge UUID für den LockRequest 
            String uuid = UUID.randomUUID().toString();
            
            // Versuche die Mail zu versenden, wenn das nicht funktioniert
            // leite den Benutzer auf eine Fehlerseite
            try {
                sendMail(uuid);
            } catch (MessagingException | IOException e) {
                page = pageDaoLocal.findByTechnical("couldnotsendmail");
                LOGGER.debug(e.getMessage());
                return "couldNotSendMail";
            }

            // Speichere Message Objekt
            String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
            MailAddressLockRequest mailAddressLockRequest = new MailAddressLockRequest();
            mailAddressLockRequest.setCreated(new Date());
            mailAddressLockRequest.setCreatedAddress(remoteAddress);
            mailAddressLockRequest.setHash(hash);
            mailAddressLockRequest.setUsed(false);
            mailAddressLockRequest.setUuid(uuid);
            mailAddressLockRequestDaoLocal.merge(mailAddressLockRequest);
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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

    private void sendMail(String uuid) throws UnsupportedEncodingException, IOException, MessagingException {
		LOGGER.info("sendMail() aufgerufen.");
		MailSender sendIt = new MailSender(getProperties(), createMailTemplate());
		sendIt.createSession(false);
		String[] argu = {mailAddress, uuid};

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
        props.put("mail.transport.protocol",   getProperty("mail.transport.protocol"));
        props.put("mail.host",                 getProperty("mail.host"));
        props.put("mail.port",                 getProperty("mail.port"));
        props.put("mail.smtp.auth",            getProperty("mail.smtp.auth"));
        props.put("mail.smtp.tls",             getProperty("mail.smtp.tls"));
        props.put("mail.smtp.starttls.enable", getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.localhost",       getProperty("mail.smtp.localhost"));
        props.put("mail.user",                 getProperty("mail.user"));
        props.put("mail.password",             getProperty("mail.password"));
        props.put("mail.mime.charset",         getProperty("mail.mime.charset"));
        props.put("mail.use8bit",              getProperty("mail.use8bit"));
        
        return props;
    } // Ende getProperties()

    private MailTemplate createMailTemplate()
            throws UnsupportedEncodingException, IOException, MessagingException {
        MailTemplate mailTemplate = new MailTemplate();
        mailTemplate.setFrom(
                getProperty("mail.lockrequest.from.personal"),
                getProperty("mail.lockrequest.from.address")
                );
        mailTemplate.addTo(mailAddress);
        mailTemplate.addReplyTo(
                getProperty("mail.lockrequest.replyto.personal"),
                getProperty("mail.lockrequest.replyto.address")
                );
        if(this.getLanguage().equals("en")) {
            mailTemplate.setSubject(getProperty("mail.lockrequest.subject.en"));
            mailTemplate.setBody(getProperty("mail.lockrequest.body.en"));
            mailTemplate.setHtml(getProperty("mail.lockrequest.html.en"));
            mailTemplate.addImages(getProperty("mail.lockrequest.images.en"));
        } else {
            mailTemplate.setSubject(getProperty("mail.lockrequest.subject.de"));
            mailTemplate.setBody(getProperty("mail.lockrequest.body.de"));
            mailTemplate.setHtml(getProperty("mail.lockrequest.html.de"));
            mailTemplate.addImages(getProperty("mail.lockrequest.images.de"));
        }
        return mailTemplate;
    }

    private void setUpPage() {
        LOGGER.info("Action Errors: " + this.getFieldErrors().size());
        if(this.getFieldErrors().size() > 0) {
            page = pageDaoLocal.findByTechnical("lockrequestform");
        } else {
            page = pageDaoLocal.findByTechnical("lockrequestsuccess");
        }
    }

    private Boolean isValid(String input) {
        if (Pattern.matches("[A-Za-z0-9-._@]*", input)) {
            if(input.length() < 255) {
                return true;
            }
        }
        return false;
    }// Ende isValid(String input)

}// Ende class
