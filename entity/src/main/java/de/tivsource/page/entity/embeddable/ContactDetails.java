/**
 * 
 */
package de.tivsource.page.entity.embeddable;

import javax.persistence.Embeddable;

/**
 * @author Marc Michele
 *
 */
@Embeddable
public class ContactDetails {

    /**
     * Handynummer
     */
    private String mobile;

    /**
     * Telefonnummer
     */
    private String telephone;

    /**
     * Faxnummer
     */
    private String fax;

    /**
     * E-Mail Adresse
     */
    private String email;

    /**
     * Internetseite
     */
    private String homepage;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

}// Ende class
