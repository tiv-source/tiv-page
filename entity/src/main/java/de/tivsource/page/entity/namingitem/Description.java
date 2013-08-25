package de.tivsource.page.entity.namingitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import de.tivsource.page.entity.enumeration.Language;

/**
 * 
 * @author Marc Michele
 * 
 */
@Entity
public class Description {

    /**
     * Datenbank-ID des Item-Objektes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "description_generator")
    @TableGenerator(name = "description_generator", initialValue = 0, allocationSize = 1)
    @Column(name = "description_id")
    private Long id;

    private String name;

    @Lob
    private String description;

    private String keywords;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private NamingItem namingItem;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public Language getLanguage() {
	return language;
    }

    public void setLanguage(Language language) {
	this.language = language;
    }

    public NamingItem getNamingItem() {
	return namingItem;
    }

    public void setNamingItem(NamingItem namingItem) {
	this.namingItem = namingItem;
    }

}// Ende class
