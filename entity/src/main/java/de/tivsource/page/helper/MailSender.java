/**
 * 
 */
package de.tivsource.page.helper;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.exceptions.NoMailSessionCreatedException;

/**
 * @author Marc Michele
 *
 */
public class MailSender {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);

    private String authUser;
    private String authPassword;
    
    private jakarta.mail.Authenticator auth = new jakarta.mail.Authenticator() {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(authUser, authPassword);
        }
    };

    private Properties properties;

    private Session session;

    private MailTemplate mailTemplate;

    public MailSender(Properties properties, MailTemplate mailTemplate) {
        super();
        this.authUser = properties.getProperty("mail.user");
        this.authPassword = properties.getProperty("mail.password");
        this.properties = properties;
        this.mailTemplate = mailTemplate;
    }

    public void createSession(Boolean debug) {
        session = Session.getInstance(properties, auth);
        session.setDebug(false);
        if(debug) {
            session.setDebug(debug);
        }
    }

    public void send(Object[] arguments) throws AddressException, MessagingException,
            UnsupportedEncodingException, NoMailSessionCreatedException {

        /*
         * Check ob es eine Session gibt
         */
        if(session == null) {
            throw new NoMailSessionCreatedException("Es wurde keine Mail Session erzeugt.");
        }

        /*
         * Erzeuge neue Nachricht.
         */
        MimeMessage message = new MimeMessage(session);

        /*
         * Setze Absender aus dem MailTemplate.
         */
        message.setFrom(mailTemplate.getFrom());

        /*
         * Setze Empfänger aus dem MailTemplate.
         */
        message.addRecipients(Message.RecipientType.TO,
                mailTemplate.getToAddresses());
        message.addRecipients(Message.RecipientType.CC,
                mailTemplate.getCcAddresses());
        message.addRecipients(Message.RecipientType.BCC,
                mailTemplate.getBccAddresses());

        // Setze die ReplyTo Adressen
        message.setReplyTo(mailTemplate.getReplyToAddresses());

        /*
         * Setze Subject in die Nachricht
         */
        message.setSubject(
            // Codiere das Subject der Nachricht
            MimeUtility.encodeText(
                // Setze Variablen in den Betreff der Nachricht.
                MessageFormat.format(mailTemplate.getSubject(), arguments),"UTF-8", "Q"), 
                "UTF-8");

        /*
         * Erzeugen des Mantels, enthält als einziges Element die Hülle. Dies
         * ist nötig damit die Bilder eingebettet werden können.
         */
        MimeBodyPart mantle = new MimeBodyPart();

        /*
         * Erzeugen der Hülle für Text und HTML. Ist vom Type "alternative" und
         * zeigt zuerst das an in den Voreinstellungen des EMail-Reader
         * eingestellt wurde.
         */
        MimeMultipart cover = new MimeMultipart("alternative");

        // Erzeugen des Text-Teils
        MimeBodyPart text = new MimeBodyPart();

        // Hinzufügen des Text-Teils zur Hülle.
        cover.addBodyPart(text);

        // Erzeugen des HTML-Teils.
        MimeBodyPart html = new MimeBodyPart();

        // Hinzufügen des HTML-Teils zur Hülle.
        cover.addBodyPart(html);

        // Füge das Cover dem Mantel hinzu.
        mantle.setContent(cover);

        /*
         * Setze den HTML-Inhalt aus dem Template in das HTML-Element. Setze das
         * Transfer-Encoding des HMTL-Elements.
         */
        html.setContent(MessageFormat.format(mailTemplate.getHtml(), arguments),
                "text/html; charset=UTF-8");
        html.setHeader("Content-Transfer-Encoding", "8bit");

        /*
         * Setze den Text-Inhalt aus dem Template in das Text-Element. Setze das
         * Transfer-Encoding des Text-Elements.
         */
        text.setText(MessageFormat.format(mailTemplate.getBody(), arguments));
        text.setHeader("Content-Transfer-Encoding", "8bit");

        // Erzeugen Nachrichten Inhalts-Element der Teil der die Bilder und den
        // Mantel enthält.
        MimeMultipart content = new MimeMultipart("related");

        // Setze den Inhalt in die Nachricht.
        message.setContent(content);

        // Setze den Setze den Mantel in das Inhalts-Element.
        content.addBodyPart(mantle);

        // Setze die Bilder in die Nachricht
        generateInlineImages(content, mailTemplate.getImages());

        // Setze das Transfer-Encoding der gesamten Nachricht.
        message.setHeader("Content-Transfer-Encoding", "8bit");

        // Speichere die Änderungen.
        message.saveChanges();

        // Sende die Nachricht.
        Transport.send(message);
    }

    private void generateInlineImages(MimeMultipart mimeMultipart, ArrayList<MimeBodyPart> images) throws MessagingException {
        LOGGER.info("generateInlineImages(MimeMultipart mimeMultipart, ArrayList<MimeBodyPart> images) aufgerufen.");
        Iterator<MimeBodyPart> imagesIt = images.iterator();
        while (imagesIt.hasNext()) {
            mimeMultipart.addBodyPart(imagesIt.next());
        }
    }// Ende generateInlineImages(MimeMultipart mimeMultipart, ArrayList<MimeBodyPart> images)

}// Ende class
