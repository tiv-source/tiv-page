/**
 * 
 */
package de.tivsource.page.dao.slider;

import javax.ejb.Local;

import de.tivsource.page.entity.slider.SliderImage;

/**
 * @author Marc Michele
 *
 */
@Local
public interface SliderImageDaoLocal {

    public SliderImage findByUuid(String uuid);

}// Ende interface
