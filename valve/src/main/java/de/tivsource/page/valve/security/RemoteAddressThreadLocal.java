/**
 * 
 */
package de.tivsource.page.valve.security;

/**
 * @author Marc Michele
 *
 */
public class RemoteAddressThreadLocal {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void setKey(String key) {
        threadLocal.set(key);        
    }

    public static String getKey() {
        return (String)threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }

}// Ende class
