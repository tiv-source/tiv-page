/**
 * 
 */
package de.tivsource.page.exceptions;

/**
 * @author Marc Michele
 *
 */
public class NoMailSessionCreatedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2644599042686382533L;

    public NoMailSessionCreatedException(String errorMessage) {
        super(errorMessage);
    }

}// Ende class
