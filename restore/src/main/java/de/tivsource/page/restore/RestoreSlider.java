/**
 * 
 */
package de.tivsource.page.restore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.entity.slider.Slider;
import de.tivsource.page.entity.slider.SliderImage;

/**
 * @author Marc Michele
 *
 */
public class RestoreSlider {

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger(RestoreSlider.class);

    private SliderDaoLocal sliderDaoLocal;

    public RestoreSlider(SliderDaoLocal sliderDaoLocal) {
        super();
        this.sliderDaoLocal = sliderDaoLocal;
    }

    public void generate(InputStream inputStream) {
        LOGGER.debug("generate(InputStream inputStream) aufgerufen");
        cleanup();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!line.startsWith("[Format Definition]")) {
                	Slider slider = convert(line);
                    sliderDaoLocal.merge(slider);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private Slider convert(String line) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // uuid|clickable|url|name|page|orderNumber|
        // uuid;original;large;normal;small;thumbnail;micro;standard;created;modified;modifiedBy;modifiedAddress;|
        // visible|created|modified|modifiedBy|modifiedAddress

        // Zerlege CSV-Zeile in String-Array.
        String[] items = line.split("\\|");

        // Erstelle Announcement-Objekt
        Slider slider = new Slider();

        slider.setUuid(items[0]);
        slider.setClickable(items[1].equals("true") ? true : false);
        slider.setUrl(items[2]);
        slider.setName(items[3]);
        slider.setPage(items[4]);
        slider.setOrderNumber(Integer.parseInt(items[5]));

        String[] imageItems = items[6].split(";");
        SliderImage sliderImage = new SliderImage();
        sliderImage.setUuid(imageItems[0]);
        sliderImage.setOriginal(imageItems[1]);
        sliderImage.setLarge(imageItems[2]);
        sliderImage.setNormal(imageItems[3]);
        sliderImage.setSmall(imageItems[4]);
        sliderImage.setThumbnail(imageItems[5]);
        sliderImage.setMicro(imageItems[6]);
        sliderImage.setStandard(imageItems[7].equals("true") ? true : false);
        sliderImage.setCreated(convertDateString(imageItems[8]));
        sliderImage.setModified(convertDateString(imageItems[9]));
        sliderImage.setModifiedBy(imageItems[10]);
        sliderImage.setModifiedAddress(imageItems[11]);
        sliderImage.setSlider(slider);
        slider.setImage(sliderImage);

        slider.setVisible(items[7].equals("true") ? true : false);
        slider.setCreated(convertDateString(items[8]));
        slider.setModified(convertDateString(items[9]));
        slider.setModifiedBy(items[10]);
        slider.setModifiedAddress(items[11]);

        return slider;
    }// Ende convert(String line)

    /**
     * Methode zum Konvertieren eines Strings des Formates "1970-12-01 23:59:59" in ein Date-Object. 
     * @param dateString
     * @return
     */
    private Date convertDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private void cleanup() {
        if(sliderDaoLocal.countAll() > 0) {
            Iterator<Slider> sliderIterator = sliderDaoLocal.findAll(0, sliderDaoLocal.countAll()).iterator();
            while(sliderIterator.hasNext()) {
                Slider next = sliderIterator.next();
                sliderDaoLocal.delete(next);
            }
        }
    }// Ende cleanup()

}// Ende class
