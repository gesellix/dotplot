/**
 * 
 */
package org.dotplot.core.services;

/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IFrameworkContext extends IContext {
	
	/**
	 * Returns the <code>ServiceRegistry</code> of the framework.
	 * @return The <code>ServiceRegistry</code>.
	 */
	public IServiceRegistry getServiceRegistry();
	
	/**
	 * Returns the path of the working directory.
	 * @return The path of the working directory.
	 */
	public String getWorkingDirectory();
}
