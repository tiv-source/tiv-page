/**
 * 
 */
package de.tivsource.page.valve.security;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Klasse die dazu dient die IP-Adresse aus dem Request auszulesen und dem
 * Login-Module zur Verfügung zu stellen.
 * 
 * @author Marc Michele
 *
 */
public class RemoteAddressValve extends ValveBase {

	private static final Log LOGGER = LogFactory.getLog(RemoteAddressValve.class);

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		LOGGER.debug("RemoteAddressValve invoke() aufgerufen");
        String key = request.getRemoteAddr();
        if (key == null) throw new ServletException("Keine IP-Adresse vorhanden");
        LOGGER.debug("IP-Adresse: " + key);
        RemoteAddressThreadLocal.setKey(key);
        // Rufe nächste valve auf
        getNext().invoke(request, response);
        LOGGER.debug("RemoteAddressValve invoke() beendet");
	}

}// Ende class
