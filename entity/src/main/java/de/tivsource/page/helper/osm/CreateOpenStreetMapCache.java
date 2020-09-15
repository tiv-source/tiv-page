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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Marc Michele
 *
 */
public class CreateOpenStreetMapCache {

    private static final Logger LOGGER = LogManager.getLogger(CreateOpenStreetMapCache.class);

	private static final String HTDOCS = "/var/www/html/osmcache/";

	private String uuid;

	private String centerLatitude;

	private String centerLongitude;
	
	// <struts:property value="latitude" />,<struts:property value="longitude" />
	// <struts:property value="latitude" />,<struts:property value="longitude" />
	// https://osm-static-maps.herokuapp.com/?geojson=[{%22type%22:%22Point%22,%22coordinates%22:[7.336500,51.438845]}]&height=230&width=250&zoom=17
	private String url = "https://osm-static-maps.herokuapp.com/?geojson=[{%22type%22:%22Point%22,%22coordinates%22:[";
	private String urlParameter1 = "],%22markerIconOptions%22:{%22iconUrl%22:%22https://www.backhaus.nrw/uploads/marker.png%22,iconAnchor:[13,35]}}]&width=";
	private String urlParameter2 = "&height=";
	private String urlParameter3 = "&zoom=17&attribution=%C2%A9%20OpenStreetMap%20contributors";

	private Map<String, String[]> widthMap;

	public CreateOpenStreetMapCache(String uuid, String latitude,
			String longitude) {
		super();
		widthMap = new HashMap<String, String[]>();
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
			LOGGER.info("MAP-URL: " + constructUrl(widthMap.get(key)));
		}
	}// Ende generate()

    private void initWidth() {
    	widthMap.put("wdefault", new String[]{"250","230"});
    	widthMap.put("w0201",    new String[]{"250","250"});
    	widthMap.put("w0581",    new String[]{"230","465"});
    	widthMap.put("w0619",    new String[]{"232","450"});
    	widthMap.put("w0641",    new String[]{"270","467"});
    	widthMap.put("w0701",    new String[]{"300","450"});
    	widthMap.put("w0901",    new String[]{"300","465"});
    	widthMap.put("w0951",    new String[]{"310","450"});
    	widthMap.put("w1001",    new String[]{"340","452"});
    	widthMap.put("w1101",    new String[]{"440","455"});
    	widthMap.put("w1291",    new String[]{"490","455"});
    	widthMap.put("w1321",    new String[]{"540","455"});
    	widthMap.put("w1401",    new String[]{"590","455"});
    	widthMap.put("w1501",    new String[]{"640","458"});
    	widthMap.put("w1701",    new String[]{"740","458"});
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
    
    private String constructUrl(String[] size) {
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append(url);
    	stringBuffer.append(centerLongitude);
    	stringBuffer.append(",");
    	stringBuffer.append(centerLatitude);
    	stringBuffer.append(urlParameter1);
    	stringBuffer.append(size[0]);
    	stringBuffer.append(urlParameter2);
    	stringBuffer.append(size[1]);
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
