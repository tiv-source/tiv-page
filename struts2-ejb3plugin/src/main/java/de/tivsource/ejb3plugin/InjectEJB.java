package de.tivsource.ejb3plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface für die EJB Injection.
 *
 * @author Marc Michele
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InjectEJB {
	
	/**
	 * Name der EJB.
	 * @return
	 */
	String name();

	/**
	 * Name der Anwendung.
	 * @return
	 */
	String appname() default "";

	/**
	 * Lokal Ja/Nein.
	 * @return
	 */
	boolean local() default true;

	/**
	 * Remote Ja/Nein.
	 * @return
	 */
	boolean remote() default false;

}// Ende interface
