/**
 * 
 */
package de.tivsource.page.entity.property;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * @author Marc Michele
 *
 */
@Indexed
@Entity
public class Property {

    @Id
    @DocumentId
    @Column(name="property_key", unique=true)
    private String key;

    @Column(name="property_value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}// Ende class
