package de.tivsource.page.helper.sender;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.pdfbox.exceptions.COSVisitorException;

import de.tivsource.page.entity.reservation.Reservation;
import de.tivsource.page.helper.EmailTemplate;
import de.tivsource.page.helper.pdf.CreateReservationPDF;

public class ReservationMail {

	/**
	 * Benutzername für die Mail-Session
	 */
	private String username;

	/**
	 * Passwort für die Mail-Session
	 */
	private String password;

	/**
	 * Authenticator für die Mail Session
	 */
    private Authenticator auth = new Authenticator() {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    };

	/**
	 * Enthät den Inputstream der Template Datei
	 */
	private InputStream template;

	/**
	 * Pfad zur Template Datei
	 */
	private URL templatePath;

	/**
	 * Reservierung die vesendet werden soll
	 */
	private Reservation reservation;

	private File logoFile;
	private File adFile;

	private File fontFile;
	
	/**
	 * Mail Session
	 */
	private Session session;

	private EmailTemplate emailTemplate;

	private String fromAddress;
	private String fromName;

	private String replyToAddress;
	private String replyToName;

	private String bccAddress;
	
	public ReservationMail(String username, String password, URL templatePath,
			Reservation reservation, File logoFile, File adFile,
			Properties properties, File fontFile, String fromAddress,
			String fromName, String replyToAddress, String replyToName, String bccAddress)
			throws IOException {
		super();
		this.username = username;
		this.password = password;
		this.templatePath = templatePath;
		this.reservation = reservation;
		this.logoFile = logoFile;
		this.adFile = adFile;
		this.fontFile = fontFile;
		this.fromAddress = fromAddress;
		this.fromName = fromName;
		this.replyToAddress = replyToAddress;
		this.replyToName = replyToName;
		this.bccAddress = bccAddress;
		this.template = this.templatePath.openStream();
        this.session = Session.getInstance(properties, auth);
        this.session.setDebug(false);
        this.emailTemplate = (EmailTemplate)EmailTemplate.getEmailTemplate(template);
	}

	public void send() throws MessagingException, COSVisitorException, IOException {

        // Datums Formatierung
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy ");

        String[] arguments = {
                reservation.getGender() ? "Sehr geehrte Frau" : "Sehr geehrter Herr",
                reservation.getFirstname(), 
                reservation.getLastname(),
                replyToName,
                reservation.getEmail(), 
                reservation.getTelephone(),
                simpleDateFormat.format(reservation.getTime()),
                reservation.getQuantity().toString(),
                reservation.getWishes(),
                reservation.getWishes().replace("\n", "<br/>")
                };
		
        /*
         * Erzeuge neue Nachricht.
         */
        MimeMessage message = new MimeMessage(session);

        /*
         * Setze Absender-Adresse aus dem Template.
         */
        message.setFrom(new InternetAddress(
        		fromAddress, 
        		MimeUtility.encodeText(fromName, "UTF-8", "Q")
        ));

        message.addRecipients(Message.RecipientType.TO,
        		new javax.mail.Address[]{
        			new InternetAddress(
        					reservation.getEmail(), 
        					MimeUtility.encodeText(reservation.getFirstname() +" "+ reservation.getLastname(),"UTF-8", "Q")
        		    )
                }
        );

        message.addRecipients(Message.RecipientType.CC,
        		emailTemplate.getCcAddresses());
        message.addRecipients(Message.RecipientType.BCC,
        	new javax.mail.Address[]{
    			new InternetAddress(
    					bccAddress, 
    					MimeUtility.encodeText("BCC Empfänger","UTF-8", "Q")
    		    )
            }
        );

        // Setze die ReplyTo Adresse
        message.setReplyTo(new javax.mail.Address[]{
        		new InternetAddress(
        				replyToAddress, 
        				MimeUtility.encodeText(replyToName, "UTF-8", "Q")
        		)
        });

        /*
         * Setze Subject in die Nachricht
         */
        message.setSubject(
            // Codiere das Subject der Nachricht
            MimeUtility.encodeText(
                // Setze Variablen in den Betreff der Nachricht.
                MessageFormat.format(emailTemplate.getSubject(), (Object[])arguments),"UTF-8", "Q"), 
                "UTF-8");

        /*
         * Erzeugen des Body Teil, enthält als einziges Element den Mixed Teil.
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
        html.setContent(MessageFormat.format(emailTemplate.getHtml(), (Object[])arguments),
                "text/html; charset=UTF-8");
        html.setHeader("Content-Transfer-Encoding", "8bit");

        /*
         * Setze den Text-Inhalt aus dem Template in das Text-Element. Setze das
         * Transfer-Encoding des Text-Elements.
         */
        text.setText(MessageFormat.format(emailTemplate.getBody(), (Object[])arguments));
        text.setHeader("Content-Transfer-Encoding", "8bit");

        // Erzeugen Nachrichten Inhalts-Element der Teil der die Bilder und den
        // Mantel enthält.
        MimeMultipart content = new MimeMultipart("related");

        // Setze den Inhalt in die Nachricht.
        message.setContent(content);

        // Setze den Setze den Mantel in das Inhalts-Element.
        content.addBodyPart(mantle);

        // Erzeuge das PDF
    	File pdfFile = new File("/tmp/" + reservation.getUuid() + ".pdf");
        new CreateReservationPDF(
        		pdfFile,
                reservation,
                logoFile,
                adFile,
                fontFile
        );
        
        // Erzeuge Teil der das PDF enthält
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        // Erzeuge DataSource Handler
        DataSource source = new FileDataSource(pdfFile);
        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setHeader("Content-Type", "application/pdf");
        mimeBodyPart.setFileName("Reservierungsbestaetigung.pdf");

        // Setze den das PDF in das Inhalts-Element.
        content.addBodyPart(mimeBodyPart);

        // Setze das Transfer-Encoding der gesamten Nachricht.
        message.setHeader("Content-Transfer-Encoding", "8bit");

        // Speichere die Änderungen.
        message.saveChanges();

        // Sende die Nachricht.
        Transport.send(message);

        // Lösche die PDF Datei
        pdfFile.delete();
        
	}// Ende send()

}// Ende class
