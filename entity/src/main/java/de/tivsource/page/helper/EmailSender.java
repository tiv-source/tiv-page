package de.tivsource.page.helper;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class EmailSender {

    public void send(String to, EmailTemplate template, Object[] arguments,
            Session session) throws AddressException, MessagingException,
            UnsupportedEncodingException {

        /*
         * Erzeuge neue Nachricht.
         */
        MimeMessage message = new MimeMessage(session);

        /*
         * Setze Absender und Empfänger aus dem Template.
         */
        message.setFrom(new InternetAddress(template.getFrom(), "Kontaktformular"));
        message.addRecipients(Message.RecipientType.TO,
                template.getToAddresses());
        message.addRecipients(Message.RecipientType.CC,
                template.getCcAddresses());
        message.addRecipients(Message.RecipientType.BCC,
                template.getBccAddresses());

        /*
         * Setze Subject in die Nachricht
         */
        message.setSubject(
            // Codiere das Subject der Nachricht
            MimeUtility.encodeText(
                // Setze Variablen in den Betreff der Nachricht.
                MessageFormat.format(template.getSubject(), arguments),"UTF-8", "Q"), 
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
        html.setContent(MessageFormat.format(template.getHtml(), arguments),
                "text/html; charset=UTF-8");
        html.setHeader("Content-Transfer-Encoding", "8bit");

        /*
         * Setze den Text-Inhalt aus dem Template in das Text-Element. Setze das
         * Transfer-Encoding des Text-Elements.
         */
        text.setText(MessageFormat.format(template.getBody(), arguments));
        text.setHeader("Content-Transfer-Encoding", "8bit");

        // Erzeugen Nachrichten Inhalts-Element der Teil der die Bilder und den
        // Mantel enthält.
        MimeMultipart content = new MimeMultipart("related");

        // Setze den Inhalt in die Nachricht.
        message.setContent(content);

        // Setze den Setze den Mantel in das Inhalts-Element.
        content.addBodyPart(mantle);

        // Setze das Transfer-Encoding der gesamten Nachricht.
        message.setHeader("Content-Transfer-Encoding", "8bit");

        // Speichere die Änderungen.
        message.saveChanges();

        // Sende die Nachricht.
        Transport.send(message);
    }



} // Ende class.
