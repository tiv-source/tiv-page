package de.tivsource.page.entity.message;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import de.tivsource.page.enumeration.GenderType;

/**
 * 
 * @author Marc Michele
 *
 */
@Entity
public class Message {

	/**
	 * Geschlecht des Kontaktes.
	 */
    @Enumerated(EnumType.STRING)
	private GenderType genderType;

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

	public GenderType getGenderType() {
		return genderType;
	}

	public void setGenderType(GenderType genderType) {
		this.genderType = genderType;
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

}// Ende class
