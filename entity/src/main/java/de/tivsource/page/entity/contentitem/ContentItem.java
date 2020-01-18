package de.tivsource.page.entity.contentitem;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItem;

/**
 * 
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ContentItem extends PictureItem implements Comparable<ContentItem> {

    /**
     * Die Map mit dem Inhalt des Objektes, die Angabe ist Lokalisiert.
     */
    @OneToMany(mappedBy = "contentItem", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @MapKey(name = "language")
    private Map<Language, Content> contentMap;

    public Map<Language, Content> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<Language, Content> contentMap) {
        this.contentMap = contentMap;
    }

    public String getContent(String language) {
        String result = contentMap.get(Language.DE).getContent();
        String tmpResult = contentMap.get(Language.DE).getContent();
        try {
            tmpResult = contentMap
                    .get(Language.valueOf(language.toUpperCase())).getContent();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public String getContent(Language language) {
        String result = contentMap.get(Language.DE).getContent();
        String tmpResult = contentMap.get(Language.DE).getContent();

        try {
            tmpResult = contentMap.get(language).getContent();
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public Content getContentObject(Language language) {
        Content result = contentMap.get(Language.DE);
        Content tmpResult = contentMap.get(Language.DE);
        try {
            tmpResult = contentMap.get(language);
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        return tmpResult;
    }

    public abstract String getUrl();

}// Ende class
