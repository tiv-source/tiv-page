/**
 * 
 */
package de.tivsource.page.entity.application;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;

import de.tivsource.page.entity.embeddable.Address;
import de.tivsource.page.entity.embeddable.ContactDetails;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Application {

    /**
     * UUID des Objektes der Klasse Application, diese ID ist einmalig über alle
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

    @Embedded
    private Address address;

    @Embedded
    private ContactDetails contactDetails;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date birthday;

    @OneToMany(mappedBy = "application", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Education> educations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_uuid")
    private Vacancy vacancy;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    private String createdAddress;

}// Ende class
