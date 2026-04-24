/**
 * 
 */
package de.tivsource.page.entity.registration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.OrderChannel;
import de.tivsource.page.entity.enumeration.RegistrationStatus;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Registration implements Comparable<Registration> {

    public static final String TABLE = "Registration";

    /**
     * UUID des Objektes der Klasse Registration, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    /**
     * Versionsnummer für die Locking Strategie: optimistic-locking.
     */
    @Version
    private int version;

    private String firm;

    /**
     * Anrede
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutation_uuid")
    private Salutation salutation;

    /**
     * Vorname
     */
    private String firstname;

    /**
     * Nachname
     */
    private String lastname;

    /**
     * Geburtsdatum
     */
    private Date birthday;

    /**
     * Strasse
     */
    private String street;

    /**
     * Postleitzahl
     */
    private String zip;

    /**
     * Stadt
     */
    private String city;

    /**
     * Land des aktuellen Adressen-Objektes.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_uuid")
    private Country country;

    /**
     * Telefonnummer
     */
    private String telephone;

    /**
     * E-Mail Adresse
     */
    private String email;

    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    private Boolean listOfMaterials;

    /**
     * Status der Anmeldung
     */
    @Column(table = TABLE)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus status = RegistrationStatus.WAITING;

    @Column(table = TABLE)
    private String orderConfirmationNumber;

    @Column(table = TABLE)
    private String invoiceNumber;

    /**
     * Kurs zu dem die Anmeldung gehört.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = TABLE, name = "course_uuid")
    private Course course;

    /**
     * Anmeldung gehört einem Endkunden.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean consumer = true;

    /**
     * Wurde der Datenverarbeitung zugestimmt.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean privacy;

    /**
     * Wurden die AGBs akzeptiert.
     */
    @Basic
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
    @Column(table = TABLE)
    private Boolean terms;

    @Column(table = TABLE)
    @Enumerated(EnumType.STRING)
    private OrderChannel channel;

    /**
     * UUID die zur interaktion mit dem Benutzer benutzt wir.
     * Zum Beispiel für die Stornierung oder Bestätigung das man
     * von der Warteliste in den Status angemeldet wechseln möchte. 
     */
    @Column(table = TABLE, name="userToken", unique=true, length=42)
    private String userToken;

    /**
     * Datum an dem die Anmeldung erstellt wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    /**
     * Datum an dem die Anmeldung bearbeitet wurde.
     */
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    /**
     * Angabe von wem die Anmeldung bearbeitet wurde.
     */
    private String modifiedBy;

    /**
     * Angabe von wo die Anmeldung bearbeitet wurde.
     */
    private String modifiedAddress;

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
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return the firm
     */
    public String getFirm() {
        return firm;
    }

    /**
     * @param firm the firm to set
     */
    public void setFirm(String firm) {
        this.firm = firm;
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
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the listOfMaterials
     */
    public Boolean getListOfMaterials() {
        return listOfMaterials;
    }

    /**
     * @param listOfMaterials the listOfMaterials to set
     */
    public void setListOfMaterials(Boolean listOfMaterials) {
        this.listOfMaterials = listOfMaterials;
    }

    /**
     * @return the status
     */
    public RegistrationStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * @return the orderConfirmationNumber
     */
    public String getOrderConfirmationNumber() {
        return orderConfirmationNumber;
    }

    /**
     * @param orderConfirmationNumber the orderConfirmationNumber to set
     */
    public void setOrderConfirmationNumber(String orderConfirmationNumber) {
        this.orderConfirmationNumber = orderConfirmationNumber;
    }

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * @return the consumer
     */
    public Boolean getConsumer() {
        return consumer;
    }

    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(Boolean consumer) {
        this.consumer = consumer;
    }

    /**
     * @return the privacy
     */
    public Boolean getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    /**
     * @return the terms
     */
    public Boolean getTerms() {
        return terms;
    }

    /**
     * @param terms the terms to set
     */
    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    /**
     * @return the channel
     */
    public OrderChannel getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(OrderChannel channel) {
        this.channel = channel;
    }

    /**
     * @return the userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken the userToken to set
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedAddress
     */
    public String getModifiedAddress() {
        return modifiedAddress;
    }

    /**
     * @param modifiedAddress the modifiedAddress to set
     */
    public void setModifiedAddress(String modifiedAddress) {
        this.modifiedAddress = modifiedAddress;
    }

    public BigDecimal sum() {
        if(listOfMaterials) {
            return course.getPrice().add(course.getMaterialPrice());
        }
        return course.getPrice();
    }// Ende sum()

    public BigDecimal netSum() {
        BigDecimal divisor = course.getPriceTax().divide(new BigDecimal("100")).add(new BigDecimal("1"));
        return sum().divide(divisor, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax() {
        BigDecimal divisor = course.getPriceTax().divide(new BigDecimal("100")).add(new BigDecimal("1"));
        return sum().subtract(sum().divide(divisor, 2, RoundingMode.HALF_UP));
    }
    
    @Override
    public int compareTo(Registration o) {
        if (o.getCreated().after(this.getCreated())) {
            return 1;
        } else if (o.getCreated().before(this.getCreated())) {
            return -1;
        } else {
            return o.getUuid().compareTo(this.getUuid());
        }
    }
    
    @Override
    public boolean equals(Object o) {
        // Selbst Test
        if (this == o) return true;
        // NULL Test
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass()) return false;
        Registration registration = (Registration) o;
        // field comparison
        return Objects.equals(getUuid(), registration.getUuid());
    }

}// Ende class
