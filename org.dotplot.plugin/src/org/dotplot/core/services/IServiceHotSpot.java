/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IServiceHotSpot {
	
	/**
	 * Returns the <code>ServiceHotSpot</code>'s id. 
	 * @return The id.
	 */
	public String getID();
	
	/**
	 * Returns the <code>Class</code> of the extention object. 
	 * @return The <code>Class</code>.
	 */
	public Class getExtentionClass();
	
	/**
	 * Checks if an <code>Extention</code> is valid for the <code>ServiceHotSpot</code>.
	 * @param extention The <code>Extention</code> to be checked.
	 * @return <code>true</code> if the <code>Extention</code> is valid, <code>false</code> otherwise.
	 */
	public boolean isValidExtention(Extention extention);
	
	/**
	 * Checks if the class of an extentionobject is valid.  
	 * @param extentionClass The class to be checked.
	 * @return <code>true</code>, if the class is valid, <code>false</code> otherwise.
	 */
	public boolean isValidExtention(Class extentionClass);
	
	/**
	 * Returns all <code>Extentions</code> of the <code>ServiceHotSpot</code>.
	 * @return The <code>Extentions</code>.
	 */
	public Collection<Extention> getAllExtentions();
	
	/**
	 * Returns a <code>Collection</code> of the avtivated <code>ServiceExtentions</code> 
	 * of the <code>ServiceHotSpot</code>.
	 * <p>
	 * An <code>Extention</code> get its activated state from its assigned
	 * <code>ServiceExtentionActivator</code>.
	 * </p>
	 * @return A <code>Collection</code> of  all avtivated <code>ServiceExtentions</code>.
	 * @see IServiceExtentionActivator
	 */
	public Collection<Extention> getActiveExtentions();
	
	/**
	 * Returns a <code>Collection</code> of the avtivated objects of the <code>ServiceExtentions</code> 
	 * of the <code>ServiceHotSpot</code>.
	 * <p>
	 * An <code>Extention</code> get its activated state from its assigned
	 * <code>ServiceExtentionActivator</code>.
	 * </p>
	 * @return A <code>Collection</code> of the objects of all avtivated <code>ServiceExtentions</code>.
	 * @see IServiceExtentionActivator
	 */
	public Collection<Object> getObjectsFromActivatedExtentions();
	
	/**
	 * Adds an <code>Extention</code> to the <code>ServiceHotSpot</code>.
	 * @param extention - the <code>Extention</code> to be added
 	 * @throws IllegalArgumentException - if the <code>Extention</code> is invalid
 	 */
	public void addExtention(Extention extention);
	
	/**
	 * Removes an <code>Extention</code> from the <code>ServiceHotSpot</code>.
	 * @param extention The <code>Extention</code>.
	 */
	public void removeExtention(Extention extention);	
}
