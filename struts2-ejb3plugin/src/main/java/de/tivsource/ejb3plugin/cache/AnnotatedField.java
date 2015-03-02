package de.tivsource.ejb3plugin.cache;

import de.tivsource.ejb3plugin.InjectEJB;
import java.lang.reflect.Field;

/**
 * Annotation Klasse
 * 
 * @author Marc Michele
 * 
 */
public class AnnotatedField {

	private InjectEJB annotation;
	private Field field;

	public AnnotatedField(InjectEJB a, Field f) {
		this.annotation = a;
		this.field = f;
	}

	public InjectEJB getAnnotation() {
		return annotation;
	}

	public void setAnnotation(InjectEJB annotation) {
		this.annotation = annotation;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}// Ende class.