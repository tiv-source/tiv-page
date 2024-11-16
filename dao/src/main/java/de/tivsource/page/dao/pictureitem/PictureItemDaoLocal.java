/**
 * 
 */
package de.tivsource.page.dao.pictureitem;

import de.tivsource.page.entity.pictureitem.PictureItem;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PictureItemDaoLocal {

    public PictureItem findByUuid(String uuid);

}// Ende interface
