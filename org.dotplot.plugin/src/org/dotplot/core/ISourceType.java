/**
 * 
 */
package org.dotplot.core;

/**
 * Supertype for all <code>SourceTypes</code>.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface ISourceType extends Comparable {

	/**
	 * Returns the name of the <code>SourceType</code>
	 * 
	 * @return The name.
	 */
	public String getName();
}
