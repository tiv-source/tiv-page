/**
 * 
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import de.tivsource.page.entity.enumeration.Language;

/**
 * @author Marc Michele
 * 
 */
@Entity
public class ShortUrl {

    /**
     * Datenbank-ID des ShortUrl-Objektes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "shorturl_generator")
    @TableGenerator(name = "shorturl_generator", initialValue = 0, allocationSize = 1)
    @Column(name = "shorturl_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private NamingItem namingItem;

    private String globalHash;

    private String longUrl;

    private String shortUrl;

    private String userHash;

    private String globalClicks;

    private String userClicks;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public String getGlobalHash() {
	return globalHash;
    }

    public void setGlobalHash(String globalHash) {
	this.globalHash = globalHash;
    }

    public String getLongUrl() {
	return longUrl;
    }

    public void setLongUrl(String longUrl) {
	this.longUrl = longUrl;
    }

    public String getShortUrl() {
	return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
	this.shortUrl = shortUrl;
    }

    public String getUserHash() {
	return userHash;
    }

    public void setUserHash(String userHash) {
	this.userHash = userHash;
    }

    public String getGlobalClicks() {
	return globalClicks;
    }

    public void setGlobalClicks(String globalClicks) {
	this.globalClicks = globalClicks;
    }

    public String getUserClicks() {
	return userClicks;
    }

    public void setUserClicks(String userClicks) {
	this.userClicks = userClicks;
    }

}// Ende class
