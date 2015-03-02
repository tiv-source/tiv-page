package de.tivsource.page.entity.contentitem;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.namingitem.NamingItem;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ContentItem extends NamingItem {

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

}// Ende class
