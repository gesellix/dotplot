/**
 * 
 */
package org.dotplot.core.plugins;

import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.IRegistry;
import org.dotplot.util.Registry;

/**
 * A <code>Regstry</code> for <code>Plugin</code>s.
 * <p>
 * During registration older pluginversions are automaticly replaced by newer
 * versions.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class PluginRegistry<P extends IPlugin> extends Registry<P> implements
		IPluginRegistry<P> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.plugins.IRegistry#combine(org.dotplot.core.plugins.IRegistry
	 * )
	 */
	@Override
	public void combine(IRegistry<P> registry)
			throws DuplicateRegistrationException {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.plugins.IPluginRegistry#registerPlugin(P)
	 */
	@Override
	public P register(String id, P plugin)
			throws DuplicateRegistrationException {
		if (plugin == null) {
			throw new NullPointerException();
		}
		else if (this.registry.containsKey(plugin.getID())) {
			P p = this.registry.get(plugin.getID());
			if (p.getVersion().equals(plugin.getVersion())) {
				throw new DuplicateRegistrationException(plugin.getID());
			}
			else if (p.getVersion().isSuperVersion(plugin.getVersion())) {
				return this.registry.put(plugin.getID(), plugin);
			}
			else {
				return plugin;
			}
		}
		else {
			this.registry.put(plugin.getID(), plugin);
			return null;
		}
	}

}
