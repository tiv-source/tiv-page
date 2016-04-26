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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	private String urlParameter1 = "&zoom=18&size=";
	private String urlParameter2 = "&maptype=mapnik&markers=";
	private String urlParameter3 = ",lightblue";

	private Map<String, String> widthMap;

	public CreateOpenStreetMapCache(String uuid, String latitude,
			String longitude) {
		super();
		widthMap = new HashMap<String, String>();
		initWidth();
		this.uuid = uuid;
		this.centerLatitude = latitude;
		this.centerLongitude = longitude;
	}

    public void generate() {
    	Iterator<String> keys = widthMap.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			loadImages(
					constructUrl(widthMap.get(key)), 
					constructPath(key)
					);
		}
	}// Ende generate()

    private void initWidth() {
    	widthMap.put("wdefault", "250x230");
    	widthMap.put("w0201",    "250x250");
    	widthMap.put("w0581",    "230x465");
    	widthMap.put("w0619",    "232x450");
    	widthMap.put("w0641",    "270x467");
    	widthMap.put("w0701",    "300x450");
    	widthMap.put("w0901",    "300x465");
    	widthMap.put("w0951",    "310x450");
    	widthMap.put("w1001",    "340x452");
    	widthMap.put("w1101",    "440x455");
    	widthMap.put("w1291",    "490x455");
    	widthMap.put("w1321",    "540x455");
    	widthMap.put("w1401",    "590x455");
    	widthMap.put("w1501",    "640x458");
    	widthMap.put("w1701",    "740x458");
    }

    private void loadImages(String urlString, String filePath) {
        BufferedImage image =null;
        try {
        	URL url = new URL(urlString);
            // Lade das Bild
            image = ImageIO.read(url);

            Path picturePath = Paths.get(filePath);

			if (Files.exists(picturePath) && !Files.isDirectory(picturePath)
					&& Files.isRegularFile(picturePath)) {
				// Lösche die Datei
            	Files.delete(picturePath);
            	// Speichere die Datei
            	ImageIO.write(image, "png",new File(filePath));
            } else if(Files.notExists(picturePath)) {
            	// Speichere die Datei
            	ImageIO.write(image, "png",new File(filePath));
            }

        } catch(IOException e) {
	            e.printStackTrace();
	    }
    }
    
    private String constructUrl(String size) {
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append(url);
    	stringBuffer.append(centerLatitude);
    	stringBuffer.append(",");
    	stringBuffer.append(centerLongitude);
    	stringBuffer.append(urlParameter1);
    	stringBuffer.append(size);
    	stringBuffer.append(urlParameter2);
    	stringBuffer.append(centerLatitude);
    	stringBuffer.append(",");
    	stringBuffer.append(centerLongitude);
    	stringBuffer.append(urlParameter3);
    	return stringBuffer.toString();
    }

    private String constructPath(String width) {
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append(HTDOCS);
    	stringBuffer.append(uuid);
    	stringBuffer.append("_");
    	stringBuffer.append(width);
    	stringBuffer.append(".png");
    	return stringBuffer.toString();
    }

}// Ende class
