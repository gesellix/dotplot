/**
 * 
 */
package org.dotplot.core.plugins;

import org.dotplot.core.services.AbstractService;

/**
 * Extents the <code>AbstractService</code> for usage in the plugin framework.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public abstract class PlugableService<C extends IPluginContext> extends
		AbstractService<C, PluginHotSpot> {

	/**
	 * Creates a new <code>PlugableService</code>.
	 * 
	 * @param id
	 *            The serviceid.
	 */
	public PlugableService(String id) {
		super(id);
	}

}
