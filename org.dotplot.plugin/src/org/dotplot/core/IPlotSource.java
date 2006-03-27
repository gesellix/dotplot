/**
 * 
 */
package org.dotplot.core;

import org.dotplot.core.services.IRessource;

/**
 * Interface for representing plot sources.
 * <p>
 * A <code>PlotSource</code> represents something which can be used as
 * source for creating a dotplot.
 * </p>
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IPlotSource extends IRessource {
	
	/**
	 * Returns the <code>SourceType</code> of the <code>PlotSource</code>.
	 * @return The <code>SourceType</code>.
	 */
	public ISourceType getType();
	
	/**
	 * Returns the name of the <code>PlotSource</code>.
	 * @return The name.
	 */
	public String getName();
	
	/**
	 * Returns the size of the <code>PlotSource</code> in Bytes.
	 * @return The size.
	 */
	public long size();
}
