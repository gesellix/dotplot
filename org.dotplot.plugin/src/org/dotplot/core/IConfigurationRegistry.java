/**
 * 
 */
package org.dotplot.core;

import org.dotplot.util.IRegistry;

/**
 * A Registry for <code>Configuration</code>s.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IConfigurationRegistry extends IRegistry<IConfiguration>{
	
	/**
	 * Returns a copy of the <code>ConfigurationRegistry</code>.
	 * @return The copy.
	 */
	public IConfigurationRegistry copy();
}
