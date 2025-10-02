/**
 * 
 */
package de.tivsource.page.entity.application;

import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.entity.embeddable.Address;
import de.tivsource.page.entity.embeddable.ContactDetails;
import de.tivsource.page.entity.vacancy.Vacancy;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;

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
    @Convert(converter = org.hibernate.type.YesNoConverter.class)
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

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date birthday;

    @OneToMany(mappedBy = "application", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Education> educations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_uuid")
    private Vacancy vacancy;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date created;

    private String createdAddress;

}// Ende class
