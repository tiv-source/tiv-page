package de.tivsource.page.common.salutation.description;

import org.hibernate.envers.Audited;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.entity.enumeration.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 * @author Marc Michele
 * 
 */
@Audited
@Entity
@Table(name = "Salutation_Description")
public class SalutationDescription {

    /**
     * UUID des Objektes der Klasse SalutationDescription, diese ID ist 
     * einmalig über alle Objekte hinweg und sollte der bevorzugte weg 
     * sein um auf bestimmte Objekte zuzugreifen.
     */
    @Id
    @DocumentId
    @Column(name="uuid", unique=true, length=42)
    private String uuid;

    private String name;

    @Lob
    private String description;

    private String keywords;

    private String longSalutation;

    @Enumerated(EnumType.STRING)
    private Language locale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salutation_uuid")
    private Salutation salutation;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLongSalutation() {
        return longSalutation;
    }

    public void setLongSalutation(String longSalutation) {
        this.longSalutation = longSalutation;
    }

    public Language getLocale() {
        return locale;
    }

    public void setLocale(Language locale) {
        this.locale = locale;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

}// Ende class
