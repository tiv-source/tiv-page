/**
 * 
 */
package de.tivsource.page.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Marc Michele
 *
 */
public class MailTemplate {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(MailTemplate.class);

    private InternetAddress from;

    private ArrayList<InternetAddress> to = new ArrayList<InternetAddress>();
    private ArrayList<InternetAddress> cc = new ArrayList<InternetAddress>();
    private ArrayList<InternetAddress> bcc = new ArrayList<InternetAddress>();

    private ArrayList<InternetAddress> replyTo = new ArrayList<InternetAddress>();
    
    private String subject;
    private String body;
    private String html;

    private ArrayList<MimeBodyPart> images = new ArrayList<MimeBodyPart>();

    private ArrayList<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();

    /**
     * @return the from
     */
    public InternetAddress getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     * @throws UnsupportedEncodingException 
     */
    public void setFrom(String fromPersonal, String fromAddress) throws UnsupportedEncodingException {
        this.from = new InternetAddress(fromAddress, fromPersonal, "UTF-8");
    }

    public InternetAddress[] getToAddresses() {

        InternetAddress[] summary = new InternetAddress[to.size()];

        int counter = 0;
        Iterator<InternetAddress> toIt = to.iterator();

        while (toIt.hasNext()) {
            summary[counter] = toIt.next();
            counter++;
        }

        return summary;
    }// Ende getToAddresses()

    /**
     * Methode zum hinzufügen von Mail-Empfängern.
     * @param toPersonal - Name der Mail-Empfängers
     * @param toAddress - Mail-Adresse des Epfängers
     * @throws UnsupportedEncodingException
     */
    public void addTo(String toPersonal, String toAddress) throws UnsupportedEncodingException {
        to.add(new InternetAddress(toAddress, toPersonal, "UTF-8"));
    }

    public void addTo(String toAddress) throws AddressException {
        to.add(new InternetAddress(toAddress));
    }

    public InternetAddress[] getCcAddresses() {

        InternetAddress[] summary = new InternetAddress[cc.size()];

        int counter = 0;
        Iterator<InternetAddress> ccIt = cc.iterator();

        while (ccIt.hasNext()) {
            summary[counter] = ccIt.next();
            counter++;
        }

        return summary;
    }// Ende getCcAddresses()

    public void addCc(String ccPersonal, String ccAddress) throws UnsupportedEncodingException {
        cc.add(new InternetAddress(ccAddress, ccPersonal, "UTF-8"));
    }

    public InternetAddress[] getBccAddresses() {
        InternetAddress[] summary = new InternetAddress[bcc.size()];

        int counter = 0;
        Iterator<InternetAddress> bccIt = bcc.iterator();

        while (bccIt.hasNext()) {
            summary[counter] = bccIt.next();
            counter++;
        }

        return summary;
    }// Ende getBccAddresses()

    public void addBcc(String bccPersonal, String bccAddress) throws UnsupportedEncodingException {
        bcc.add(new InternetAddress(bccAddress, bccPersonal, "UTF-8"));
    }

    public InternetAddress[] getReplyToAddresses() {
        InternetAddress[] summary = new InternetAddress[replyTo.size()];

        int counter = 0;
        Iterator<InternetAddress> replyToIt = replyTo.iterator();

        while (replyToIt.hasNext()) {
            summary[counter] = replyToIt.next();
            counter++;
        }

        return summary;
    }// Ende getBccAddresses()

    public void addReplyTo(String replyToPersonal, String replyToAddress) throws UnsupportedEncodingException {
        replyTo.add(new InternetAddress(replyToAddress, replyToPersonal, "UTF-8"));
    }
    
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     * @throws IOException 
     */
    public void setBody(String filePath) throws IOException {
        this.body = readLineByLine(filePath);
    }

    /**
     * @return the html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html the html to set
     * @throws IOException 
     */
    public void setHtml(String filePath) throws IOException {
        this.html = readLineByLine(filePath);
    }

    /**
     * @return the images
     */
    public ArrayList<MimeBodyPart> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     * @throws FileNotFoundException 
     * @throws IOException
     * @throws MessagingException 
     */
    public void addImages(String imageList) throws FileNotFoundException, IOException, MessagingException {
        BufferedReader in = new BufferedReader(new FileReader(imageList));
        String line = null;
        while ((line = in.readLine()) != null) {
            LOGGER.info("Eingelesene Zeile der Image Liste: " + line);
            String[] items = line.split("\\|");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            DataSource fileDataSource = new FileDataSource(items[0]);
            mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
            mimeBodyPart.setDisposition(MimeBodyPart.INLINE);
            mimeBodyPart.setContentID("<"+ items[1] +">");
            //mimeBodyPart.setFileName(items[2]);
            //mimeBodyPart.removeHeader("Content-Type");
            mimeBodyPart.addHeader("Content-Type", items[3] +"; name=" + items[2]);
            images.add(mimeBodyPart);
        }
        in.close();
    }

    /**
     * @return the attachments
     */
    public ArrayList<MimeBodyPart> getAttachments() {
        return attachments;
    }

    /**
     * 
     * @param filePath
     * @param fileName
     * @param contentType
     * @throws MessagingException
     */
    public void addAttachment(String filePath, String fileName, String contentType) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        DataSource fileDataSource = new FileDataSource(filePath);
        mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
        mimeBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
        mimeBodyPart.setHeader("Content-Type", contentType);
        mimeBodyPart.setFileName(fileName);
        attachments.add(mimeBodyPart);
    }

    /**
     * Methode zum lesen aller Zeilen einer Datei (Java8 kompatible).
     * @param filePath - Pfad zur Datei
     * @return String - alle Zeilen die in der Datei enthalten sind
     * @throws IOException 
     */
    private static String readLineByLine(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            throw new IOException();
        }

        return stringBuilder.toString();
    }

}// Ende class
