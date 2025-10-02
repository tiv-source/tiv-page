/**
 * 
 */
package de.tivsource.page.dao.slider;

import de.tivsource.page.entity.slider.SliderImage;
import jakarta.ejb.Local;

/**
 * @author Marc Michele
 *
 */
@Local
public interface SliderImageDaoLocal {

    public SliderImage findByUuid(String uuid);

}// Ende interface
