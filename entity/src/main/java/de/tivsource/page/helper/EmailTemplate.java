package de.tivsource.page.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import jakarta.mail.internet.InternetAddress;

import org.apache.commons.digester.Digester;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 * @author Marc Michele
 *
 */
public class EmailTemplate {

    /**
     * Statischer Logger der Klasse EmailTemplate, zur Zeit gibt es Meldungen
     * vom Type TRACE und DEBUG.
     */
    private static final Logger LOGGER = LogManager.getLogger(EmailTemplate.class);

    private String subject;
    private String body;
    private String from;
    private String html;

    private ArrayList<String> to = new ArrayList<String>();
    private ArrayList<String> cc = new ArrayList<String>();
    private ArrayList<String> bcc = new ArrayList<String>();

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public void addTo(String toSet) {
        to.add(toSet);
    }

    public void addCc(String ccSet) {
        cc.add(ccSet);
    }

    public void addBcc(String bccSet) {
        bcc.add(bccSet);
    }

    public InternetAddress[] getToAddresses() {

        InternetAddress[] summary = new InternetAddress[to.size()];

        int counter = 0;
        Iterator<String> toIt = to.iterator();

        while (toIt.hasNext()) {
            InternetAddress iaddress = new InternetAddress();
            String next = toIt.next();
            iaddress.setAddress(next);
            summary[counter] = iaddress;
            counter++;
        }

        return summary;
    }

    public InternetAddress[] getCcAddresses() {

        InternetAddress[] summary = new InternetAddress[cc.size()];

        int counter = 0;
        Iterator<String> ccIt = cc.iterator();

        while (ccIt.hasNext()) {
            InternetAddress iaddress = new InternetAddress();
            iaddress.setAddress(ccIt.next());
            summary[counter] = iaddress;
            counter++;
        }

        return summary;
    }

    public InternetAddress[] getBccAddresses() {
        InternetAddress[] summary = new InternetAddress[bcc.size()];

        int counter = 0;
        Iterator<String> bccIt = bcc.iterator();

        while (bccIt.hasNext()) {
            InternetAddress iaddress = new InternetAddress();
            iaddress.setAddress(bccIt.next());
            summary[counter] = iaddress;
            counter++;
        }

        return summary;
    }

    public static Object getEmailTemplate(InputStream aStream) {
    	LOGGER.info("getEmailTemplate(InputStream aStream) aufgerufen");
    	Digester digester = new Digester();
        digester.setValidating(false);
        digester.setUseContextClassLoader(true);
        LOGGER.info("Digester gesetzt 0");

        digester.addObjectCreate("email", EmailTemplate.class);

        digester.addBeanPropertySetter("email/subject", "subject");
        digester.addBeanPropertySetter("email/body", "body");
        digester.addBeanPropertySetter("email/from", "from");
        digester.addBeanPropertySetter("email/html", "html");
        digester.addCallMethod("email/to", "addTo", 0);
        digester.addCallMethod("email/cc", "addCc", 0);
        digester.addCallMethod("email/bcc", "addBcc", 0);
        LOGGER.info("Digester gesetzt 1");
        try {
        	LOGGER.info(aStream.available());
            return digester.parse(aStream);
        } catch (IOException e) {
            LOGGER.info("Error: IOException");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            LOGGER.info("Error: SAXException");
            e.printStackTrace();
            return null;
        }
    }
}// Ende class
