package de.tivsource.page.admin.actions.academy.registration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import de.tivsource.page.admin.actions.EmptyAction;
import de.tivsource.page.admin.services.PropertyService;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.entity.administration.User;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import de.tivsource.page.entity.registration.Registration;
import de.tivsource.page.exceptions.NoMailSessionCreatedException;
import de.tivsource.page.helper.MailSender;
import de.tivsource.page.helper.MailTemplate;
import jakarta.mail.MessagingException;

/**
 * 
 * @author Marc Michele
 *
 */
@TilesDefinitions({
  @TilesDefinition(name="registrationRevokeError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/revoke_error.jsp")
  }),
  @TilesDefinition(name="registrationRevokeForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/revoke_form.jsp")
  })
})
public class RevokeAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 8530586926844720007L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RevokeAction.class);

    @Autowired
    @Qualifier("registrationDaoLocal")
    private RegistrationDaoLocal registrationDaoLocal;

    @Autowired
    @Qualifier("userDaoLocal")
    private UserDaoLocal userDaoLocal;

    private Registration registration;

    private Registration dbRegistration;

    private String redirection;

    /**
     * Benutzer der die Bestätigung verschickt.
     */
    private User user;

    @Autowired
    public RevokeAction(PropertyService propertyService) {
        super(propertyService);
    }

    @StrutsParameter(depth=3)
    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getRedirection() {
        return redirection;
    }

    @Override
    @Actions({
        @Action(
            value = "revoke",
            results = {
                @Result(name = "success",     type = "redirectAction", location = "%{redirection}"),
                @Result(name = "input",       type="tiles", location = "registrationRevokeForm"),
                @Result(name = "error",       type="tiles", location = "registrationRevokeError")
            }
        )
    })
    public String execute() throws Exception {
    	LOGGER.info("execute() aufgerufen.");

        String remoteUser    = ServletActionContext.getRequest().getRemoteUser();
        String remoteAddress = ServletActionContext.getRequest().getRemoteAddr();
        // Hole die Benutzerdaten aus der Datenbank
        user = userDaoLocal.findByUsername(remoteUser);
        
    	if(registration != null) {
    	    dbRegistration = registrationDaoLocal.findByUuid(registration.getUuid());

    	    dbRegistration.setModified(new Date());
            dbRegistration.setModifiedBy(remoteUser);
            dbRegistration.setModifiedAddress(remoteAddress);

    	    if(dbRegistration.getStatus() == RegistrationStatus.PREDECLARED) {
    	        dbRegistration.setStatus(RegistrationStatus.WAITING);
    	        registrationDaoLocal.merge(dbRegistration);
    	        // Zurück zu den zu auf Benutzer-Bestätigung wartenden Anmeldungen
    	        redirection = "predeclared.html";

                SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
                String[] arguments = {
                        dbRegistration.getSalutation().getLongSalutation("de"),
                        dbRegistration.getFirstname(),
                        dbRegistration.getLastname(),
                        user.getFirstname() + " " + user.getLastname(),
                        dbRegistration.getEmail(),
                        dbRegistration.getTelephone(),
                        "",
                        "https://academy.app.internal/public/registration/" + dbRegistration.getUserToken() + "/cancelForm.html",
                        dbRegistration.getCourse().getName("de"),
                        dbRegistration.getCourse().getNumber(),
                        simpleDateFormatDate.format(dbRegistration.getCourse().getStartDate()),
                        simpleDateFormatTime.format(dbRegistration.getCourse().getStartDate())
                };

                MailSender mailSender = new MailSender(getProperties(), createMailTemplate());
                Boolean debugMailSession = getProperty("mail.course.predeclare.debug.session").equals("true") ? true : false;
                mailSender.createSession(debugMailSession);

                new Thread(new Runnable() {
                    public void run(){
                        try {
                            mailSender.send(arguments);
                        } catch (NoMailSessionCreatedException | MessagingException | UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return; // to stop the thread
                    }
                }).start();

    	        // TODO: Hier Anfragemail ob Kunde die Anmeldung annehmen möchte
    	        // Es wird kein Dokument (PDF) benötigt
    	        return SUCCESS;
    	    }// Ende if()
    	    return ERROR;
    	}
   		return ERROR;
    	
    }// Ende execute()

    private Properties getProperties() {
        LOGGER.info("getProperties() aufgerufen.");

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
        props.put("mail.transport.protocol", 
                getProperty("mail.transport.protocol"));
        props.put("mail.host", 
                getProperty("mail.host"));
        props.put("mail.port", 
                getProperty("mail.port"));
        props.put("mail.smtp.auth", 
                getProperty("mail.smtp.auth"));
        props.put("mail.smtp.tls", 
                getProperty("mail.smtp.tls"));
        props.put("mail.smtp.starttls.enable",
                getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.localhost", 
                getProperty("mail.smtp.localhost"));
        props.put("mail.user", 
                getProperty("mail.user"));
        props.put("mail.password", 
                getProperty("mail.password"));
        props.put("mail.mime.charset", 
                getProperty("mail.mime.charset"));
        props.put("mail.use8bit", 
                getProperty("mail.use8bit"));
        //TODO: Reihenfolge aufräumen
        props.put("mail.smtp.ssl.protocols", 
                getProperty("mail.smtp.ssl.protocols"));
        props.put("mail.smtp.ssl.trust", 
                getProperty("mail.smtp.ssl.trust"));
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
                getProperty("mail.course.revoke.from.personal"),
                getProperty("mail.course.revoke.from.address")
                );
        mailTemplate.addTo(
                dbRegistration.getFirstname() + " " + dbRegistration.getLastname(),
                dbRegistration.getEmail()
                );
        mailTemplate.addBcc(
                getProperty("mail.course.revoke.bcc.personal"),
                getProperty("mail.course.revoke.bcc.address")
                );
        mailTemplate.addReplyTo(
                user.getFirstname() + " " + user.getLastname(),
                getProperty("mail.course.revoke.replyTo.address")
                );
        if(getProperty("mail.course.revoke.language").equals("en")) {
            mailTemplate.setSubject(
                    "Der Status Ihrer Anmeldung zum Kurs \"" + 
                    dbRegistration.getCourse().getName("de") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\" hat sich geändert."
            );
            mailTemplate.setBody(getProperty("mail.course.revoke.body.en"));
            mailTemplate.setHtml(getProperty("mail.course.revoke.html.en"));
            mailTemplate.addImages(getProperty("mail.course.revoke.images.en"));
        } else {
            mailTemplate.setSubject(
                    "Der Status Ihrer Anmeldung zum Kurs \"" + 
                    dbRegistration.getCourse().getName("de") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\" hat sich geändert."
            );
            mailTemplate.setBody(getProperty("mail.course.revoke.body.de"));
            mailTemplate.setHtml(getProperty("mail.course.revoke.html.de"));
            mailTemplate.addImages(getProperty("mail.course.revoke.images.de"));
        }
        return mailTemplate;
    }

}// Ende class
