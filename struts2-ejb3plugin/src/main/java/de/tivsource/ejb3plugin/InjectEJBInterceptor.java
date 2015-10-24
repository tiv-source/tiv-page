/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.tivsource.ejb3plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

import de.tivsource.ejb3plugin.cache.AnnotatedField;
import de.tivsource.ejb3plugin.cache.InjectEJBCache;

/**
 * Interceptor der nach allen Feldern die mit InjectEJB annotierten sucht und
 * dann die angegebene EJB in das feld einfügt.
 * 
 * @author Marc Michele
 * 
 */
public class InjectEJBInterceptor extends AbstractInterceptor implements
		Interceptor {

	/**
	 * Serial Version Uid der Klasse.
	 */
	private static final long serialVersionUID = 7717052849037004429L;

    /**
     * Statischer Logger der Klasse.
     */
    private static final Logger LOGGER = Logger.getLogger(InjectEJBInterceptor.class);

	/**
	 * Einfacher Cache.
	 */
	private InjectEJBCache cache;

	public InjectEJBInterceptor() {
		super();
		this.cache = InjectEJBCache.getInstance();
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		// Hole das Action Objekt
		Object action = actionInvocation.getAction();

		// Hol den Namen der Action
		String actionClassName = action.getClass().getName();

		// Frage ob die Action Klasse sich im Cache befindet und EJBs besitzt.
		Boolean hasEJBAnnotations = this.cache.hasEJBAnnotations(actionClassName);

		// Wenn das Action Objekt sich im Cache befindet-
		if (Boolean.TRUE.equals(hasEJBAnnotations)) {
			// Hole die Felder aus dem Cache
			List<AnnotatedField> aFields = this.cache.getAnnotatedFields(action.getClass().getName());
			// Durchlaufe die Liste der Felder
			for (Iterator<AnnotatedField> it = aFields.iterator(); it.hasNext();) {
				// EJB Injection
				this.injectEJB(action, it.next());
			}
		} else if (hasEJBAnnotations == null) {
			List<AnnotatedField> annotatedFields = new ArrayList<AnnotatedField>();

			for (Field f : action.getClass().getDeclaredFields()) {

				if (f.isAnnotationPresent(InjectEJB.class)) {
					// Annotationen gefunden

					AnnotatedField aField = new AnnotatedField(
							(InjectEJB) f.getAnnotation(InjectEJB.class), f);
					this.injectEJB(action, aField);
					annotatedFields.add(aField);
				}
			}

			for (Field f : action.getClass().getSuperclass()
					.getDeclaredFields()) {

				if (f.isAnnotationPresent(InjectEJB.class)) {
					// Annotationen gefunden

					AnnotatedField aField = new AnnotatedField(
							(InjectEJB) f.getAnnotation(InjectEJB.class), f);
					this.injectEJB(action, aField);
					annotatedFields.add(aField);
				}
			}


			if (annotatedFields.size() == 0) {
				cache.noEJBAnnotations(actionClassName);
			} else {
				cache.cacheAnnotatedFields(actionClassName, annotatedFields);
			}
		}

		return actionInvocation.invoke();
	}

	private void injectEJB(Object action, AnnotatedField aField)
			throws Exception {
		InjectEJB annotation = aField.getAnnotation();
		Field f = aField.getField();

		StringBuilder serviceName = new StringBuilder(
				annotation.name() != null ? annotation.name() : f.getType()
						.getName());

		if (annotation.appname() != null && !annotation.appname().isEmpty()) {
			serviceName.insert(0, annotation.appname() + "/");
		} else {
			serviceName.insert(0, "dao-0.0.1/");
		}

		LOGGER.info("Test Inject");
		Object service = null;
		InitialContext ic = new InitialContext();
		try {
			LOGGER.info("Try EJB: java:global/tiv-page/"
					+ serviceName.toString());
			service = ic.lookup("java:global/tiv-page/"
					+ serviceName.toString());
		} catch (NamingException ex) {
			LOGGER.info("Error Inject");
			LOGGER.info("Try EJB: " + serviceName.toString());
			service = ic.lookup(serviceName.toString());
		} finally {
			if (service != null) {
				LOGGER.info("wasAccessible");
				boolean wasAccessible = f.isAccessible();
				f.setAccessible(true);
				f.set(action, service);
				f.setAccessible(wasAccessible);
			}
		}
	}

}// Ende class
