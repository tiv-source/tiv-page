/**
 * 
 */
package de.tivsource.page.entity.feedback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.DocumentId;

/**
 * @author Marc Michele
 *
 */
@Entity
public class Feedback {

    /**
     * UUID des Objektes der Klasse Feedback, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Nummer der Kasse zu der das Feedback abgegeben wird.
     */
    private String cashpoint;

    /**
     * Nummer des Kassenbons des Einkaufs zu der das Feedback abgegeben wird.
     */
    private String voucher;

    /**
     * Map aus UUIDs von FeebackOption-Objekten und der Bewertung als Integer.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name="name", length = 64)
    @Column(name="value")
    @CollectionTable(name="Feedback_Answers", joinColumns=@JoinColumn(name="feedback_uuid"))
    private Map<String, Integer> answers = new HashMap<String, Integer>();

    /**
     * Kommentar
     */
    private String comment;

    /**
     * Zeitpunkt an dem das Feedback abgegeben wurde.
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * IP-Adresse von der aus das Feedback abgegeben wurde.
     */
    private String createdAddress;

    public Feedback() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedAddress() {
        return createdAddress;
    }

    public void setCreatedAddress(String createdAddress) {
        this.createdAddress = createdAddress;
    }

}// Ende class
