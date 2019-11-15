package de.tivsource.page.entity.message;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

/**
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Message {

    /**
     * UUID des Objektes der Klasse Message, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

	/**
	 * Geschlecht des Kontaktes.
	 */
    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean gender;

	/**
	 * Vorname des Kontakes.
	 */
	private String firstname;

	/**
	 * Nachname des Kontaktes.
	 */
	private String lastname;

	/**
	 * Mail-Adresse des Kontakes.
	 */
	private String mail;

	private String telephone;

	private String fax;

	@Lob
	private String content;

    @Basic
    @org.hibernate.annotations.Type(type = "yes_no")
    private Boolean privacy;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    private String createdAddress;

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
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
