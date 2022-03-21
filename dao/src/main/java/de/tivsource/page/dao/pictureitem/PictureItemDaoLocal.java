/**
 * 
 */
package de.tivsource.page.dao.pictureitem;

import javax.ejb.Local;

import de.tivsource.page.entity.pictureitem.PictureItem;

/**
 * @author Marc Michele
 *
 */
@Local
public interface PictureItemDaoLocal {

    public PictureItem findByUuid(String uuid);

}// Ende interface
