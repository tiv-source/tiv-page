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

import de.tivsource.ejb3plugin.InjectEJB;
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
  @TilesDefinition(name="registrationPredeclareError", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/predeclare_error.jsp")
  }),
  @TilesDefinition(name="registrationPredeclareForm", extend = "adminTemplate", putAttributes = {
    @TilesPutAttribute(name = "navigation", value = "/WEB-INF/tiles/active/navigation/academy.jsp"),
    @TilesPutAttribute(name = "content",    value = "/WEB-INF/tiles/active/view/registration/predeclare_form.jsp")
  })
})
public class PredeclareAction extends EmptyAction {

	/**
	 * Serial Version UID.
	 */
    private static final long serialVersionUID = 5020911413538258387L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(PredeclareAction.class);

    @InjectEJB(name="RegistrationDao")
    private RegistrationDaoLocal registrationDaoLocal;

    @InjectEJB(name="UserDao")
    private UserDaoLocal userDaoLocal;

    private Registration registration;

    private Registration dbRegistration;

    private String redirection;

    /**
     * Benutzer der die Bestätigung verschickt.
     */
    private User user;

    @Autowired
    public PredeclareAction(PropertyService propertyService) {
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
            value = "predeclare",
            results = {
                @Result(name = "success",     type = "redirectAction", location = "%{redirection}"),
                @Result(name = "input",       type="tiles", location = "registrationPredeclareForm"),
                @Result(name = "error",       type="tiles", location = "registrationPredeclareError")
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

    	    if(dbRegistration.getStatus() == RegistrationStatus.WAITING) {
    	        dbRegistration.setStatus(RegistrationStatus.PREDECLARED);
    	        registrationDaoLocal.merge(dbRegistration);
    	        // Zurück zu den zu wartenden Anmeldungen
    	        redirection = "waiting.html";

                SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
                String[] arguments = {
                        dbRegistration.getSalutation().getLongSalutation("de"),
                        dbRegistration.getFirstname(),
                        dbRegistration.getLastname(),
                        user.getFirstname() + " " + user.getLastname(),
                        dbRegistration.getEmail(),
                        dbRegistration.getTelephone(),
                        "https://academy.app.internal/public/registration/" + dbRegistration.getUserToken() + "/confirmForm.html",
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
                getProperty("mail.course.predeclare.from.personal"),
                getProperty("mail.course.predeclare.from.address")
                );
        mailTemplate.addTo(
                dbRegistration.getFirstname() + " " + dbRegistration.getLastname(),
                dbRegistration.getEmail()
                );
        mailTemplate.addBcc(
                getProperty("mail.course.predeclare.bcc.personal"),
                getProperty("mail.course.predeclare.bcc.address")
                );
        mailTemplate.addReplyTo(
                user.getFirstname() + " " + user.getLastname(),
                getProperty("mail.course.predeclare.replyTo.address")
                );
        if(getProperty("mail.course.predeclare.language").equals("en")) {
            mailTemplate.setSubject(
                    "Es ist ein Platz im Kurs \"" + 
                    dbRegistration.getCourse().getName("en") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\" freigeworden."
            );
            mailTemplate.setBody(getProperty("mail.course.predeclare.body.en"));
            mailTemplate.setHtml(getProperty("mail.course.predeclare.html.en"));
            mailTemplate.addImages(getProperty("mail.course.predeclare.images.en"));
        } else {
            mailTemplate.setSubject(
                    "Es ist ein Platz im Kurs \"" + 
                    dbRegistration.getCourse().getName("en") + 
                    " (" + 
                    dbRegistration.getCourse().getNumber() + 
                    ")\" freigeworden."
            );
            mailTemplate.setBody(getProperty("mail.course.predeclare.body.de"));
            mailTemplate.setHtml(getProperty("mail.course.predeclare.html.de"));
            mailTemplate.addImages(getProperty("mail.course.predeclare.images.de"));
        }
        return mailTemplate;
    }

}// Ende class
