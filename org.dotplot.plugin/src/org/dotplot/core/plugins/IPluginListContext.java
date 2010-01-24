/**
 * 
 */
package org.dotplot.core.plugins;

import java.util.Collection;

import org.dotplot.core.services.IContext;

/**
 * A <code>Context</code> providing a list of <code>plugins</code>.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public interface IPluginListContext extends IContext {

	/**
	 * Returns a <code>Collection</code> containing <code>Plugin</code>s.
	 * 
	 * @return The <code>Plugin</code>s.
	 */
	public Collection<? extends IPlugin> getPluginList();
}
