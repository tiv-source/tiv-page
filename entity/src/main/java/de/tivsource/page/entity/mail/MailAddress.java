package de.tivsource.page.entity.mail;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.enumeration.Salutation;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class MailAddress {

    /**
     * UUID des Objektes der Klasse MailAddress, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    private Salutation salutation;

    private String firstname;

    private String lastname;

    private String mail;

    private Language language;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    private String createdAddress;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean validated;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date validationDate;

    private String validationAddress;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the salutation
     */
    public Salutation getSalutation() {
        return salutation;
    }

    /**
     * @param salutation the salutation to set
     */
    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the createdAddress
     */
    public String getCreatedAddress() {
        return createdAddress;
    }

    /**
     * @param createdAddress the createdAddress to set
     */
    public void setCreatedAddress(String createdAddress) {
        this.createdAddress = createdAddress;
    }

    /**
     * @return the validated
     */
    public Boolean getValidated() {
        return validated;
    }

    /**
     * @param validated the validated to set
     */
    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    /**
     * @return the validationDate
     */
    public Date getValidationDate() {
        return validationDate;
    }

    /**
     * @param validationDate the validationDate to set
     */
    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    /**
     * @return the validationAddress
     */
    public String getValidationAddress() {
        return validationAddress;
    }

    /**
     * @param validationAddress the validationAddress to set
     */
    public void setValidationAddress(String validationAddress) {
        this.validationAddress = validationAddress;
    }

}// Ende class
