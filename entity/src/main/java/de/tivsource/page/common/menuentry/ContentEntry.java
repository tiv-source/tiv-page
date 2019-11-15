/**
 * 
 */
package de.tivsource.page.common.menuentry;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import de.tivsource.page.entity.appointment.Appointment;
import de.tivsource.page.entity.contentitem.ContentItem;
import de.tivsource.page.entity.manual.Manual;
import de.tivsource.page.entity.news.News;
import de.tivsource.page.entity.vacancy.Vacancy;

/**
 * @author Marc Michele
 *
 */
@Audited
@Entity
@Table(name = "ContentEntry")
public class ContentEntry extends MenuEntry {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contentItem_uuid")
	private ContentItem contentItem;

    /**
	 * @return the contentItem
	 */
	public ContentItem getContentItem() {
		return contentItem;
	}

	/**
	 * @param contentItem the contentItem to set
	 */
	public void setContentItem(ContentItem contentItem) {
		this.contentItem = contentItem;
	}

	/* (non-Javadoc)
	 * @see de.tivsource.portal.common.menuentry.MenuEntry#getUrl()
	 */
	@Override
	public String getUrl() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("/");
        if(contentItem instanceof Appointment) {
            stringBuffer.append("appointment/");
            stringBuffer.append(contentItem.getUuid());
            stringBuffer.append("/");
        }
        else if(contentItem instanceof Manual) {
		    stringBuffer.append("manual/");
		    stringBuffer.append(contentItem.getUuid());
		    stringBuffer.append("/");
		}
		else if(contentItem instanceof News) {
		    stringBuffer.append("news/");
            stringBuffer.append(contentItem.getUuid());
            stringBuffer.append("/");
		}
        else if(contentItem instanceof Vacancy) {
            stringBuffer.append("vacancy/");
            stringBuffer.append(contentItem.getUuid());
            stringBuffer.append("/");
        }
		else {
	        stringBuffer.append(contentItem.getTechnical());
	        stringBuffer.append("/");
		}
		stringBuffer.append("index.html");
		return stringBuffer.toString();
	}

}// Ende class
