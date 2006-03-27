/**
 * 
 */
package org.dotplot.core.plugins;

import org.dotplot.core.services.ServiceHotSpot;

/**
 * Default implementaion of the <code>IPluginHotSpot</code> interface.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PluginHotSpot extends ServiceHotSpot implements IPluginHotSpot {

	/**
	 * Creates a new <code>PluginHotSpot</code>.
	 * @param id - the id of the <code>PluginHotSpot</code>.
	 * @param extentionClass - the class of the extention objects.
	 */
	public PluginHotSpot(String id, Class extentionClass) {
		super(id, extentionClass);
	}

}
