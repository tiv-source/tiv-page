/**
 * 
 */
package de.tivsource.page.entity.location;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name="LocationContactDetails")
public class ContactDetails {

    /**
     * UUID des Objektes der Klasse ContactDetails, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Handynummer
     */
    private String mobile;

    /**
     * Telefonnummer
     */
    private String telephone;

    /**
     * E-Mail Adresse
     */
    private String email;

    /**
     * Internetseite
     */
    private String homepage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_uuid")
    private Location location;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Methode die die Telefonnummer als tel: Link zurückgibt.
     *
     * @return
     */
    public String getTelephoneAsLink() {
        StringBuilder stringBuilder = new StringBuilder("tel:+49");
        String digits = telephone.replaceAll("[^0-9]", "");
        if(digits.length()>0) {
            char c = digits.charAt(0);
            if (c == '0') {
                return stringBuilder.append(digits.substring(1)).toString();
            }
        }
        return stringBuilder.append(digits).toString();
    }

}// Ende class
