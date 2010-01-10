/**
 * 
 */
package org.dotplot.core.plugins;

/**
 * Factory interface for creating extention objects. This interface can be used
 * to add an extention to a hotspot.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IExtentionFactory {

	/**
	 * Creates the object.
	 * 
	 * @return The created object.
	 */
	public Object createObject();
}
