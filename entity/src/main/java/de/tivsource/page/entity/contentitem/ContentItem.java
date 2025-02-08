package de.tivsource.page.entity.contentitem;

import java.util.Map;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.pictureitem.PictureItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

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

    @Override
    public int compareTo(ContentItem o) {
        if (o.getCreated().after(this.getCreated())) {
            return 1;
        } else if (o.getCreated().before(this.getCreated())) {
            return -1;
        } else {
            return o.getUuid().compareTo(this.getUuid());
        }
    }

    public abstract String getUrl();

}// Ende class
