/**
 * 
 */
package org.dotplot.core.services;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IServiceExtentionActivator {
	
	/**
	 * Returns the id of the <code>ServiceExtentionActivator</code>.
	 * @return The id.
	 */
	public String getID();
	
	/**
	 * Indicates of the <code>Extention</code> is activated.
	 * @return <code>true</code> of its activated, <code>false</code> otherwise.
	 */
	public boolean isActivated();
}
