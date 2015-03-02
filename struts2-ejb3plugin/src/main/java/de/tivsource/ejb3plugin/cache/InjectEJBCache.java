/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.tivsource.ejb3plugin.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementierung eine einfachen Caches.
 * 
 * @author Marc Michele
 * 
 */
public class InjectEJBCache {

	/**
	 * Cache der Klasse.
	 */
	private static Map<String, List<AnnotatedField>> cache = new ConcurrentHashMap<String, List<AnnotatedField>>();

	/**
	 * Map die dazu dient die Frage zu beantworten ob eine Klasse EJBs enthält.
	 */
	private static Map<String, Boolean> info = new ConcurrentHashMap<String, Boolean>();

	/**
	 * Singelton Pattern.
	 */
	private static InjectEJBCache instance;
	static {
		instance = new InjectEJBCache();
	}

	public static InjectEJBCache getInstance() {
		return instance;
	}

	/**
	 * Methode die die gespeicherten annotierten Felder einer Klasse zurück
	 * liefert.
	 *
	 * @param className - Name der Klasse für die die Felder gesucht werden.
	 * @return List<AnnotatedField> - Liste der annotierten Felder der übergebenen Klasse.
	 */
	public List<AnnotatedField> getAnnotatedFields(String className) {
		return cache.get(className);
	}

	/**
	 * Methode die die Felder die annotiert sind unter dem Namen der Klasse in
	 * den Cache schreibt und die Klasse makiert.
	 *
	 * @param className - Name der Klasse die die Felder enthält.
	 * @param fields - Felder die annotiert sind.
	 */
	public void cacheAnnotatedFields(String className,
			List<AnnotatedField> fields) {
		cache.put(className, fields);
		info.put(className, Boolean.TRUE);
	}

	/**
	 * Methode die testet ob eine Klasse EJBs enthält oder nicht. 
	 *
	 * @param className - Name der Klasse der getestet werden soll.
	 * @return Boolean - True wenn die Klasse EJBs enthält, sonst False.
	 */
	public Boolean hasEJBAnnotations(String className) {
		return info.get(className);
	}

	/**
	 * Methode die dazu dient eine Klasse die keine EJBs enthält zu makieren.
	 *
	 * @param className - Name der Klasse die markiert werden soll.
	 */
	public void noEJBAnnotations(String className) {
		info.put(className, Boolean.FALSE);
	}

}// Ende class