/**
 * 
 */
package org.dotplot.core.services;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public interface ITaskMonitor {

	public boolean isCanceled();
	public void setTaskName(String name);
	public void setTaskPartName(String name );
	
	/**
	 * 
	 * @param d - between 0.0 and 1.0.
	 */
	public void worked(double d);
	
}
