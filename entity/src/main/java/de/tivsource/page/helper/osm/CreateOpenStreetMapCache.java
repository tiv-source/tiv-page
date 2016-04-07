/**
 * 
 */
package de.tivsource.page.helper.osm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * @author Marc Michele
 *
 */
public class CreateOpenStreetMapCache {

	private static final String HTDOCS = "/srv/www/htdocs/osmcache/";
	
	private String uuid;

	private String centerLatitude;

	private String centerLongitude;
	
	// <struts:property value="latitude" />,<struts:property value="longitude" />
	// <struts:property value="latitude" />,<struts:property value="longitude" />
	private String url = "http://staticmap.openstreetmap.de/staticmap.php?center=";
	private String urlParameter1 = "&zoom=18&size=250x230&maptype=mapnik&markers=";
	private String urlParameter2 = ",lightblue";

	public CreateOpenStreetMapCache(String uuid, String latitude,
			String longitude) {
		super();
		this.uuid = uuid;
		this.centerLatitude = latitude;
		this.centerLongitude = longitude;
	}

    public void generate() {
    	StringBuffer urlStringBuffer = new StringBuffer();
    	urlStringBuffer.append(url);
    	urlStringBuffer.append(centerLatitude);
    	urlStringBuffer.append(",");
    	urlStringBuffer.append(centerLongitude);
    	urlStringBuffer.append(urlParameter1);
    	urlStringBuffer.append(centerLatitude);
    	urlStringBuffer.append(",");
    	urlStringBuffer.append(centerLongitude);
    	urlStringBuffer.append(urlParameter2);

    	StringBuffer pictureStringBuffer = new StringBuffer();
    	pictureStringBuffer.append(HTDOCS);
    	pictureStringBuffer.append(uuid);
    	pictureStringBuffer.append(".png");
    	
        BufferedImage image =null;
        try {
        	URL url =new URL(urlStringBuffer.toString());

            // read the url
            image = ImageIO.read(url);
            String pictureString = pictureStringBuffer.toString();
            Path picturePath = Paths.get(pictureString);

            
            
			if (Files.exists(picturePath) && !Files.isDirectory(picturePath)
					&& Files.isRegularFile(picturePath)) {
				// Lösche die Datei
            	Files.delete(picturePath);
            	// Speichere die Datei
            	ImageIO.write(image, "png",new File(pictureString));
            } else if(Files.notExists(picturePath)) {
            	// Speichere die Datei
            	ImageIO.write(image, "png",new File(pictureString));
            }

        } catch(IOException e) {
	            e.printStackTrace();
	    }

		
	}// Ende generate()

}// Ende class
