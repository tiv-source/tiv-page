/**
 * 
 */
package de.tivsource.page.entity.application;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
public class Education {

    /**
     * UUID des Objektes der Klasse Message, diese ID ist einmalig über alle
     * Objekte hinweg und sollte der bevorzugte weg sein auf bestimmte Objekte
     * zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date beginning;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date ending;

    private String kindOfSchool;

    private String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_uuid")
    private Application application;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnding() {
		return ending;
	}

	public void setEnding(Date ending) {
		this.ending = ending;
	}

	public String getKindOfSchool() {
		return kindOfSchool;
	}

	public void setKindOfSchool(String kindOfSchool) {
		this.kindOfSchool = kindOfSchool;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
    
}// Ende class
